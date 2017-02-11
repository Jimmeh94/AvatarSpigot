package avatar.game.ability;

import avatar.game.ability.type.AbilityContainer;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.hotbar.CombatHotbar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserAbilityManager {

    private Map<Integer, AbilityContainer> mapping = new HashMap<>();
    private CombatHotbar combatHotbar;
    private User owner;

    public UserAbilityManager(User owner){
        this.owner = owner;

        if(owner.isPlayer()){
            combatHotbar = new CombatHotbar(((UserPlayer)owner));
        }
    }

    public void add(int slot, AbilityContainer ability){
        abilityUsedCheck(ability);

        mapping.put(slot, ability);
        combatHotbar.add(slot, ability.getItemRepresentation());
    }

    private void abilityUsedCheck(AbilityContainer ability){
        Iterator<Map.Entry<Integer, AbilityContainer>> iterator = mapping.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, AbilityContainer> entry = iterator.next();
            if(entry.getValue().getClass().getCanonicalName().equals(ability.getClass().getCanonicalName())){
                remove(entry.getKey());
            }
        }
    }

    public void remove(int slot){
        mapping.remove(slot);
        combatHotbar.remove(slot);
    }

    public CombatHotbar getCombatHotbar() {
        return combatHotbar;
    }

    public void fire(int slot) {
        if(mapping.containsKey(slot)){
            mapping.get(slot).fire();
        }
    }
}
