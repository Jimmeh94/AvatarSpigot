package avatar.game.ability.property.collision;

import avatar.Avatar;
import avatar.util.misc.BlockReplacement;
import avatar.util.particles.effectData.EffectData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

public class CallbackDestroyBlocks implements CollisionCallback {

    private EffectData effectData;

    public CallbackDestroyBlocks(EffectData effectData){this.effectData = effectData;}

    @Override
    public boolean doCollision(CollisionBehavior collisionBehavior) {
        if(collisionBehavior instanceof CollisionBehavior.CollideOnBlock) {
            CollisionBehavior.CollideOnBlock collideOnBlock = (CollisionBehavior.CollideOnBlock) collisionBehavior;
            for (Block block : collideOnBlock.getCollidedBlocks()) {
                effectData.setDisplayAt(block.getLocation());
                effectData.getActiveDisplayProfile().setData(new MaterialData(block.getType()));
                effectData.display();
                Avatar.INSTANCE.getBlockManager().add(new BlockReplacement(block, 7));
                block.setType(Material.AIR);
            }
        }
        return false;
    }
}
