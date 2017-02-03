package avatar;

import avatar.commands.*;
import avatar.events.*;
import avatar.events.custom.PlayerInteract;
import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.manager.*;
import avatar.runnable.GameTimer;
import avatar.runnable.SlowTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class Avatar extends JavaPlugin {

    //TODO test instanced and area particle displaying
    //TODO test entity targeting and ability/user collision
    //TODO block replacement needs to replace metadeta too

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
    private final DialogueBuilder dialogueBuilder = new DialogueBuilder();
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

        getLogger().info(">> " + getDescription().getName() + " v" + getDescription().getVersion() + " enabled! <<");
    }

    private void registerRunnables() {
        gameTimer = new GameTimer(5L);
        slowTimer = new SlowTimer(20L);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntity(), this);
        getServer().getPluginManager().registerEvents(new LeafDecay(), this);
        getServer().getPluginManager().registerEvents(new FireSpread(), this);
        getServer().getPluginManager().registerEvents(new PlayerHunger(), this);
        getServer().getPluginManager().registerEvents(new WeatherChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
        getServer().getPluginManager().registerEvents(new InventoryDrop(), this);
        getServer().getPluginManager().registerEvents(new InventoryMove(), this);
        getServer().getPluginManager().registerEvents(new InventoryPickUp(), this);
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

    public DialogueBuilder getDialogueBuilder() {
        return dialogueBuilder;
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
