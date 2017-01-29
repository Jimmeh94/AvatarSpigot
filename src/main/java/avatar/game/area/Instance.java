package avatar.game.area;

import avatar.game.ability.type.Ability;
import avatar.game.user.User;

import java.util.ArrayList;
import java.util.List;

public class Instance {

    private List<User> members;
    private List<Ability> abilities;
    private int capacity;

    public Instance(){
        this(-1);
    }

    public Instance(int capacity){
        members = new ArrayList<>();
        abilities = new ArrayList<>();
        this.capacity = capacity;
    }

    public void addUser(User user){
        members.add(user);

        //make only visible to other users
    }

    public void removeUser(User user){
        members.remove(user);

        //make visible to all others besides this.members members
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
