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
    protected double boundingRadius;

    /**
     * Override this and spawn all armorstands here
     */
    protected abstract void spawnMenu();

    public HologramMenu(UserPlayer owner, double boundingRadius){
        this.owner = owner;
        this.boundingRadius = boundingRadius;
        this.center = owner.getPlayer().getLocation().clone();

        if(owner.getOpenMenu() != null){
            owner.getOpenMenu().closeMenu();
        }

        holograms = new ArrayList<>();
        owner.setOpenMenu(this);
        spawnMenu();
    }

    protected List<Location> getMenuLocations(int amount){
        return MenuGeneration.getRelativeLocations(amount, owner.getPlayer());
    }

    public void closeMenu(){
        holograms.forEach(ClientHologram::remove);
        owner.setOpenMenu(null);
    }

    public Optional<ClientHologram> getHologram(Location location){
        for(ClientHologram clientHologram: holograms){
            if(clientHologram.hasLocation(location)){
                return Optional.of(clientHologram);
            }
        }
        return Optional.empty();
    }

    public void tick(){
        if(owner.getPlayer().getLocation().distance(center) > boundingRadius){
            closeMenu();
        }
    }
}
