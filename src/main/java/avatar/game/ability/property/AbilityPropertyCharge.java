package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import org.bukkit.scheduler.BukkitTask;

public class AbilityPropertyCharge extends AbilityProperty implements Runnable{

    private int duration, countdown;
    private BukkitTask task;
    private boolean charged = false;

    public AbilityPropertyCharge(String displayName, Ability ability, int duration) {
        super(displayName, ability, AbilityStage.REQUIREMENT_CHECK);

        this.duration = duration;
        this.countdown = new Integer(duration);
    }

    public void stop(){
        if(this.task != null)
            task.cancel();
    }

    @Override
    public void run() {
        countdown--;
        if(countdown == 0){
            charged = true;
        }
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void reset() {
        charged = false;
        countdown = duration;
    }

    @Override
    public String getFailMessage() {
        return null;
    }
}
