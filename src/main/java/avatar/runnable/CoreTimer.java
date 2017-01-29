package avatar.runnable;

import avatar.Avatar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class CoreTimer implements Runnable {

    protected BukkitTask task;
    private int intervalsPassed = 0, cancelAt = -1;
    private long interval, delay;

    protected abstract void runTask();

    public CoreTimer(long interval){
        this(interval, 0L);
    }

    public CoreTimer(long interval, long delay){
        this(interval, delay, -1);
    }

    public CoreTimer(long interval, long delay, int cancelAt){
        this.interval = interval;
        this.delay = delay;
        this.cancelAt = cancelAt;
    }

    public void start(){
        task = Bukkit.getScheduler().runTaskTimer(Avatar.INSTANCE, this, interval, delay);
    }

    public void stop(){
        if (task == null)
            return;
        try {
            task.cancel();
            task = null;
        } catch (IllegalStateException exc) {
        }
    }

    @Override
    public void run() {
        intervalsPassed++;
        if(cancelAt != -1 && intervalsPassed >= cancelAt / interval){
            stop();
        }
        runTask();
    }
}
