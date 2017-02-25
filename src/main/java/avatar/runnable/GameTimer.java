package avatar.runnable;

import avatar.Avatar;

/**
 * This is the main game runnable
 */
public class GameTimer extends CoreTimer {

    public GameTimer(long interval) {
        super(interval);

        start();
    }

    @Override
    protected void runTask() {
        //Update necessary player information
        Avatar.INSTANCE.getUserManager().tick();

        //Update abilities per area
        Avatar.INSTANCE.getAreaManager().tick();
    }
}
