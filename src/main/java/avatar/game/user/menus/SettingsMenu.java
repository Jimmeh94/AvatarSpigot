package avatar.game.user.menus;

import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsMenu {

    public static void handle(int slot, UserPlayer player){
        switch (slot){
            case 0: new ParticleModifierMenu(player);
                break;
            case 2:
                break;
            case 4:
                break;
            case 6:
                break;
            case 8:
        }
    }

    public SettingsMenu(UserPlayer display){
        Inventory inventory = Bukkit.createInventory(null, 9, "Settings");

        ItemStack itemStack = new ItemStack(Material.CLAY_BALL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Particle Settings");
        itemStack.setItemMeta(itemMeta);
        inventory.addItem(itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(2, itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(6, itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(8, itemStack.clone());

        display.getPlayer().openInventory(inventory);
    }

}
