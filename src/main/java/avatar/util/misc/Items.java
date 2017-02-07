package avatar.util.misc;

import avatar.game.user.UserPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Optional;

public enum Items {

    /**
     * Transparent Blocks
     * Coin bag full: dark_oak_leaves
     * Coin bag empty: something else
     * Scroll: acacia leaves
     */

    SCROLL(Material.LEAVES_2, (byte)4),
    COIN_BAG_FULL(Material.LEAVES_2, (byte)5);

    private Material material;
    private byte data;

    Items(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public boolean equals(Material material, byte data){
        return material == this.material && this.data == data;
    }

    public static Optional<Items> find(Material type, byte data) {
        for(Items items: Items.values()){
            if(items.equals(type, data)){
                return Optional.of(items);
            }
        }
        return Optional.empty();
    }

    /**
     * Use this to give gold, info from a scroll, etc
     */
    public interface ItemCallback{
        void handle(UserPlayer userPlayer);
    }

    public static String getScrollPrefixMessage(){
        return ChatColor.GRAY + ChatColor.ITALIC.toString() + "     The scroll reads...     ";
    }
}
