package avatar.game.area;

import avatar.game.chat.channel.ChatChannel;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum AreaReferences {

    //List children first, then parent after

    //Children of GLOBAL
    TEST(new Area.AreaCircle(new Location(Bukkit.getWorlds().get(0), 50, 50, 50), 10, 256), "Test Area", null),
    TEST2(new Area.AreaRectangle(new Location(Bukkit.getWorlds().get(0), 75, 50, 75), new Location(Bukkit.getWorlds().get(0), 100, 50, 100), 256),
            "Test 2", new ChatChannel(ChatChannel.Type.AREA, "Test2")),
    GLOBAL(null, "Global Wilderness", ChatChannel.GLOBAL, TEST, TEST2);
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

    public AreaReferences setShape(Area.AreaShape shape) {
        this.shape = shape;
        return this;
    }

    public AreaReferences setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public AreaReferences setChildren(AreaReferences[] children) {
        this.children = children;
        return this;
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
