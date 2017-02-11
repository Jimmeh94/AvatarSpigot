package avatar.game.user.hotbar;

import avatar.game.user.UserPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CombatHotbar extends HotbarSetup {

    public CombatHotbar(UserPlayer player) {
        super(player);
    }

    @Override
    protected void setup() {
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
            case 4: owner.getUserAbilityManager().fire(slot);
                break;
            case 6:
                break;
            case 8: owner.swapHotbars();
        }
    }

    public void add(int slot, ItemStack itemStack){
        mapping.put(slot, itemStack);
    }

    public void remove(int slot){
        mapping.remove(slot);
        apply();
    }
}
