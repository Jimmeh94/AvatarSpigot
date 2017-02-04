package avatar.game.user.hotbar;

import avatar.game.user.UserPlayer;
import avatar.game.user.menus.SettingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultHotbar extends HotbarSetup {

    /**
     * 0: character (skills, stats, inventory, etc)
     * 1:
     * 2: journal (quests and map)
     * 3:
     * 4: friends (party system)
     * 5:
     * 6: settings (game settings)
     * 7:
     * 8: pvp mode (go into combat mode)
     */

    public DefaultHotbar(UserPlayer player) {
        super(player);
    }

    @Override
    public void handle(int slot) {
        switch (slot){
            case 0:
                break;
            case 2: owner.getQuestManager().displayQuestMenu();
                break;
            case 4:
                break;
            case 6: new SettingsMenu(owner);
                break;
            case 8: ;
        }
    }

    @Override
    protected void setup() {
        ItemStack itemStack = new ItemStack(Material.ARMOR_STAND);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + owner.getPlayer().getDisplayName());
        itemStack.setItemMeta(itemMeta);
        mapping.put(0, itemStack.clone());

        itemStack = new ItemStack(Material.BOOK);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Journal");
        itemStack.setItemMeta(itemMeta);
        mapping.put(2, itemStack);

        itemStack = new ItemStack(Material.SKULL_ITEM);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Friends");
        itemStack.setItemMeta(itemMeta);
        mapping.put(4, itemStack);

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Settings");
        itemStack.setItemMeta(itemMeta);
        mapping.put(6, itemStack);

        itemStack = new ItemStack(Material.STONE_SWORD);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Enter Combat Mode");
        itemStack.setItemMeta(itemMeta);
        mapping.put(8, itemStack);
    }
}
