package avatar.game.user;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.chat.ChatColorTemplate;
import avatar.game.chat.channel.ChatChannel;
import avatar.game.user.scoreboard.Scoreboard;
import avatar.game.user.stats.IStatsPreset;
import avatar.util.misc.LocationUtils;
import avatar.util.particles.ParticleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserPlayer extends User {

    private ParticleUtils.ParticleModifier particleModifier = ParticleUtils.ParticleModifier.NORMAL;
    private Optional<Location> lastBlockLocation = Optional.empty();

    private PlayerQuestManager questManager;

    private Dialogue currentDialogue;
    private Account account;
    private Scoreboard scoreboard;
    private Title title;
    private ChatColorTemplate chatColorTemplate = ChatColorTemplate.GRAY;
    private ChatChannel chatChannel;

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
        setChatChannel(ChatChannel.GLOBAL);
        scoreboard = new Scoreboard(this);

        Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(getPlayer().getLocation());
        if(area.isPresent())
            enterArea(area.get());
    }

    @Override
    public void enterArea(Area area){
        super.enterArea(area);

        scoreboard.updateScoreboard();
    }

    public void giveDialogue(String id){
        Optional<DialogueReference> reference = DialogueReference.getReference(id);
        if(reference.isPresent()){
            giveDialogue(reference.get());
        }
    }

    public void giveDialogue(DialogueReference reference){
        setCurrentDialogue(reference.getDialogue(getPlayer()));
        startDialogue();
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
                enterArea(area.get());
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
                        enterArea(temp2.get());
                    }
                }
            }
            setLastBlockLocation(player.getLocation());
        }

        //Quest updating
        getQuestManager().tick();

        //scoreboard update
        scoreboard.updateScoreboard();
    }

    @Override
    public void cleanUp(){
        super.cleanUp();
    }

    public void removeDialogue() {
        currentDialogue = null;
    }

    public void startDialogue() {
        currentDialogue.displayNext();

        Sponge.getEventManager().post(new DialogueEvent.Displayed(Cause.source(Avatar.INSTANCE.getPluginContainer()).build(), this));
    }

    public void updateScoreboard() {
        scoreboard.updateScoreboard();
    }

    //--- Getters ---

    public Player getPlayer(){return Bukkit.getPlayer(getUUID());}

    public ParticleUtils.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    public Optional<Location> getLastBlockLocation() {
        return lastBlockLocation;
    }

    public Dialogue getCurrentDialogue() {
        return currentDialogue;
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

    //--- Setters ---

    public void setParticleModifier(ParticleUtils.ParticleModifier particleModifier) {
        this.particleModifier = particleModifier;
    }

    public void setLastBlockLocation(Location lastBlockLocation) {
        this.lastBlockLocation = Optional.of(lastBlockLocation);
    }

    public UserPlayer setCurrentDialogue(Dialogue currentDialogue) {
        this.currentDialogue = currentDialogue;
        return this;
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
}
