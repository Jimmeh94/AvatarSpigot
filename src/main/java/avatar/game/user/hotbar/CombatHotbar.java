package avatar.game.user.hotbar;

import avatar.game.ability.abilities.fire.Fireball;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class CombatHotbar extends HotbarSetup {

    public CombatHotbar(UserPlayer player) {
        super(player);
    }

    @Override
    public void apply(){
        super.apply();

        Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Entering combat mode!", Optional.of(Messager.Prefix.INFO));
    }

    @Override
    protected void setup() {
        mapping.put(4, Fireball.getRepresentation(owner));

        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Leave Combat Mode");
        itemStack.setItemMeta(itemMeta);
        mapping.put(8, itemStack);
    }

    @Override
    public void handle(int slot) {
        switch (slot){
            case 0:
                break;
            case 2:
                break;
            case 4: new Fireball(owner, 1, 5L).fire();
                break;
            case 6:
                break;
            case 8: owner.swapHotbars();
        }
    }
}
