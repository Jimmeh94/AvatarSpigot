package avatar.game.ability.type;

import avatar.game.ability.property.AbilityProperty;
import avatar.game.user.User;
import avatar.util.text.AltCodes;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AbilityContainer {

    protected User owner;

    public AbilityContainer(User owner){
        this.owner = owner;
    }

    protected abstract Ability getAbility();
    public abstract ItemStack getItemRepresentation();

    public User getOwner() {
        return owner;
    }

    public void fire() {
        getAbility().fire();
    }

    protected List<String> getItemLore(AbilityProperty... properties){
        List<String> give = new ArrayList<>();
        for(AbilityProperty property: properties){
            give.add(ChatColor.GOLD + AltCodes.BULLET_POINT.getSign() + " " + ChatColor.GRAY + property.getLore());
        }
        return give;
    }
}
