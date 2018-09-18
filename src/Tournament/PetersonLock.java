package Tournament;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PetersonLock implements Lock{


	private boolean[] flag = new boolean[2];
	private int victim;

	
	
	
	@Override
	public void lock() {
		// TODO change me and other.
		int me = Integer.parseInt(Thread.currentThread().getName());
		int other = 1 - me;
		
		flag[me] = true;
		victim = me;
		
		while(flag[other] && victim == me)
		{
			//wait
		}
		
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		int me = Integer.parseInt(Thread.currentThread().getName());
		flag[me] = false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
