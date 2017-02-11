package avatar.game.ability;

import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.hotbar.CombatHotbar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserAbilityManager {

    private Map<Integer, Ability> mapping = new HashMap<>();
    private CombatHotbar combatHotbar;
    private User owner;

    public UserAbilityManager(User owner){
        this.owner = owner;

        if(owner.isPlayer()){
            combatHotbar = new CombatHotbar(((UserPlayer)owner));
        }
    }

    public void add(int slot, Ability ability){
        abilityUsedCheck(ability);

        mapping.put(slot, ability);
        combatHotbar.add(slot, ability.getItemRepresentation());
    }

    private void abilityUsedCheck(Ability ability){
        Iterator<Map.Entry<Integer, Ability>> iterator = mapping.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Ability> entry = iterator.next();
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
