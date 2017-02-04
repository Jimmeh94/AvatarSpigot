package avatar.manager;

import avatar.game.entity.hologram.environment.EnvironmentInteractable;
import avatar.game.entity.hologram.environment.ScrollEI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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
    public void load(Chunk chunk){
        for(EnvironmentInteractable e: objects){
            //System.out.println("e: " + e.getReference().getLocation().getChunk().getX() + " " + e.getReference().getLocation().getChunk().getZ());
            //System.out.println("chunk: " + chunk.getX() + " " + chunk.getZ());
            if(e.getReference().getLocation().getChunk() == chunk){
              //  System.out.println("chunk");
                e.spawn();
            }
        }
    }

    public void unload(Chunk chunk){
        for(EnvironmentInteractable e: objects){
            if(e.getReference().getLocation().getChunk() == chunk){
                e.remove();
            }
        }
    }

    public void unloadAll(){
        objects.forEach(EnvironmentInteractable::remove);
    }

    public enum ServerEIReference{
        DEMO(new Location(Bukkit.getWorlds().get(0), -814, 6, 367));

        private Location location;

        ServerEIReference(Location location) {
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }
    }
}
