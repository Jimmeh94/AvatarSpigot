package avatar.util.particles.effectData;

import avatar.game.area.Area;
import avatar.game.user.UserPlayer;
import avatar.util.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Use this to display particles to all players in an area
 * If an owner is passed, the particles will be displayed to that owner's instance
 */
public class AreaBasedEffectData extends EffectData {

    private Area displayArea;
    private Optional<UserPlayer> owner;

    public AreaBasedEffectData(Location center, Area displayArea, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);

        this.displayArea = displayArea;
        owner = Optional.empty();
    }

    public AreaBasedEffectData(Location center, Area displayArea, Optional<UserPlayer> owner, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);

        this.displayArea = displayArea;
        this.owner = owner;
    }

    public AreaBasedEffectData(Location center, UserPlayer owner, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);

        this.displayArea = owner.getPresentArea();
        this.owner = Optional.of(owner);
    }

    public AreaBasedEffectData(Location center, Location displayAt, Area displayArea, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);

        this.displayArea = displayArea;
        owner = Optional.empty();
    }

    public AreaBasedEffectData(Location center, Location displayAt, Area displayArea, Optional<UserPlayer> owner, DisplayProfile... displayProfiles) {
        super(center, displayAt, displayProfiles);

        this.displayArea = displayArea;
        this.owner = owner;
    }

    public AreaBasedEffectData(Location center, Location displayAt, UserPlayer owner, DisplayProfile... displayProfiles) {
        super(center, displayAt, displayProfiles);

        this.displayArea = owner.getPresentArea();
        this.owner = Optional.of(owner);
    }

    @Override
    public void display(){
        Optional<Area> temp;
        List<Player> players = new ArrayList<>();

        for (UserPlayer player : displayArea.getPlayersFromMembers()) {
            players.add(player.getPlayer());
        }
        ParticleUtils.displayParticles(this, players);
    }

    public Area getDisplayArea() {
        return displayArea;
    }

    public Optional<UserPlayer> getOwner() {
        return owner;
    }
}
