package avatar.game.quest;

import avatar.Avatar;
import avatar.events.custom.AbilityEvent;
import avatar.events.custom.DialogueEvent;
import avatar.events.custom.QuestEvent;
import avatar.game.quest.builder.CheckpointBuilder;
import avatar.game.quest.condition.ClickDialogueChoice;
import avatar.game.quest.condition.FireAbility;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import avatar.util.misc.Items;
import avatar.util.text.AltCodes;
import avatar.util.text.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest {

    public static CheckpointBuilder getCheckpointBuilder(UserPlayer userPlayer){return new CheckpointBuilder(userPlayer);}

    /*
     * General quest container.
     */

    private String title, description;
    protected UserPlayer owner;
    private int recommendedLvl = 0;
    private QuestReference reference;
    private boolean active = false;
    protected List<Checkpoint> checkpoints;
    private int currentCheckpoint = 0;
    private ItemStack itemRepresentation;
    private Reward reward;

    protected abstract void loadCheckpoints();

    public Quest(String title, String description, int lvl, QuestReference reference, Material itemType, Reward reward, UserPlayer userPlayer){
        this.owner = userPlayer;
        this.title = ChatColor.GOLD +title;
        this.description = ChatColor.WHITE + description;
        recommendedLvl = lvl;
        this.reference = reference;
        loadCheckpoints();
        this.itemRepresentation = new ItemStack(itemType);
        this.reward = reward;
        setLore();
    }

    /**
     * This is for the item representation
     */
    public void setLore(){
        List<String> temp = new ArrayList<>();
        temp.add(ChatColor.ITALIC + description);
        temp.add(" ");
        temp.add(ChatColor.GRAY + "Objectives:");
        temp.add(" ");
        for(Checkpoint checkpoint: checkpoints){
            //This will only add quest objectives that have either been completed or that is the current one
            if(checkpoint.isCompleted()){
                temp.add(ChatColor.GREEN + AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get());
            } else if(checkpoints.indexOf(checkpoint) == currentCheckpoint){
                temp.add(ChatColor.GRAY + AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get());
            }
            temp.add(" ");
        }
        if(active){
            temp.add(0, " ");
            temp.add(1, ChatColor.GREEN + "Active");
            temp.add(2, " ");
        }
        ItemMeta itemMeta = itemRepresentation.getItemMeta();
        itemMeta.setLore(temp);
        itemMeta.setDisplayName(title);
        itemRepresentation.setItemMeta(itemMeta);
    }

    /*
     * Function that makes things happen.
     * Check if checkpoint is reached
     * if so, is final checkpoint?
     * if so, give reward and finishing dialogue
     * if complete and not final, load next checkpoint and start()
     * Update quest tracker
     */
    public boolean tick(){
        Checkpoint current = checkpoints.get(currentCheckpoint);
        if (current.isComplete()) {
            current.printCompletionMsg();

            Avatar.INSTANCE.getServer().getPluginManager().callEvent(new QuestEvent.CheckpointComplete(ListenerManager.getDefaultCause(), owner, this, current));

            current.doCompleteAction();

            currentCheckpoint++;

            /**
             * If the current checkpoint has a fork condition, get the appropriate continuation checkpoint
             * for the choice the player made
             */
            if(current.isFork()){
                checkpoints.addAll(currentCheckpoint, current.getForkedCheckpoint());
            }

            if (currentCheckpoint <= checkpoints.size() - 1) {
                checkpoints.get(currentCheckpoint).start();
                setLore();
            } else {
                completeQuest();
                setLore();
                return true;
            }
        }

        //update tracker
        checkpoints.get(currentCheckpoint).printActionMessage();

        return false;
    }

    private void completeQuest(){
        Title.sendBoth(owner.getPlayer(), ChatColor.GOLD + getTitle(), ChatColor.GREEN + "Completed", 2, 3, 2);
        if(reward != null)
            reward.giveReward(owner.getPlayer());

        Avatar.INSTANCE.getServer().getPluginManager().callEvent(new QuestEvent.Complete(ListenerManager.getDefaultCause(), owner, this));

        active = false;
    }

    public QuestReference getReference(){return reference;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public boolean isActive() {
        return active;
    }

    public void toggleActive() {
        if(active){
            active = false;
            setLore();
        } else {
            active = true;
            setLore();
            checkpoints.get(currentCheckpoint).start();
            Title.sendBoth(owner.getPlayer(), getTitle(), getDescription(), 2, 3, 2);

            Avatar.INSTANCE.getServer().getPluginManager().callEvent(new QuestEvent.Start(ListenerManager.getDefaultCause(), owner, this));
        }
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }

    public void checkItemForQuestItem(Location location, Items items) {
        checkpoints.get(currentCheckpoint).checkItemForQuestItem(location, items);
    }

    /**
     * We check the event here so that way each condition for every player doesn't have to have its own listener
     * @param event
     */
    public void checkEvent(Event event) {
        if(event instanceof DialogueEvent.ChoiceClicked){
            if(checkpoints.get(currentCheckpoint).hasCondition(ClickDialogueChoice.class)){
                try {
                    ((ClickDialogueChoice)checkpoints.get(currentCheckpoint).getCondition(ClickDialogueChoice.class)).handle((DialogueEvent.ChoiceClicked) event);
                } catch (Exception e) {
                }
            }
        } else if(event instanceof AbilityEvent.PostFire){
            if(checkpoints.get(currentCheckpoint).hasCondition(FireAbility.class)){
                try {
                    ((FireAbility)checkpoints.get(currentCheckpoint).getCondition(FireAbility.class)).handle((AbilityEvent.PostFire) event);
                } catch (Exception e) {
                }
            }
        }


    }
}
