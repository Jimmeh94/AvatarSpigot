package avatar.game.area.instances;

import avatar.game.area.Area;
import avatar.game.area.Instance;
import avatar.game.entity.npc.nms.CustomZombie;
import avatar.game.entity.npc.nms.EntityTypes;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import org.bukkit.Location;

public class SpawnGardenBrothersInstance extends Instance {

    public SpawnGardenBrothersInstance(Area area){
        super(area);
    }

    @Override
    public void addUser(User user) {
        super.addUser(user);

        if(user.isPlayer()){
            //spawn entities
            Location use = area.getCenter().clone();
            use.setY(((UserPlayer)user).getPlayer().getLocation().getY());
            EntityTypes.spawnEntity(new CustomZombie(area.getCenter().getWorld()), use);
        }
    }

    @Override
    public void removeUser(User user) {
        super.removeUser(user);

        //despawn entities
    }
}
