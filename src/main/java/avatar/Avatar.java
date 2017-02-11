package avatar;

import avatar.commands.*;
import avatar.events.EntityEvents;
import avatar.events.InventoryEvents;
import avatar.events.PlayerEvents;
import avatar.events.WorldEvents;
import avatar.game.entity.npc.nms.CustomEntities;
import avatar.game.quest.builder.QuestBuilder;
import avatar.manager.*;
import avatar.runnable.GameTimer;
import avatar.runnable.SlowTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class Avatar extends JavaPlugin {

    //TODO test instanced and area particle displaying
    //TODO test entity targeting and ability/user collision

    //TODO handle checkpoint/quest resetting
    //TODO work with unique dialogue settings. Want the dialogue to not be dispalyable after certain choice is chosen

    //TODO figure out the screen tint effect Sky Bounds has when you enter combat (/toggles, called World Border Warning feature)

    //TODO change abilities to fire new instances so they can fire multiple right after one another

    public static Avatar INSTANCE;

    //--- manager ---
    private UserManager userManager;
    private AreaManager areaManager;
    private ChatChannelManager chatChannelManager;
    private EconomyManager economyManager;
    private BlockManager blockManager;
    private HologramManager hologramManager;
    private EntityManager entityManager;

    //misc
    private final int combatInterval = 5; //how many seconds out of combat needed to be switched to out of combat
    private final QuestBuilder questBuilder = new QuestBuilder();
    private GameTimer gameTimer;
    private SlowTimer slowTimer;

    @Override
    public void onEnable(){
        INSTANCE = this;

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
    }

    private void registerCommands() {
        getCommand("choice").setExecutor(new ChoiceCommands());
        getCommand("quest").setExecutor(new QuestCommands());
        getCommand("dialogue").setExecutor(new DialogueCommands());
        getCommand("particle").setExecutor(new ParticleCommands());
        getCommand("ability").setExecutor(new AbilityCommands());
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
}
