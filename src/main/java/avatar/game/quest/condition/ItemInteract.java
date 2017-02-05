package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.util.misc.Items;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

public class ItemInteract extends Condition{

    private Items item;
    private Items.ItemCallback itemCallback;
    private Location where;

    public ItemInteract(Items item, Location where, Items.ItemCallback itemCallback) {
        this.item = item;
        this.where = where;
        this.itemCallback = itemCallback;
    }

    @EventHandler
    public void handle(Location location, Items items){
        //The player found the item and interacted with it
        //For some reason, the main PlayerInteractEvent listener would fire before this one,
        //regardless of priority, so we have to do it this way.
        //For some reason, the x value was 1 block off where it displays it should be in game. For instance, when I stand
        //on the block, it's at -814, but the location passed here is -815. Do .distance for now
        System.out.println(where.toString());
        System.out.println(location.toString());
        if(location.distance(where) <= 1){
            if(items == this.item){
                valid = true;
                itemCallback.handle(Avatar.INSTANCE.getUserManager().findUserPlayer(getPlayer()).get());
            }
        }
    }


    @Override
    protected void unregister() {

    }
}
