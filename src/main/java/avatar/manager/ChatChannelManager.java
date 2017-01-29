package avatar.manager;

import avatar.game.chat.channel.ChatChannel;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.Optional;

public class ChatChannelManager extends Manager<ChatChannel> {

    public boolean isKeyAvailable(String key){
        return !findChannel(key).isPresent();
    }

    private Optional<ChatChannel> findChannel(String key){
        for(ChatChannel chatChannel: objects){
            if(chatChannel.getKey().equalsIgnoreCase(key)){
                return Optional.of(chatChannel);
            }
        }
        return Optional.empty();
    }

    public void setToDefault(UserPlayer userPlayer){
        userPlayer.setChatChannel(ChatChannel.GLOBAL);
    }

    public void setChannel(UserPlayer userPlayer, String key){
        Optional<ChatChannel> channel = findChannel(key);

        if(channel.isPresent() && channel.get() != userPlayer.getChatChannel()){
            userPlayer.setChatChannel(channel.get());
            Messager.sendMessage(userPlayer.getPlayer(), ChatColor.GRAY + "Moved to chat channel: " + channel.get().getKey(), Optional.of(Messager.Prefix.SUCCESS));
        } else {
            Messager.sendMessage(userPlayer.getPlayer(), ChatColor.GRAY + "The channel doesn't exist or you are already in it: " + key, Optional.of(Messager.Prefix.ERROR));
        }
    }

}
