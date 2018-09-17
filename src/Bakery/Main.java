package Bakery;

public class Main {

	private static int count = 0;
	public static final int NUM_THREADS = 4;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println(cores);
		final Bakery bakery = new Bakery(NUM_THREADS);
		
		Thread[] threads = new Thread[NUM_THREADS];
		
		
		for(int i=0;i<NUM_THREADS;i++) {
			threads[i] = createNewThread(i, bakery);
			System.out.println("Thread Spawned number : " + threads[i].getName());
			threads[i].start();
			
		}
		
		
		
		
		
	}
	private static Thread createNewThread(int i, Bakery bakery) {
		
		
		
		Thread threadToSpawn = new Thread(
				() ->  {
					try {
					bakery.lock();
					//critical section
					System.out.println("Thread in lock : " + Thread.currentThread().getName());
					System.out.println("Thread updating count: " + Thread.currentThread().getName());
					System.out.println("count before update : " + count);
					countNum();
					System.out.println("count after update : " + count);
					}finally{bakery.unlock();}
					System.out.println("Thread outside lock : " +Thread.currentThread().getName());
				}, 
				String.valueOf(i)); // name of the thread;
		
		return threadToSpawn;
	}
	
	private static void countNum() {
		for(int i=0; i<1000000; i++) {
			count++;
		}
		
	}

}
