package Tournament;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PetersonLock implements Lock{

	//Assigning IDs as ThreadLocal. This will help in automatically setting ID, and storing them as a thread calls.

	final private ThreadLocal<Integer> local_thread_id = new ThreadLocal<Integer>(){
		final private AtomicInteger threadId = new AtomicInteger(0);

		public  Integer initialValue(){ return threadId.getAndIncrement() % 2;}
	};



	private AtomicBoolean[] flag = new AtomicBoolean[2];
	private volatile int victim;

	
	
	
	@Override
	public void lock() {
		// TODO change me and other.
		int me = local_thread_id.get();
		int other = 1 - me;
		
		flag[me].set(true);
		victim = me;
		
		while(flag[other].get() && victim == me)
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
		int me = local_thread_id.get();
		flag[me].set(false);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
