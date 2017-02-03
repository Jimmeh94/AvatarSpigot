package avatar.manager;

import avatar.game.entity.hologram.Hologram;
import avatar.game.entity.hologram.ServerHologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Optional;

public class HologramManager extends Manager<Hologram>{

    public HologramManager(){
        //load default server holograms
        World world = Bukkit.getWorlds().get(0);
        add(new ServerHologram(new Location(world, -822.5, 6, 303.5), ChatColor.GOLD + " ^ Quest ^ "));
    }

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
