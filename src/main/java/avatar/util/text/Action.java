package avatar.util.text;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Action {

    public static void send(Player player, String text){
        if(player != null && text != null) {
            String jsonRepresentation = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', text) + "\"}";
            IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(jsonRepresentation);
            PacketPlayOutChat actionPacket = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionPacket);
        }
    }

}
