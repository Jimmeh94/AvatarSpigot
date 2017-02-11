package avatar.runnable;

import avatar.Avatar;

public class SlowTimer extends CoreTimer {

    public SlowTimer(long interval) {
        super(interval);

        start();
    }

    @Override
    protected void runTask() {
        //Checks the block replacements
        Avatar.INSTANCE.getBlockManager().tick();

        //Remove unneeded NPC's
        Avatar.INSTANCE.getEntityManager().tick();

        Avatar.INSTANCE.getUserManager().tickHologramMenus();
    }
}
