package avatar.events;

import avatar.Avatar;
import avatar.game.entity.npc.NPC;
import avatar.game.user.UserPlayer;
import avatar.util.misc.BlockReplacement;
import avatar.util.misc.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

import java.util.Optional;

public class PlayerEvents implements Listener {

    private static Location spawn = new Location(Bukkit.getWorlds().get(0), -811.5, 6, 303.5, 90f, 0f);

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        new UserPlayer(event.getPlayer().getUniqueId()).init();
        event.getPlayer().teleport(spawn);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get().cleanUp();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get();
        userPlayer.getChatChannel().displayMessage(userPlayer.getChatColorTemplate().getPrefix() + userPlayer.getTitle().getDisplay()
                + userPlayer.getChatColorTemplate().getName() + userPlayer.getPlayer().getDisplayName() + ": "
                + userPlayer.getChatColorTemplate().getMessage() + event.getMessage());
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        event.setCancelled(true);

        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get();

        if(event.getClickedBlock() != null) {
            Optional<Items> optional = Items.find(event.getClickedBlock().getType(), event.getClickedBlock().getData());
            if(optional.isPresent()){
                Avatar.INSTANCE.getBlockManager().add(new BlockReplacement(event.getClickedBlock(), 20));
                event.getClickedBlock().setType(Material.AIR);

                userPlayer.getQuestManager().checkItemForQuestItem(event.getClickedBlock().getLocation(), optional.get());
                //do certain thing based on what item it is

                return;
            }
        }

        if(event.getItem() != null){
            userPlayer.getHotbarSetup().handle(userPlayer.getPlayer().getInventory().getHeldItemSlot());
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){
        event.setCancelled(true);

        Optional<NPC> npc = Avatar.INSTANCE.getEntityManager().find(event.getRightClicked());
        if(npc.isPresent()){
            npc.get().onInteract(event.getPlayer());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
            Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get()
                    .enterArea(Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(event.getTo()).get(), true);
    }

}
