package avatar.util.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class BlockReplacement {

    private Material replace;
    private Location where;
    private BlockState blockState;
    private int howLong;
    private long whenReplaced;

    public BlockReplacement(Block block, int howLong){
        replace = block.getType();
        where = block.getLocation();
        blockState = block.getState();
        this.howLong = howLong;
        whenReplaced = System.currentTimeMillis();
    }

    public void replace(){
        where.getBlock().setType(replace);
    }

    public boolean shouldReplace(){
        return ((System.currentTimeMillis() - whenReplaced)/1000 >= howLong);
    }

}
