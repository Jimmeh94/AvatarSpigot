package avatar.game.area;

import avatar.game.chat.channel.ChatChannel;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum AreaReferences {

    //List children first, then parent after

    //Children of GLOBAL ----
    SOUTHERN_BRIDGES(new Area.AreaRectangle(new Location(Bukkit.getWorlds().get(0), -828, 1, 360), new Location(Bukkit.getWorlds().get(0), -794, 1, 370), 50),
            "Southern Bridges", null, null),

    //Children of SPAWN ----
    SPAWN(new Area.AreaCircle(new Location(Bukkit.getWorlds().get(0), -811.5, 4, 303.5), 11, 256),
            "Spawn", new ChatChannel(ChatChannel.Type.AREA, "Spawn"), null),
    //----------------------

    GLOBAL(null, "Global Wilderness", ChatChannel.GLOBAL, SPAWN, SOUTHERN_BRIDGES);
    //--------------------

    private Area.AreaShape shape;
    private String displayName;
    private ChatChannel chatChannel;
    private AreaReferences[] children;

    AreaReferences(Area.AreaShape shape, String displayName, ChatChannel chatChannel, AreaReferences... children){
        this.shape = shape;
        this.displayName = displayName;
        this.chatChannel = chatChannel;
        this.children = children;
    }

    public Area.AreaShape getShape() {
        return shape;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AreaReferences[] getChildren() {
        return children;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }
}
