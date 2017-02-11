package avatar.game.user;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.chat.ChatColorTemplate;
import avatar.game.chat.channel.ChatChannel;
import avatar.game.dialogue.PlayerDialogueManager;
import avatar.game.entity.hologram.HologramMenu;
import avatar.game.quest.PlayerQuestManager;
import avatar.game.user.hotbar.CombatHotbar;
import avatar.game.user.hotbar.DefaultHotbar;
import avatar.game.user.hotbar.HotbarSetup;
import avatar.game.user.scoreboard.Scoreboard;
import avatar.game.user.scoreboard.presets.CombatPreset;
import avatar.game.user.scoreboard.presets.DefaultPreset;
import avatar.game.user.stats.IStatsPreset;
import avatar.util.directional.LocationUtils;
import avatar.util.text.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserPlayer extends User {

    private PlayerQuestManager questManager;
    private PlayerDialogueManager dialogueManager;

    private Account account;
    private Scoreboard scoreboard;
    private Title title;
    private ChatColorTemplate chatColorTemplate = ChatColorTemplate.GRAY;
    private ChatChannel chatChannel;
    private Optional<Location> lastBlockLocation = Optional.empty();
    private DefaultHotbar passiveHotbar;
    private HotbarSetup currentSetup;
    private CombatHotbar combatHotbar;
    private HologramMenu openMenu;
    private Settings settings;

    public UserPlayer(UUID user) {
        super(user);
    }

    public UserPlayer(UUID user, IStatsPreset preset){
        super(user, preset);
    }

    public void init(){
        title = Title.TEST;
        account = new Account(this);
        questManager = new PlayerQuestManager(this);
        dialogueManager = new PlayerDialogueManager(this);
        setChatChannel(ChatChannel.GLOBAL);
        scoreboard = new Scoreboard(this);

        Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(getPlayer().getLocation());
        if(area.isPresent())
            enterArea(area.get(), false);

        scoreboard.init();
        settings = new Settings(this);

        passiveHotbar = new DefaultHotbar(this);
        combatHotbar = new CombatHotbar(this);
        currentSetup = passiveHotbar;
        currentSetup.apply();
    }

    @Override
    public void enterArea(Area area, boolean updateScoreboard){
        super.enterArea(area, updateScoreboard);

        if(updateScoreboard)
            scoreboard.updateScoreboard();
    }

    public PlayerDialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public HotbarSetup getHotbarSetup() {
        return currentSetup;
    }

    public void setOpenMenu(HologramMenu openMenu) {
        this.openMenu = openMenu;
    }

    public HologramMenu getOpenMenu() {
        return openMenu;
    }

    @Override
    public void tick(){
        super.tick();

        //area checks
        Player player = getPlayer();
        boolean doWork = true;

        //If they left an area and where they went to isn't a defined area, put them into a "global" area
        if(getPresentArea() == null){
            Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GLOBAL);
            if(area.isPresent()){
                enterArea(area.get(), true);
            }
        }

        if(getLastBlockLocation().isPresent()){
            if(getLastBlockLocation().get().distance(player.getLocation()) < 1) {
                //traveled less than 1 block
                doWork = false;
            }
        } else {
            setLastBlockLocation(getPlayer().getLocation());
            doWork = false;
        }

        if(doWork) {
            //Get a connecting path of locations from where they were to where they are
            List<Location> traveled = LocationUtils.getConnectingLine(getLastBlockLocation().get(), player.getLocation());
            if (traveled.size() > 1) {
                //Where they started
                Optional<Area> temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(0));
                //Where they are
                Optional<Area> temp2 = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(traveled.size() - 1));

                if(temp.isPresent() && temp2.isPresent()){
                    if(temp.get() != temp2.get()){
                        enterArea(temp2.get(), true);
                    }
                }
            }
            setLastBlockLocation(player.getLocation());
        }

        //Quest updating
        getQuestManager().tick();

        //scoreboard update
        if(scoreboard != null)
            scoreboard.updateScoreboard();
    }

    @Override
    public void cleanUp(){
        super.cleanUp();

        chatChannel.removeMember(this);
        scoreboard.unregisterScoreboard();
    }

    //--- Getters ---

    public Player getPlayer(){return Bukkit.getPlayer(getUUID());}

    public Optional<Location> getLastBlockLocation() {
        return lastBlockLocation;
    }

    public Account getAccount() {
        return account;
    }

    public PlayerQuestManager getQuestManager() {
        return questManager;
    }

    public ChatColorTemplate getChatColorTemplate() {
        return chatColorTemplate;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public Title getTitle() {
        return title;
    }

    public Settings getSettings(){
        return settings;
    }

    //--- Setters ---

    public void setLastBlockLocation(Location lastBlockLocation) {
        this.lastBlockLocation = Optional.of(lastBlockLocation);
    }


    public void setTitle(Title title) {
        this.title = title;
    }

    public void setChatChannel(ChatChannel chatChannel) {
        if(this.chatChannel != null)
            this.chatChannel.removeMember(this);
        this.chatChannel = chatChannel;
        this.chatChannel.addMember(this);
    }

    public void setChatColorTemplate(ChatColorTemplate chatColorTemplate) {
        this.chatColorTemplate = chatColorTemplate;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void tickHologramMenu() {
        if(openMenu != null){
            openMenu.tick();
        }
    }

    public void swapHotbars() {
        if(currentSetup == passiveHotbar){
            currentSetup = combatHotbar;
            getScoreboard().setPreset(new CombatPreset((this)));
        } else {
            if(getCombatLogger().isInCombat()){
                Messager.sendMessage(getPlayer(), ChatColor.GRAY + "You can't enter passive mode while in combat!", Optional.of(Messager.Prefix.ERROR));
                return;
            }
            currentSetup = passiveHotbar;
            getScoreboard().setPreset(new DefaultPreset((this)));
        }

        currentSetup.apply();
    }
}
