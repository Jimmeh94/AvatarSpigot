package avatar.manager;

import avatar.game.entity.npc.NPC;
import avatar.game.entity.npc.spawn.NPCConcernedCitizen;
import avatar.game.entity.npc.spawn.NPCOldMan;
import org.bukkit.entity.Entity;

import java.util.Optional;

public class EntityManager extends Manager<NPC>{

    public void spawn(){
        new NPCConcernedCitizen();
        new NPCOldMan();
    }

    public Optional<NPC> find(Entity entity){
        for(NPC npc: objects){
            //We check locations because this is checked in entity spawn, before the entity actually spawns
            if(npc.getLocation().equals(entity.getLocation())) {
                System.out.println("valid");
                return Optional.of(npc);
            }
        }
        return Optional.empty();
    }

    public boolean isValidNPC(Entity entity){
        return entity.isValid() && !entity.isDead();
    }

    public void clearAll() {
        for(NPC entity: objects){
            entity.remove();
        }
    }
}
