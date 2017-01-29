package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

public class AbilityPropertyCharge extends AbilityProperty implements Runnable{

    private int duration, countdown;
    private Task task;
    private boolean charged = false;

    public AbilityPropertyCharge(String displayName, Ability ability, int duration) {
        super(displayName, ability, AbilityStage.REQUIREMENT_CHECK);

        this.duration = duration;
        this.countdown = duration;
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
    public Text getFailMessage() {
        return null;
    }
}
