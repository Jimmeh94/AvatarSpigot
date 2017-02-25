package avatar.game.ability.type;

import avatar.game.ability.AbilityStage;
import avatar.game.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class AbilityTargetingEntity extends AbilityTargeting {

    private Entity targetEntity;

    public AbilityTargetingEntity(User owner, double speed, Entity targetEntity) {
        this(owner, speed, 3, targetEntity);
    }

    public AbilityTargetingEntity(User owner, double speed, int history, Entity targetEntity) {
        super(owner, speed, history);

        this.targetEntity = targetEntity;
        this.setTarget(targetEntity.getLocation().add(0, 1, 0));
    }

    private boolean entityValid(){
        return !targetEntity.isDead() && targetEntity.isValid();
    }

    @Override
    public void run(){
        if(this.stage != AbilityStage.FINISH){
            if(entityValid()){
                setTarget(targetEntity.getLocation());
            } else {
                this.cancel(null);
                return;
            }
        }
        super.run();
    }

    @Override
    protected Location setInitialTarget() {
        return null;
    }


}
