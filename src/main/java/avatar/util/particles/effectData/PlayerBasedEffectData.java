package avatar.util.particles.effectData;

import avatar.game.user.UserPlayer;
import avatar.util.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Use this to display particles to ONE individual player
 */
public class PlayerBasedEffectData extends EffectData{

    private UserPlayer owner;

    public PlayerBasedEffectData(Location center, UserPlayer owner, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);

        this.owner = owner;
    }

    public PlayerBasedEffectData(Location center, Location displayAt, UserPlayer owner, DisplayProfile... displayProfiles) {
        super(center, displayAt, displayProfiles);

        this.owner = owner;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    @Override
    public void display(){
        ParticleUtils.displayParticles(this, owner);
    }

    public void display(List<Player> players){
        ParticleUtils.displayParticles(this, players);
    }
}
