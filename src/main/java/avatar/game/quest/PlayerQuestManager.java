package avatar.game.quest;

import avatar.game.quest.menu.QuestMenu;
import avatar.game.user.UserPlayer;
import avatar.util.misc.Items;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerQuestManager {

    /**
     * Manages all quest actions for the player
     */

    private UserPlayer owner;
    private QuestMenu questMenu;
    /**
     * All currently owned quest that can be worked on
     */
    private List<Quest> ownedQuests;
    /**
     * All completed quest ID's
     */
    private List<String> completed;

    public PlayerQuestManager(UserPlayer owner){
        this.owner = owner;
        ownedQuests = new ArrayList<>();
        completed = new ArrayList<>();
        questMenu = new QuestMenu(owner);
    }

    public void displayQuestMenu(){
        questMenu.build();
        owner.getPlayer().openInventory(owner.getQuestManager().getQuestMenu().getMenu());
    }

    public boolean has(QuestReference id){
        for(Quest quest: ownedQuests){
            if(quest.getReference().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean hasCompleted(QuestReference id){return completed.contains(id.getID());}

    public UserPlayer getOwner() {
        return owner;
    }

    public QuestMenu getQuestMenu() {
        return questMenu;
    }

    public boolean canTakeQuest(QuestReference reference){
        if(ownedQuests.size() == 54){
            Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "You can only have up to 54 quests at a time!", Optional.of(Messager.Prefix.ERROR));
            return false;
        } else if(has(reference)){
            return false;
        } else if(hasCompleted(reference)) {
            return false;
        } else return true;
    }

    public void add(Quest quest) {
        Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Quest added: " + quest.getTitle(), Optional.of(Messager.Prefix.SUCCESS));
        ownedQuests.add(quest);
    }

    public void setActiveQuest(QuestReference reference){
        String id = reference.getID();
        Quest quest = null;
        for(Quest q: ownedQuests){
            if(q.getReference().equals(id)) {
                quest = q;
            }
        }

        if(quest != null && !quest.isActive()){
            Optional<Quest> temp = getActiveQuest();
            if(temp.isPresent() && temp.get().getReference() != quest.getReference()){
                temp.get().toggleActive();
            }
            quest.toggleActive();
        }
    }

    public Optional<Quest> getActiveQuest(){
        Optional<Quest> quest = Optional.empty();
        for(Quest q: ownedQuests){
            if(q.isActive())
                quest = Optional.of(q);
        }
        return quest;
    }

    public void tick() {
        Optional<Quest> quest = getActiveQuest();
        if(quest.isPresent()){
            if(quest.get().tick()) {
                completed.add(quest.get().getReference().getID());
                ownedQuests.remove(quest.get());
            }
        }
    }

    public List<Quest> getQuests() {
        return ownedQuests;
    }

    /**
     * We have to do it this way because the 'main' PlayerInteractEvent listener would always be checked
     * before a quest condition PlayerInteractEvent listener
     * @param location
     * @param items
     */
    public void checkItemForQuestItem(Location location, Items items) {
        for(Quest quest: ownedQuests){
            quest.checkItemForQuestItem(location, items);
        }
    }

    public void checkEvent(Event event) {
        //The event will be checked no matter if the quest is active or not
        for(Quest quest: ownedQuests){
            quest.checkEvent(event);
        }
    }

    public boolean hasActiveQuest() {
        for(Quest quest: ownedQuests){
            if(quest.isActive()){
                return true;
            }
        }
        return false;
    }
}
