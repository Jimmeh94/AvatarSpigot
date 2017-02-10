package avatar.game.area.instances;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.Instance;
import avatar.game.entity.npc.nms.CustomZombie;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.util.misc.EntityHiding;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnGardenBrothersInstance extends Instance {

    public SpawnGardenBrothersInstance(Area area){
        super(area);

        Location use = new Location(area.getCenter().getWorld(), -754, 5, 328);
        CustomZombie customZombie = new CustomZombie(area.getCenter().getWorld(), use);
        addUser(Avatar.INSTANCE.getUserManager().find(customZombie.getUniqueID()).get());

        //We've spawned all the entities for the initialization of this instance
        //Now we need to make sure no players can see them
        //As players are added to the instance, they'll be able to track them
        for(Player player: Bukkit.getOnlinePlayers()){
            for(User user: getMembers()){
                if(user.isPlayer())
                    continue;

                EntityHiding.hideEntity(player, user.getEntity());
            }
        }
    }

    @Override
    public void addUser(User user) {
        super.addUser(user);

        if(user.isPlayer()){
            //spawn entities
            for(User user1: getMembers()){
                if(user1.isPlayer())
                    continue;

                EntityHiding.showEntity(((UserPlayer)user).getPlayer(), user1.getEntity());
            }
        }
    }

    @Override
    public void removeUser(User user) {
        super.removeUser(user);

        //despawn entities
        if(user.isPlayer()){
            for(User user1: getMembers()){
                if(user1.isPlayer())
                    continue;

                EntityHiding.hideEntity(((UserPlayer) user).getPlayer(), user1.getEntity());
            }
        }
    }
}
