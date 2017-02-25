package avatar.manager;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.entity.npc.NPC;
import avatar.game.entity.npc.spawn.NPCConcernedCitizen;
import avatar.game.entity.npc.spawn.NPCOldMan;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
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
                return Optional.of(npc);
            } else if(npc.getEntity() != null && npc.getEntity().getUniqueId().equals(entity.getUniqueId())){
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

    public List<NPC> findEntitiesWithin(AreaReferences bound, Class<? extends NPC>... type){
        return findEntitiesWithin(Avatar.INSTANCE.getAreaManager().getAreaByReference(bound).get(), type);
    }

    public List<NPC> findEntitiesWithin(Area bound, Class<? extends NPC>... type){
        List<NPC> give = new ArrayList<>();
        for(NPC npc: objects){
            if(npc.getUser().getPresentArea() == bound) {
                for (Class<? extends NPC> temp : type) {
                    if (npc.getClass().getCanonicalName().equals(temp.getCanonicalName())) {
                        give.add(npc);
                    }
                }
            }
        }
        return give;
    }
}
