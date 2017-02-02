package avatar.events;

import avatar.Avatar;
import avatar.game.quest.Quest;
import avatar.game.user.UserPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getClickedInventory() == event.getWhoClicked().getInventory())
            return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        String name = event.getClickedInventory().getName();
        if(name.equalsIgnoreCase("Quests")){
            event.setCancelled(true);
            UserPlayer playerInfo = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getWhoClicked()).get();

            for(Quest quest: playerInfo.getQuestManager().getQuests()){
                if(event.getCurrentItem().equals(quest.getItemRepresentation())){
                    quest.toggleActive();
                    event.getWhoClicked().closeInventory();
                    return;
                }
            }
        }

    }

}
