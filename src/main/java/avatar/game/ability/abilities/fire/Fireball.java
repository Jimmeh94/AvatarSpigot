package avatar.game.ability.abilities.fire;

import avatar.game.ability.property.*;
import avatar.game.ability.property.collision.CallbackDestroyBlocks;
import avatar.game.ability.property.collision.CollisionBehavior;
import avatar.game.ability.type.AbilityTargetingLocation;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.stats.Stats;
import avatar.util.directional.LocationUtils;
import avatar.util.directional.Vector;
import avatar.util.particles.ParticleUtils;
import avatar.util.particles.effectData.DisplayProfile;
import avatar.util.particles.effectData.EffectData;
import avatar.util.particles.effectData.PlayerBasedEffectData;
import avatar.util.particles.effects.SphereEffect;
import avatar.util.text.AltCodes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Fireball extends AbilityTargetingLocation {

    private SphereEffect sphereEffect;

    public Fireball(User owner,double speed, long interval) {
        super(owner, speed, interval, 5);
    }

    @Override
    protected void loadProperties(List<AbilityProperty> properties) {
        properties.add(new AbilityPropertyCost(null, this, 5, Stats.StatType.CHI));
        properties.add(new AbilityPropertyDuration(null, this, 100));
        properties.add(new AbilityPropertyBoundRange(null, this, 50));
        properties.add(new AbilityPropertyCollisionLogic.CubeCollisionLogic(null, this, 1, 1, 1,
                new CollisionBehavior.CollideOnBlock(this, null,
                        new CallbackDestroyBlocks(
                                new PlayerBasedEffectData(getCenter(), (UserPlayer) owner, DisplayProfile.builder().particle(Particle.BLOCK_CRACK).amount(15).build())
                        )
                )));
    }

    @Override
    public void setPrefireData(){
        super.setPrefireData();

        sphereEffect = new SphereEffect(effectData, ParticleUtils.Loaded.SPHERE_025R, 0L, 0L, -1);
    }

    @Override
    protected EffectData setEffectData() {
        return new PlayerBasedEffectData(getCenter().clone(), (UserPlayer)owner, DisplayProfile.builder().particle(Particle.FLAME).amount(50).build(),
                DisplayProfile.builder().particle(Particle.FLAME).particleOffsets(0.15, 0.15, 0.15).amount(75).build(),
                DisplayProfile.builder().particle(Particle.SMOKE_LARGE).particleOffsets(0.2, 0.2, 0.2).amount(100).build());
    }

    @Override
    protected void display() {
        //display ball
        //display fire trail
        //display smoke trail

        effectData.setActiveDisplayProfile(effectData.getDisplayProfiles()[0]);
        sphereEffect.play();

        for(int i = 0; i < 5; i++){
            if(history[i] == null)
                continue;
            Vector.Vector3D vector3D = LocationUtils.getOffsetBetween(effectData.getDisplayAt(), history[i]);
            effectData.setActiveDisplayProfile(effectData.getDisplayProfiles()[i == 0 ? 1 : 2]);
            effectData.adjustDisplayProfile(vector3D);
            effectData.display();
        }
    }

    @Override
    public ItemStack getItemRepresentation(){
        ItemStack give = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta itemMeta = give.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Element: " + ChatColor.RED + "Fire");
        lore.add(" ");
        lore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Fires a single fireball towards the target location");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Properties:");
        lore.add(ChatColor.GOLD + AltCodes.BULLET_POINT.getSign() + " " + ChatColor.GRAY + "Cost: " + ChatColor.AQUA + "5 energy");
        lore.add(ChatColor.GOLD + AltCodes.BULLET_POINT.getSign() + " " + ChatColor.GRAY + "Duration: 25 seconds");
        lore.add(ChatColor.GOLD + AltCodes.BULLET_POINT.getSign() + " " + ChatColor.GRAY + "Range: 50");

        itemMeta.setLore(lore);
        itemMeta.setDisplayName(ChatColor.RED + "Fireball");
        give.setItemMeta(itemMeta);
        return give;
    }
}
