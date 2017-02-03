package avatar.manager;

import avatar.Avatar;
import avatar.game.user.combatlog.EntityCombatLogger;
import avatar.game.user.combatlog.entries.CombatEntry;
import avatar.game.user.User;

import java.util.Optional;
import java.util.UUID;

public class CombatManager {

    /*
     * Add method to go through the combat entries once entity has died and determine who did what percentage
     */

    public void damageEntity(UUID victim, CombatEntry entry){
        //go through all info's entries, find match of entry's owner, if not make new entrycontainer, add entry
        EntityCombatLogger entityInfo;
        Optional<User> user = Avatar.INSTANCE.getUserManager().find(victim);

        if(user.isPresent()){
            entityInfo = user.get().getCombatLogger();

            if(!entityInfo.hasEntryContainer(entry.getOwner())){
                entityInfo.addEntryContainer(entry.getOwner());
            }

            entityInfo.addEntry(entry);
        }
    }

}
