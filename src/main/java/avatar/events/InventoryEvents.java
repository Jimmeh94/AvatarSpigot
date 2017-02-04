package avatar.events;

import avatar.Avatar;
import avatar.game.quest.Quest;
import avatar.game.user.UserPlayer;
import avatar.game.user.menus.ParticleModifierMenu;
import avatar.game.user.menus.SettingsMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getClickedInventory() == event.getWhoClicked().getInventory())
            return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        UserPlayer playerInfo = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getWhoClicked()).get();

        String name = event.getClickedInventory().getName();
        if(name.equalsIgnoreCase("Quests")){
            event.setCancelled(true);

            for(Quest quest: playerInfo.getQuestManager().getQuests()){
                if(event.getCurrentItem().equals(quest.getItemRepresentation())){
                    quest.toggleActive();
                    event.getWhoClicked().closeInventory();
                    return;
                }
            }
        } else if(name.equalsIgnoreCase("Particle Settings")){
            event.setCancelled(true);
            ParticleModifierMenu.handle(event.getSlot(), playerInfo);
            playerInfo.getPlayer().closeInventory();
        } else if(name.equalsIgnoreCase("Settings")){
            event.setCancelled(true);
            SettingsMenu.handle(event.getSlot(), playerInfo);
        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(InventoryPickupItemEvent event){
        event.setCancelled(true);
    }

}
