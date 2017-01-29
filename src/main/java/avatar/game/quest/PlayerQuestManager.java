package avatar.game.quest;

import avatar.game.quest.menu.QuestMenu;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

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

    public boolean has(String id){return ownedQuests.contains(id);}

    public boolean hasCompleted(String id){return completed.contains(id);}

    public UserPlayer getOwner() {
        return owner;
    }

    public QuestMenu getQuestMenu() {
        return questMenu;
    }

    public void add(QuestReference reference) {
        Quest quest = reference.getQuest(owner);
        if(ownedQuests.size() == 54){
            Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "You can only have up to 54 quest at a time!", Optional.of(Messager.Prefix.ERROR));
            return;
        } else if(!has(quest.getID()) && !hasCompleted(quest.getID())){
            Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Quest added: " + quest.getTitle(), Optional.of(Messager.Prefix.SUCCESS));
            ownedQuests.add(quest);
        }
    }

    public void setActiveQuest(QuestReference reference){
        String id = reference.getID();
        Quest quest = null;
        for(Quest q: ownedQuests){
            if(q.getID().equals(id)) {
                quest = q;
            }
        }

        if(quest != null && !quest.isActive()){
            Optional<Quest> temp = getActiveQuest();
            if(temp.isPresent() && temp.get().getID() != quest.getID()){
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
                completed.add(quest.get().getID());
                ownedQuests.remove(quest.get());
                questMenu.build();
            }
        }
    }

    public List<Quest> getQuests() {
        return ownedQuests;
    }
}
