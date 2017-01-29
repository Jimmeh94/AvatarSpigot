package avatar.util.text;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Messager {

    public static void sendMessage(Player player, String text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            player.sendMessage(prefix.get().getText() + text);
        } else {
            player.sendMessage(text);
        }
        player.sendMessage(" ");
    }

    public static void sendActionBarMessage(Player player, String text){
        player.sendMessage(ChatTypes.ACTION_BAR, text);
    }

    public static void sendTitleMessage(Player player, String text){player.sendTitle(Title.of(text));}

    public static void sendTitleAndSubTitle(Player player, String title, String subtitle){player.sendTitle(Title.of(title, subtitle));}

    public static void broadcastMessage(String text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            text = prefix.get().getText() + text;
        }
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(text);
            p.sendMessage(" ");
        }

    }

    public enum Prefix{
        ERROR(ChatColor.RED + ChatColor.BOLD.toString() + "[" + AltCodes.THICK_X.getSign() + "] "),
        INFO(ChatColor.GOLD + ChatColor.BOLD.toString() + "[!] "),
        SUCCESS(ChatColor.GREEN + ChatColor.BOLD.toString() + "[" + AltCodes.CHECKMARK.getSign() + "] ");

        private String text;

        Prefix(String text){this.text = text;}

        public String getText() {
            return text;
        }
    }

}
