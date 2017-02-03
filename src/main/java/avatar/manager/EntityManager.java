package avatar.manager;

import avatar.game.entity.npc.NPC;
import avatar.game.entity.npc.NPCVillager;
import avatar.game.entity.npc.spawn.NPCConcernedCitizen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

public class EntityManager extends Manager<NPC>{

    public EntityManager(){
        World world = Bukkit.getWorlds().get(0);
        add(new NPCConcernedCitizen(new Location(world, -844.5, 5, 303.5, -90f, 0f)));

        for(NPC npc: objects){
            if(npc instanceof NPCVillager){
                ((NPCVillager)npc).spawn();
            }
        }
    }

    public Optional<NPC> find(int id){
        for(NPC npc: objects){
            if(isValidNPC(npc)){
                if(npc.getEntity().getEntityId() == id)
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

    public void interaction(Player player, int entityId) {
        for(NPC entity: objects){
            if(isValidNPC(entity)) {
                if (entity.getEntity().getEntityId() == entityId)
                    entity.onInteract(player);
            } else remove(entity);
        }
    }

    public void tick() {
        for(NPC npc: objects){
            if(!isValidNPC(npc)){
                remove(npc);
            }
        }
    }
}
