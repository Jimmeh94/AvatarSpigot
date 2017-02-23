package avatar.util.particles.effectData;

import avatar.Avatar;
import avatar.util.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationBasedEffectData extends EffectData {
    
    private double radius;

    public LocationBasedEffectData(Location center, double radius, DisplayProfile... displayProfiles) {
        super(center, displayProfiles);
        
        this.radius = radius;
    }

    public LocationBasedEffectData(Location center, Location displayAt, double radius, DisplayProfile... displayProfiles) {
        super(center, displayAt, displayProfiles);

        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void display(){
        List<Entity> entities = new ArrayList<>();
        List<Player> users = new ArrayList<>();
        Entity origin = null;

        //find nearest entity
        for(Entity entity: getDisplayAt().getChunk().getEntities()){
            if(entity.getLocation().distance(getDisplayAt()) > radius)
                continue;
            if(origin == null){
                origin = entity;
            } else {
                if(origin.getLocation().distance(getDisplayAt()) > entity.getLocation().distance(getDisplayAt())){
                    origin = entity;
                }
            }
        }

        //get nearby entities
        if(origin == null){
            return;
        } else {
            entities.addAll(origin.getNearbyEntities(radius, radius, radius));
        }

        for (Entity entity: entities) {
            if(!(entity instanceof Player)){
                continue;
            }
            users.add(Avatar.INSTANCE.getUserManager().findUserPlayer(entity).get().getPlayer());
        }

        ParticleUtils.displayParticles(this, users);
    }
}
