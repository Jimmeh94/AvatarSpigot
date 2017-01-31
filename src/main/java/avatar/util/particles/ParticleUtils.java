package avatar.util.particles;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.Instance;
import avatar.game.user.UserPlayer;
import avatar.util.particles.effects.EffectData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class ParticleUtils {

    public static class PlayerBased {

        public static void displayParticles(EffectData effectData){
            displayParticles(effectData, Arrays.asList(((UserPlayer)effectData.getOwner()).getPlayer()));
        }

        public static void displayParticles(EffectData effectData, Collection<Player> players){
            Optional<Instance> instance = Optional.empty();
            Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(effectData.getOwner().getEntity().getLocation());
            Optional<Area> temp;

            if(area.isPresent()){
                if(area.get().isInstanced(effectData.getOwner())){
                    instance = area.get().getInstance(effectData.getOwner());
                }
            }

            for (Player player : players) {
                Optional<UserPlayer> user = Avatar.INSTANCE.getUserManager().findUserPlayer(player);
                if(!user.isPresent()){
                    continue;
                }

                if(instance.isPresent()){
                    temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(user.get().getPlayer().getLocation());
                    if(temp.isPresent()){
                        if(temp.get().isInstanced(user.get())){
                           if(temp.get().getInstance(user.get()).get() != instance.get()) {
                               continue;
                           }
                        } else continue;
                    }
                }

                display(effectData, user.get());
            }
        }
    }

    public static class AreaBased{
        public static void displayParticles(EffectData effectData) {
            Optional<Instance> instance = Optional.empty();
            Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(effectData.getOwner().getEntity().getLocation());
            Optional<Area> temp;

            if(area.get().isInstanced(effectData.getOwner())){
                instance = area.get().getInstance(effectData.getOwner());
            }

            for (UserPlayer player : effectData.getDisplayArea().getPlayersFromMembers()) {
                if(instance.isPresent()){
                    temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(player.getPlayer().getLocation());
                    if(temp.isPresent()){
                        if(temp.get().isInstanced(player)){
                            if(temp.get().getInstance(player).get() != instance.get()) {
                                continue;
                            }
                        } else continue;
                    }
                }

                display(effectData, player);
            }
        }
    }

    public static class LocationBased{

        public static void displayParticles(EffectData effectData) {
            List<Entity> entities = new ArrayList<>();
            Entity origin = null;

            //find nearest entity
            for(Entity entity: effectData.getDisplayAt().getChunk().getEntities()){
                if(entity.getLocation().distance(effectData.getDisplayAt()) > effectData.getDisplayRadius())
                    continue;
                if(origin == null){
                    origin = entity;
                } else {
                    if(origin.getLocation().distance(effectData.getDisplayAt()) > entity.getLocation().distance(effectData.getDisplayAt())){
                        origin = entity;
                    }
                }
            }

            //get nearby entities
            if(origin == null){
                return;
            } else {
                entities.addAll(origin.getNearbyEntities(effectData.getDisplayRadius(), effectData.getDisplayRadius(), effectData.getDisplayRadius()));
            }

            Optional<Instance> instance = Optional.empty();
            Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(effectData.getOwner().getEntity().getLocation());
            Optional<Area> temp;

            if(area.get().isInstanced(effectData.getOwner())){
                instance = area.get().getInstance(effectData.getOwner());
            }

            for (Entity entity: entities) {
                if(!(entity instanceof Player)){
                    continue;
                }

                UserPlayer player = Avatar.INSTANCE.getUserManager().findUserPlayer(entity).get();
                if(instance.isPresent()){
                    temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(player.getPlayer().getLocation());
                    if(temp.isPresent()){
                        if(temp.get().isInstanced(player)){
                            if(temp.get().getInstance(player).get() != instance.get()) {
                                continue;
                            }
                        } else continue;
                    }
                }

                display(effectData, player);
            }
        }
    }

    private static void display(EffectData effectData, UserPlayer userPlayer){
        double factor = userPlayer.getParticleModifier().factor;
        userPlayer.getPlayer().spawnParticle(effectData.getParticle(), effectData.getDisplayAt(), (int)(factor * effectData.getAmount()),
            effectData.getxOffset(), effectData.getyOffset(), effectData.getzOffset(), effectData.getVelocity());
    }

    /**
     * An option every player can set to increase or reduce the particle amounts they see.
     */
    public enum ParticleModifier{
        LOW(0.25),
        MEDIUM(0.5),
        NORMAL(1.0),
        HIGH(1.25),
        EXTREME(1.5);

        private double factor;

        ParticleModifier(double factor){this.factor = factor;}

        public double getFactor() {
            return factor;
        }
    }

}
