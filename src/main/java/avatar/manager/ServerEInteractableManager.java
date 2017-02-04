package avatar.manager;

import avatar.game.entity.hologram.environment.EnvironmentInteractable;
import avatar.game.entity.hologram.environment.ScrollEI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Optional;

public class ServerEInteractableManager extends Manager<EnvironmentInteractable> {

    public ServerEInteractableManager(){
        //load all server environment interactables
        add(new ScrollEI(ServerEIReference.DEMO));
    }

    public Optional<EnvironmentInteractable> find(Location location){
        for(EnvironmentInteractable e: objects){
            if(e.getReference().getLocation().equals(location)){
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    //when a chunk loads, spawn all EI's in that chunk
    public void load(int x, int z){
        for(EnvironmentInteractable e: objects){
            if(e.getReference().getX() == x && e.getReference().getZ() == z){
                e.spawn();
            }
        }
    }

    public void unload(int x, int z){
        for(EnvironmentInteractable e: objects){
            if(e.getReference().getX() == x && e.getReference().getZ() == z){
                e.remove();
            }
        }
    }

    public void unloadAll(){
        objects.forEach(EnvironmentInteractable::remove);
    }

    public enum ServerEIReference{
        DEMO(new Location(Bukkit.getWorlds().get(0), -814, 6, 367),  -51, 22);

        private Location location;
        private int x, z;

        ServerEIReference(Location location, int x, int z) {
            this.location = location;
            this.x = x;
            this.z = z;
        }

        public Location getLocation() {
            return location;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }
}
