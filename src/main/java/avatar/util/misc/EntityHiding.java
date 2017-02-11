package avatar.util.misc;

import avatar.Avatar;
import avatar.game.area.Instance;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class EntityHiding {

    public static void instance(Instance instance){
        //make all players and entities outside of instance hidden to instanced players
        //make all instanced players and entities hidden to non-instanced players

        for(User user: instance.getMembers()){
            if(user.isPlayer()){
                UserPlayer userPlayer = (UserPlayer)user;

                for(Player player: Bukkit.getOnlinePlayers()){
                    if(player == userPlayer.getPlayer())
                        continue;

                    if(instance.hasUser(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())){
                        player.showPlayer(userPlayer.getPlayer());
                        userPlayer.getPlayer().showPlayer(player);
                    } else {
                        player.hidePlayer(userPlayer.getPlayer());
                        userPlayer.getPlayer().hidePlayer(player);
                    }
                }
            }
        }
    }

    public static void hidePlayer(Player toHide){
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(toHide).get();
        Optional<Instance> instance = userPlayer.getPresentArea().getInstance(userPlayer);
        for(Player player: Bukkit.getOnlinePlayers()){
            if(instance.isPresent()){
                if(!instance.get().hasUser(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())){
                    player.hidePlayer(toHide);
                    toHide.hidePlayer(player);
                }
            } else {
                player.hidePlayer(toHide);
                toHide.hidePlayer(player);
            }
        }
    }

    public static void showPlayer(Player toShow){
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(toShow).get();
        Optional<Instance> instance = userPlayer.getPresentArea().getInstance(userPlayer);

        for(Player player: Bukkit.getOnlinePlayers()){
            if(instance.isPresent()){
                if(instance.get().hasUser(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())){
                    player.showPlayer(toShow);
                    toShow.showPlayer(player);
                }
            } else {
                player.showPlayer(toShow);
                toShow.showPlayer(player);
            }
        }
    }

    public static void hideEntity(Player player, Entity entity){
        EntityTracker tracker = ((CraftWorld)player.getWorld()).getHandle().tracker;
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        net.minecraft.server.v1_11_R1.Entity other = ((CraftEntity)entity).getHandle();
        EntityTrackerEntry entry = tracker.trackedEntities.get(other.getId());
        if(entry != null) {
            entry.clear(entityPlayer);
        }

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(other.getId()));
    }

    public static void showEntity(Player player, Entity entity){
        EntityTracker tracker = ((CraftWorld)player.getWorld()).getHandle().tracker;
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        net.minecraft.server.v1_11_R1.Entity other = ((CraftEntity)entity).getHandle();

        EntityTrackerEntry entry = tracker.trackedEntities.get(other.getId());
        if(entry != null) {
            //entry.updatePlayer(this.getHandle());
        }

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving((EntityLiving) other));
    }

}
