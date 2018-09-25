package Tournament;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament{

	final private ThreadLocal<Integer> local_thread_id = new ThreadLocal<Integer>(){
        final private AtomicInteger threadId = new AtomicInteger(0);
        protected Integer initialValue(){
            return threadId.getAndIncrement();
        }
    };

	private int MAX_DEPTH;
	private PetersonLock[][] lockLevel;   

    //Constructor
	public Tournament(int threads){

		this.MAX_DEPTH =  (int) Math.ceil(((Math.log(threads))/Math.log(2))) ;
		//=Tree of Peterson Locks
		this.lockLevel = new PetersonLock[(this.MAX_DEPTH) + 1][threads + 1];
		for(int i = 0; i <= this.MAX_DEPTH; i++){
			for (int j = 0; j <= threads; j++){
				lockLevel[i][j] = new PetersonLock();
			}
		}
		
	}

	public void lock(){
		//Get ID and starting point
		int i = local_thread_id.get();
		int start = this.MAX_DEPTH - 1;
		int me;

		//go through the levels
		while (start>=0){
			me = i;
			i = (int) Math.floor(i/2);
			this.lockLevel[start][i].lock(me);
			start--;
		}
	}


	public void unlock(){
		int i = local_thread_id.get();
		ArrayList<Integer> list = new ArrayList(this.MAX_DEPTH + 1);
		int start = 0;
		list.add(i);
		//to find out what to unlock
		for(int j=0 ; j < this.MAX_DEPTH ; j++){
			i = (int) Math.floor(i/2);
			list.add(i);
		}
		for(int j=(list.size() - 1); j >= 1; j--){
			this.lockLevel[start][list.get(j)].unlock(list.get(j-1));
			start++;
		}
			
	}
}