package avatar.game.area;

import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.util.misc.PlayerHiding;

import java.util.ArrayList;
import java.util.List;

public abstract class Instance {

    private List<User> members;
    private List<Ability> abilities;
    private int capacity;
    protected Area area;

    public Instance(Area area){
        this(area, -1);
    }

    public Instance(Area area, int capacity){
        members = new ArrayList<>();
        abilities = new ArrayList<>();
        this.capacity = capacity;
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void addUser(User user){
        if(capacity > -1){
            if(members.size() == capacity)
                return;
        }
        members.add(user);

        if(user.isPlayer()){
            PlayerHiding.instance(this);
        }
    }

    public void removeUser(User user){
        members.remove(user);

        if(user.isPlayer()){
            PlayerHiding.instance(this);
        }
    }

    public boolean hasUser(User user){
        return members.contains(user);
    }

    public void addAbility(Ability user){
        abilities.add(user);
    }

    public void removeAbility(Ability user){
        abilities.remove(user);
    }

    public boolean hasAbility(Ability user){
        return abilities.contains(user);
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }
}
