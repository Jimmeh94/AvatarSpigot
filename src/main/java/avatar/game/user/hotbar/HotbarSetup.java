package avatar.game.user.hotbar;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class HotbarSetup {

    protected Map<Integer, ItemStack> mapping;

    protected abstract void setup(Player player);

    public HotbarSetup(Player player){
        mapping = new HashMap<>();
        setup(player);
        apply(player);
    }

    private void apply(Player player){
        for(Map.Entry<Integer, ItemStack> entry: mapping.entrySet()){
            player.getInventory().setItem(entry.getKey(), entry.getValue());
        }
    }

}
