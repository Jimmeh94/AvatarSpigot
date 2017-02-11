package avatar.game.user;

import avatar.util.particles.ParticleUtils;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.Optional;

public class Settings {

    private boolean useHologramMenus;
    private ParticleUtils.ParticleModifier particleModifier;
    private UserPlayer owner;

    public Settings(UserPlayer owner){
        this.useHologramMenus = true;
        this.owner = owner;
        particleModifier = ParticleUtils.ParticleModifier.NORMAL;
    }

    public boolean isUseHologramMenus() {
        return useHologramMenus;
    }

    public ParticleUtils.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    public void setParticleModifier(ParticleUtils.ParticleModifier particleModifier) {
        Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Particle settings set to " + particleModifier.toString(), Optional.of(Messager.Prefix.SUCCESS));
        this.particleModifier = particleModifier;
    }

    public void toggleHologramMenus() {
        if(useHologramMenus){
            useHologramMenus = false;
        } else useHologramMenus = true;

        if(useHologramMenus)
            Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Hologram menus will now be used when possible!", Optional.of(Messager.Prefix.SUCCESS));
        else Messager.sendMessage(owner.getPlayer(), ChatColor.GRAY + "Normal menus will now be used!", Optional.of(Messager.Prefix.SUCCESS));
    }
}
