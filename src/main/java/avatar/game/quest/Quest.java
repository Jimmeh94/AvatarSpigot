package avatar.game.quest;

import avatar.Avatar;
import avatar.events.custom.QuestEvent;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import avatar.util.misc.Items;
import avatar.util.text.AltCodes;
import avatar.util.text.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Quest {

    /*
     * General quest container.
     */

    private String title, description;
    private UserPlayer owner;
    private int recommendedLvl = 0;
    private QuestReference reference;
    private boolean active = false;
    private List<Checkpoint> checkpoints;
    private int currentCheckpoint = 0;
    private ItemStack itemRepresentation;
    private Reward reward;

    public Quest(String title, String description, int lvl, QuestReference reference, List<Checkpoint> checkpoints, Material itemType, Reward reward, UserPlayer userPlayer){
        this.title = ChatColor.GOLD +title;
        this.description = ChatColor.WHITE + description;
        recommendedLvl = lvl;
        this.reference = reference;
        this.checkpoints = checkpoints;
        this.itemRepresentation = new ItemStack(itemType);
        this.reward = reward;
        setLore();
        this.owner = userPlayer;
        for(Checkpoint c: checkpoints){
            c.setPlayer(owner.getPlayer());
        }
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
            if(checkpoint.isCompleted()){
                temp.add(ChatColor.GREEN + AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get());
            } else temp.add(ChatColor.GRAY + AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get());
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
        if (checkpoints.get(currentCheckpoint).isComplete()) {
            checkpoints.get(currentCheckpoint).printCompletionMsg();

            Avatar.INSTANCE.getServer().getPluginManager().callEvent(new QuestEvent.CheckpointComplete(ListenerManager.getDefaultCause(), owner, this, checkpoints.get(currentCheckpoint)));

            checkpoints.get(currentCheckpoint).deactivate();
            setLore();
            currentCheckpoint++;

            if (currentCheckpoint <= checkpoints.size() - 1) {
                checkpoints.get(currentCheckpoint).start();
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

    public Reward getReward() {
        return reward;
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

            for(Checkpoint checkpoint: checkpoints){
                checkpoint.deactivate();
            }
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
}
