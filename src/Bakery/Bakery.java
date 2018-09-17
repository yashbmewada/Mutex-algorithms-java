package Bakery;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock{

	boolean[] flag;
	int[] label;
	int numThreads;
	
	public Bakery(int numThreads) {
		this.numThreads = numThreads;
		flag = new boolean[numThreads];
		label = new int[numThreads];
		System.out.println(Arrays.toString(flag));
		System.out.println(Arrays.toString(label));
		
	}
	
	
	@Override
	public void lock() {
		int i = Integer.parseInt(Thread.currentThread().getName());
		flag[i] = true;
		label[i] = getMaxLabelId() + 1;
		while(checkFlagsAndLabel(i)) {
			//wait;
		}
		
		
		
	}

	private boolean checkFlagsAndLabel(int i) {
		// TODO Auto-generated method stub
		for(int k=0;k<numThreads;k++) { 
			if(k != i) {
				int[] currentPair = {label[k],k};
				int[] threadPair = {label[i],i};
				if(flag[k] && (currentPair[0]<threadPair[0] && currentPair[1]<threadPair[1])){
					return true;
				}
			}
		}
		return false;
	}


	private int getMaxLabelId() {
		// TODO Auto-generated method stub
		int max = label[0];
		for(int num:label) {
			max = Math.max(max, num);
		}
		return max;
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
		flag[Integer.parseInt(Thread.currentThread().getName())] = false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
