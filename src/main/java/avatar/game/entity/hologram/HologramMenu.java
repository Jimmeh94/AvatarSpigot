package avatar.game.entity.hologram;

import avatar.game.user.UserPlayer;
import avatar.util.directional.MenuGeneration;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class HologramMenu {

    protected UserPlayer owner;
    protected List<ClientHologram> holograms;
    protected Location center;
    protected double boudningRadius;

    /**
     * Override this and spawn all armorstands here
     */
    public abstract void spawnMenu();

    public HologramMenu(UserPlayer owner, double boudningRadius){
        this.owner = owner;
        this.boudningRadius = boudningRadius;
        this.center = owner.getPlayer().getLocation().clone();

        holograms = new ArrayList<>();
    }

    protected List<Location> getMenuLocations(){
        return MenuGeneration.getRelativeLocations(8, owner.getPlayer());
    }

    public void closeMenu(){
        holograms.forEach(ClientHologram::remove);
    }

    public Optional<ClientHologram> getHologram(Location location){
        for(ClientHologram clientHologram: holograms){
            if(clientHologram.hasLocation(location)){
                return Optional.of(clientHologram);
            }
        }
        return Optional.empty();
    }

}
