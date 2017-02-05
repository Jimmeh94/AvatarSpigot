package avatar.manager;

import avatar.game.entity.npc.NPC;
import avatar.game.entity.npc.NPCVillager;
import avatar.game.entity.npc.spawn.NPCConcernedCitizen;
import avatar.game.entity.npc.spawn.NPCOldMan;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager extends Manager<NPC>{

    private List<Entity> toCheck = new CopyOnWriteArrayList<>();

    public EntityManager(){
        World world = Bukkit.getWorlds().get(0);
        add(new NPCConcernedCitizen(new Location(world, -844.5, 5, 303.5, -90f, 0f)));
        add(new NPCOldMan());

        for(NPC npc: objects){
            if(npc instanceof NPCVillager){
                ((NPCVillager)npc).spawn();
            }
        }
    }

    public Optional<NPC> find(Entity entity){
        for(NPC npc: objects){
            if(isValidNPC(npc)){
                if(npc.getEntity() == entity)
                    return Optional.of(npc);
            } else remove(npc);
        }
        return Optional.empty();
    }

    public boolean isValidNPC(NPC npc){
        return npc.getEntity().isValid() && !npc.getEntity().isDead();
    }

    public void clearAll() {
        for(NPC entity: objects){
            entity.getEntity().remove();
        }
    }

    public void tick() {
        Optional<NPC> optional;
        for(Entity entity: toCheck){
            optional = find(entity);
            if(!optional.isPresent()){
                entity.remove();
                toCheck.remove(entity);
            }
        }

        for(NPC npc: objects){
            if(!isValidNPC(npc)){
                remove(npc);
            }
        }
    }

    //Since we can't check if an entity should spawn until after it spawns, we can use this
    public void addEntityToCheck(Entity entity){
        toCheck.add(entity);
    }
}
