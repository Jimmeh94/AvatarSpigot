package avatar.game.quest.menu;

import avatar.game.quest.Quest;
import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestMenu {

    /*
     * To be the quest menu that players open to view their currently owned quest
     */

    private Inventory menu;
    private UserPlayer owner;

    public QuestMenu(UserPlayer owner){
        this.owner = owner;
    }

    /*
     * Default building of quest menu
     * Player can't have more than 54 currently owned quest
     */

    public void build(){
        List<ItemStack> temp = new ArrayList<>();
        Inventory inv = Bukkit.createInventory(null, 54, "Quests");
        for(Quest quest: owner.getQuestManager().getQuests()){
            if(quest.isActive())
                temp.add(0, quest.getItemRepresentation());
            else temp.add(quest.getItemRepresentation());
        }
        for(ItemStack itemStack: temp){
            inv.addItem(itemStack);
        }

        menu = inv;
    }

    public Optional<Quest> findClickedQuest(int id) {
        if (owner.getQuestManager().getQuests().get(id) != null) {
            return Optional.of(owner.getQuestManager().getQuests().get(id));
        }
        return Optional.empty();
    }

    public Optional<Quest> findClickedQuest(String id) {
        for(Quest quest: owner.getQuestManager().getQuests()){
            if(quest.getID().equalsIgnoreCase(id)){
                return Optional.of(quest);
            }
        }
        return Optional.empty();
    }

    public Inventory getMenu() {
        return menu;
    }
}
