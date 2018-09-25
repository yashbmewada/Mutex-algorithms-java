package Bakery;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock{

	//Assigning IDs as ThreadLocal. This will help in automatically setting ID, and storing them as a thread calls.

    final private ThreadLocal<Integer> local_thread_id = new ThreadLocal<Integer>(){
        final private AtomicInteger threadId = new AtomicInteger(0);

        public  Integer initialValue(){ return threadId.getAndIncrement();}
    };


	AtomicBoolean[] flag;
	AtomicInteger[] label;
	volatile int numThreads;
	
	public Bakery(int numThreads) {
		this.numThreads = numThreads;
		flag = new AtomicBoolean[numThreads];
		label = new AtomicInteger[numThreads];
		for(int i=0;i<numThreads; i++){
			flag[i] = new AtomicBoolean(false);
			label[i] = new AtomicInteger(0);
		}
		
	}
	
	
	@Override
	public void lock() {
		int i = local_thread_id.get();
		flag[i].set(true);
		label[i].set(getMaxLabelId() + 1);
		while(checkFlagsAndLabel(i)) {
			//wait;
		}
		
		
		
	}

	private boolean checkFlagsAndLabel(int i) {
		// TODO Auto-generated method stub
		for(int k=0;k<numThreads;k++) { 
			if(k != i) {
				int[] currentPair = {label[k].intValue(),k};
				int[] threadPair = {label[i].intValue(),i};
				if(flag[k].get() && (currentPair[0]<threadPair[0] && currentPair[1]<threadPair[1])){
					return true;
				}
			}
		}
		return false;
	}


	private int getMaxLabelId() {
		// TODO Auto-generated method stub
		AtomicInteger max = label[0];
		for(AtomicInteger num:label) {
			int valueToSet = Math.max(max.intValue(), num.intValue());
			max.set(valueToSet);
		}
		return max.intValue();
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
		int id = local_thread_id.get();
		flag[id].set(false);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
