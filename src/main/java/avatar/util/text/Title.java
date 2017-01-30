package avatar.util.text;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {

    public static void sendBoth(Player player, String title, String sub, int fadeIn, int stay, int fadeOut){
        String jsonRepresentation = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}";
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(jsonRepresentation);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(Type.TITLE.getNmsAction(), chatBaseComponent, fadeIn, stay, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);

        jsonRepresentation = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', sub) + "\"}";
        chatBaseComponent = IChatBaseComponent.ChatSerializer.a(jsonRepresentation);
        titlePacket = new PacketPlayOutTitle(Type.SUBTITLE.getNmsAction(), chatBaseComponent, fadeIn, stay, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
    }

    public static void send(Player player, Type type, String text, int fadeIn, int stay, int fadeOut) {
        String jsonRepresentation = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', text) + "\"}";
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(jsonRepresentation);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(type.getNmsAction(), chatBaseComponent, fadeIn, stay, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
    }

    public enum Type {
        TITLE(PacketPlayOutTitle.EnumTitleAction.TITLE),
        SUBTITLE(PacketPlayOutTitle.EnumTitleAction.SUBTITLE);

        private PacketPlayOutTitle.EnumTitleAction nmsAction;

        Type(PacketPlayOutTitle.EnumTitleAction nmsAction) {
            this.nmsAction = nmsAction;
        }

        PacketPlayOutTitle.EnumTitleAction getNmsAction() {
            return this.nmsAction;
        }
    }

}
