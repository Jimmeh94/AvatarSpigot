package avatar.game.user.menus;

import avatar.game.entity.hologram.menu.ParticleMenu;
import avatar.game.user.UserPlayer;
import avatar.util.directional.MenuGeneration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsMenu {

    public static void handle(int slot, UserPlayer player){
        switch (slot){
            case 0: {
                if(player.getSettings().isUseHologramMenus() && MenuGeneration.hasEnoughRoom(player.getPlayer())){
                    player.getPlayer().closeInventory();
                    new ParticleMenu(player, 5);
                } else new ParticleModifierMenu(player);
            }
                break;
            case 2: {
                player.getSettings().toggleHologramMenus();
                player.getPlayer().getOpenInventory().getTopInventory().setItem(2, getHologramDisplay(player));
                player.getPlayer().updateInventory();
            }
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
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(ChatColor.GOLD + "Particle Settings");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Currently: " + ChatColor.WHITE + display.getSettings().getParticleModifier().toString());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.addItem(itemStack.clone());

        inventory.setItem(2, getHologramDisplay(display));

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(6, itemStack.clone());

        itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(8, itemStack.clone());

        display.getPlayer().openInventory(inventory);
    }

    private static ItemStack getHologramDisplay(UserPlayer display){
        ItemStack itemStack;
        ItemMeta itemMeta;
        List<String> lore;

        if(display.getSettings().isUseHologramMenus()){
            itemStack = new ItemStack(Material.INK_SACK, 1, (byte)10);
        } else itemStack = new ItemStack(Material.CLAY_BALL);
        itemMeta = itemStack.getItemMeta();
        lore = new ArrayList<>();
        itemMeta.setDisplayName(ChatColor.GOLD + "Hologram Menus");
        lore.add(" ");
        lore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "If enabled, hologram menus will be used instead");
        lore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "of normal menus, where possible");
        lore.add(" ");
        if(display.getSettings().isUseHologramMenus()){
            lore.add(ChatColor.GRAY + "Currently: " + ChatColor.GREEN + "Enabled");
        } else {
            lore.add(ChatColor.GRAY + "Currently: " + ChatColor.RED + "Disabled");
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
