package avatar;

import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.manager.AreaManager;
import avatar.manager.ChatChannelManager;
import avatar.manager.EconomyManager;
import avatar.manager.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Avatar extends JavaPlugin {

    public static Avatar INSTANCE;

    //--- manager ---
    private UserManager userManager;
    private AreaManager areaManager;
    private ChatChannelManager chatChannelManager;
    private EconomyManager economyManager;

    //misc
    private final int combatInterval = 5; //how many seconds out of combat needed to be switched to out of combat
    private final QuestBuilder questBuilder = new QuestBuilder();
    private final DialogueBuilder dialogueBuilder = new DialogueBuilder();

    @Override
    public void onEnable(){
        INSTANCE = this;

        getLogger().info(">> " + getDescription().getName() + " v" + getDescription().getVersion() + " enabled! <<");
    }

    @Override
    public void onDisable(){
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
}
