package avatar.game.entity.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Hologram {

    protected List<ArmorStand> stands;

    public Hologram(){
        stands = new ArrayList<>();
    }

    public boolean hasLocation(Location location) {
        for(ArmorStand armorStand: stands){
            if(armorStand.getLocation().equals(location)){
                return true;
            }
        }
        return false;
    }

    public void remove() {
        for(ArmorStand armorStand: stands){
            armorStand.remove();
        }
    }

    /**
     * Used for ClientHologram
     */
    public interface HologramInteraction{
        void onInteract(Player player, Hologram hologram);
    }
}
