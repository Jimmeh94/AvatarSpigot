package avatar.game.user.hotbar;

import avatar.game.user.UserPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class HotbarSetup {

    protected Map<Integer, ItemStack> mapping;
    protected UserPlayer owner;

    protected abstract void setup();

    public HotbarSetup(UserPlayer player){
        mapping = new HashMap<>();
        this.owner = player;
        setup();
        apply();
    }

    private void apply(){
        for(Map.Entry<Integer, ItemStack> entry: mapping.entrySet()){
            owner.getPlayer().getInventory().setItem(entry.getKey(), entry.getValue());
        }
    }

    public abstract void handle(int slot);

}
