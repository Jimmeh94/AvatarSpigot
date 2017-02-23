package avatar.game.user;


import avatar.Avatar;
import avatar.game.ability.UserAbilityManager;
import avatar.game.area.Area;
import avatar.game.user.combatlog.EntityCombatLogger;
import avatar.game.user.stats.IStatsPreset;
import avatar.game.user.stats.Stats;
import avatar.game.user.stats.presets.DefaultBenderPreset;
import avatar.util.misc.EntityHiding;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Base class for any entity that will need stats or ability
 */
public class User implements Hideable{

    /**
     * Hideable is for entities that are specific to instances and such
     */

    private UUID user;
    private Stats stats;
    private Area presentArea;
    private EntityCombatLogger combatLogger;
    private UserAbilityManager userAbilityManager;
    /**
     * For instanced entities, but have no need for it now
     */
    private List<Player> viewers;

    public User(UUID user){
        this(user, new DefaultBenderPreset());
    }

    public User(UUID user, IStatsPreset preset){
        this.user = user;
        stats = new Stats(preset, this);
        combatLogger = new EntityCombatLogger(this);

        Avatar.INSTANCE.getUserManager().add(this);

        userAbilityManager = new UserAbilityManager(this);
    }

    public boolean canBeAttacked(){return combatLogger.canReceiveDamage();}

    /**
     * Cleans up all loose ends this might have, if the user was just directly removed
     */
    public void cleanUp(){
        leaveArea();
        Avatar.INSTANCE.getUserManager().remove(this);
    }

    public boolean isPlayer(){return this instanceof UserPlayer;}

    /**
     * Use this to have this User enter an area rather than Area#enterArea(User user)
     * @param area
     */
    public void enterArea(Area area, boolean updateScoreboard){
        if(area == presentArea)
            return;

        if(presentArea != null){
            leaveArea();
        }

        presentArea = area;
        presentArea.entering(this);
    }

    /**
     * Use this to have this User leave the current area, rather than Area#leaveArea()
     */
    private void leaveArea() {
        if(presentArea == null)
            return;

        presentArea.leaving(this);
        presentArea = null;
    }

    //--- Getters ---
    public Stats getStats() {
        return stats;
    }

    public Area getPresentArea(){return presentArea;}

    public UUID getUUID() {
        return user;
    }

    public EntityCombatLogger getCombatLogger() {
        return combatLogger;
    }

    public Entity getEntity(){
        return Bukkit.getEntity(user);
    }

    public UserAbilityManager getUserAbilityManager() {
        return userAbilityManager;
    }

    public void tick() {

    }

    @Override
    public void addViewer(Player player) {
        if(!viewers.contains(player)){
            viewers.add(player);
            EntityHiding.showEntity(player, getEntity());
        }
    }

    @Override
    public void removeViewer(Player player) {
        if(viewers.contains(player)){
            viewers.remove(player);
            EntityHiding.hideEntity(player, getEntity());
        }
    }

    @Override
    public boolean canSee(Player player) {
        if(viewers.isEmpty()){
            return true;
        }
        return viewers.contains(player);
    }
}
