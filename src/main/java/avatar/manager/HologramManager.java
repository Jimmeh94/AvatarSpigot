package avatar.manager;

import avatar.game.entity.hologram.Hologram;
import org.bukkit.Location;

import java.util.Optional;

public class HologramManager extends Manager<Hologram>{

    public Optional<Hologram> find(Location location){
        for(Hologram hologram: objects){
            if(hologram.hasLocation(location)){
                return Optional.of(hologram);
            }
        }
        return Optional.empty();
    }

    public void removeHolograms(){
        for(Hologram hologram: objects){
            hologram.remove();
        }
    }

}
