package avatar.game.entity.npc.nms;


import net.minecraft.server.v1_11_R1.PathfinderGoal;

/**
 * Created by Emiel on 6-5-2015.
 */
public abstract class PathfinderGoalWrapper
{
	private PathfinderGoal goal = new PathfinderGoal()
	{
		@Override
		public boolean a()
		{
			return shouldExecute();
		}

		@Override
		public boolean b()
		{
			return shouldContinue();
		}

		@Override
		public boolean g()
		{
			return isInterruptable();
		}

		@Override
		public void c()
		{
			executeTask();
		}

		@Override
		public void d()
		{
			resetTask();
		}

		@Override
		public void e()
		{
			updateTask();
		}
	};

	public abstract boolean shouldExecute(); //a()

	public boolean shouldContinue() //b()
	{
		return false;
	}

	public boolean isInterruptable() //i()
	{
		return true;
	}

	public void executeTask(){} //c()

	public void resetTask(){} //d()

	public void updateTask(){} //e()

	public void setMutexBits(int i)
	{
		goal.a(i);
	}

	public PathfinderGoal getHandle()
	{
		return goal;
	}
}
