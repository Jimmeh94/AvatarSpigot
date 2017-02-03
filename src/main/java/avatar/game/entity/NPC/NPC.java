package avatar.game.entity.npc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class NPC {

    protected Entity entity;

    public abstract void onInteract(Player player);

    public Entity getEntity(){return entity;}

}
