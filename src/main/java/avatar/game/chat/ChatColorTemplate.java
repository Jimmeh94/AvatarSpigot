package avatar.game.chat;

import org.bukkit.ChatColor;

public class ChatColorTemplate{

    public static final ChatColorTemplate GRAY = new ChatColorTemplate(ChatColor.GRAY, ChatColor.GRAY, ChatColor.GRAY);

    private ChatColor prefix, name, message;

    ChatColorTemplate(ChatColor... colors){
        this.prefix = colors[0];
        this.name = colors[1];
        this.message = colors[2];
    }

    public ChatColor getPrefix() {
        return prefix;
    }

    public ChatColor getName() {
        return name;
    }

    public ChatColor getMessage() {
        return message;
    }
}
