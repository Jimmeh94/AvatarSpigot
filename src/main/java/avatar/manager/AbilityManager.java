package avatar.manager;

import avatar.game.ability.type.Ability;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager extends Manager<Ability> {

    public List<Ability> getNearbyAbilitiesInChunk(Ability ability){
        List<Ability> give = new ArrayList<>();

        for(Ability a: objects){
            if(a == ability)
                continue;
            else if(a.getLocationChunk().equals(ability.getLocationChunk())){
                give.add(a);
            }
        }

        return give;
    }

}
