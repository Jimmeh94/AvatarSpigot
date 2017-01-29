package avatar.game.chat.channel;

import avatar.Avatar;
import avatar.game.user.UserPlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class ChatChannel {

    public static final ChatChannel GLOBAL = new ChatChannel(Type.GLOBAL, "Global");

    private String key;
    private Type type;
    protected Set<UserPlayer> members = Collections.newSetFromMap(new WeakHashMap<>());

    public ChatChannel(Type type, String key){
        this.type = type;
        this.key = key;

        Avatar.INSTANCE.getChatChannelManager().add(this);
    }

    public ChatChannel(Type type, String key, UserPlayer owner) {
        this.type = type;
        this.key = key;

        Avatar.INSTANCE.getChatChannelManager().add(this);
        Avatar.INSTANCE.getChatChannelManager().setChannel(owner, this.key);
    }

    public ChatChannel(Type type, String key, Set<UserPlayer> members) {
        this.type = type;
        this.members.addAll(members);
        this.key = key;

        Avatar.INSTANCE.getChatChannelManager().add(this);
        for(UserPlayer messageReceiver: members){
            Avatar.INSTANCE.getChatChannelManager().setChannel(messageReceiver, this.key);
        }
    }

    public void displayMessage(String message){
        for(UserPlayer userPlayer: members){
            userPlayer.getPlayer().sendMessage(message);
        }
    }

    public Type getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public boolean hasMember(UserPlayer userPlayer){return members.contains(userPlayer);}

    public Collection<UserPlayer> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }

    public boolean addMember(UserPlayer member) {
        if(members.contains(member))
            return false;
        return this.members.add(member);
    }

    public void removeMember(UserPlayer member) {
        this.members.remove(member);
        if(members.size() == 0 && this.type != Type.GLOBAL && this.type != Type.AREA){
            Avatar.INSTANCE.getChatChannelManager().remove(this);
        }
    }

    public enum Type{
        GLOBAL,
        PARTY,
        AREA
    }
}
