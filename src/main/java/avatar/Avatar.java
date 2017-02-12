package avatar;

import avatar.commands.ChoiceCommands;
import avatar.commands.DialogueCommands;
import avatar.commands.ParticleCommands;
import avatar.commands.QuestCommands;
import avatar.events.EntityEvents;
import avatar.events.InventoryEvents;
import avatar.events.PlayerEvents;
import avatar.events.WorldEvents;
import avatar.events.protocol.ServerToClient;
import avatar.game.entity.npc.nms.CustomEntities;
import avatar.game.quest.builder.QuestBuilder;
import avatar.manager.*;
import avatar.runnable.GameTimer;
import avatar.runnable.SlowTimer;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Avatar extends JavaPlugin {

    //TODO figure out the screen tint effect Sky Bounds has when you enter combat (/toggles, called World Border Warning feature)

    //TODO ask Spigot forums if more efficient for each ability to have own runnable or have 1 big runnable
    //TODO ask forums about entity hiding

    //TODO test instanced and area particle displaying
    //TODO test entity targeting and ability/user collision
    //TODO test blocking and combat, damaging system, combat entries

    public static Avatar INSTANCE;

    //--- manager ---
    private UserManager userManager;
    private AreaManager areaManager;
    private ChatChannelManager chatChannelManager;
    private EconomyManager economyManager;
    private BlockManager blockManager;
    private HologramManager hologramManager;
    private EntityManager entityManager;
    private ProtocolManager protocolManager;

    //misc
    private final int combatInterval = 5; //how many seconds out of combat needed to be switched to out of combat
    private final QuestBuilder questBuilder = new QuestBuilder();
    private GameTimer gameTimer;
    private SlowTimer slowTimer;

    @Override
    public void onEnable(){
        INSTANCE = this;

        protocolManager = ProtocolLibrary.getProtocolManager();
        userManager = new UserManager();
        chatChannelManager = new ChatChannelManager();
        areaManager = new AreaManager();
        economyManager = new EconomyManager();
        blockManager = new BlockManager();
        hologramManager = new HologramManager();
        entityManager = new EntityManager();

        registerListeners();
        registerCommands();
        registerRunnables();

        CustomEntities.registerEntities();

        getLogger().info(">> " + getDescription().getName() + " v" + getDescription().getVersion() + " enabled! <<");
    }

    private void registerRunnables() {
        gameTimer = new GameTimer(5L);
        slowTimer = new SlowTimer(20L);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new EntityEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new WorldEvents(), this);

        protocolManager.addPacketListener(new ServerToClient());
    }

    private void registerCommands() {
        getCommand("choice").setExecutor(new ChoiceCommands());
        getCommand("quest").setExecutor(new QuestCommands());
        getCommand("dialogue").setExecutor(new DialogueCommands());
        getCommand("particle").setExecutor(new ParticleCommands());
    }

    @Override
    public void onDisable(){
        hologramManager.removeHolograms();
        entityManager.clearAll();
        blockManager.replaceAll();

        CustomEntities.unregisterEntities();

        getLogger().info(">> " + getDescription().getName() + " v" + getDescription().getVersion() + " disabled! <<");
    }

    public int getCombatInterval() {
        return combatInterval;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public ChatChannelManager getChatChannelManager() {
        return chatChannelManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public QuestBuilder getQuestBuilder() {
        return questBuilder;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
