package avatar.game.user.menus;

import avatar.game.user.UserPlayer;
import avatar.util.particles.ParticleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParticleModifierMenu {

    public static void handle(int slot, UserPlayer player){
        switch (slot){
            case 0: player.getSettings().setParticleModifier(ParticleUtils.ParticleModifier.LOW);
                break;
            case 2: player.getSettings().setParticleModifier(ParticleUtils.ParticleModifier.MEDIUM);
                break;
            case 4: player.getSettings().setParticleModifier(ParticleUtils.ParticleModifier.NORMAL);
                break;
            case 6: player.getSettings().setParticleModifier(ParticleUtils.ParticleModifier.HIGH);
                break;
            case 8: player.getSettings().setParticleModifier(ParticleUtils.ParticleModifier.EXTREME);
        }
    }

    /**
     * LOW(0.25),
     MEDIUM(0.5),
     NORMAL(1.0),
     HIGH(1.25),
     EXTREME(1.5);
     */

    public ParticleModifierMenu(UserPlayer display){
        Inventory inventory = Bukkit.createInventory(null, 9, "Particle Settings");

        ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (byte)8);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Low (25%)");
        itemStack.setItemMeta(itemMeta);
        inventory.addItem(itemStack.clone());

        itemStack = new ItemStack(Material.INK_SACK, 1, (byte)7);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Medium (50%)");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(2, itemStack.clone());

        itemStack = new ItemStack(Material.INK_SACK, 1, (byte)11);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Default (100%)");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack.clone());

        itemStack = new ItemStack(Material.INK_SACK, 1, (byte)14);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "High (125%)");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(6, itemStack.clone());

        itemStack = new ItemStack(Material.INK_SACK, 1, (byte)1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Extreme (150%)");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(8, itemStack.clone());

        display.getPlayer().openInventory(inventory);
    }

}
