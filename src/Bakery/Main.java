package Bakery;

public class Main {

	private static int count = 0;
	public static final int NUM_THREADS = 4;
	public static void main(String[] args) {
		int NUM_THREADS = 0;
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println(cores);
		for(int j=1; j<=cores; j++) {
			count = 0;
			NUM_THREADS = j;
			System.out.println("NUMBER OF THREADS   = " + NUM_THREADS);
			final Bakery bakery = new Bakery(NUM_THREADS);
			
			Thread[] threads = new Thread[NUM_THREADS];
			
			
			for(int i=0;i<NUM_THREADS;i++) {
				threads[i] = createNewThread(bakery);
				threads[i].start();
			}
			
			for(int i=0; i<NUM_THREADS; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Failed" );
				}
			}
			
			System.out.println("COUNT FOR " + NUM_THREADS + "  = " + count);
			
			
		
		}
			
		
		
		
		
		
	}
	private static Thread createNewThread(final Bakery bakery) {
		
		
		
		Thread threadToSpawn = new Thread(new Runnable(){ public void run() {
					try {
					bakery.lock();
					//critical section
					countNum();
					}finally{bakery.unlock();}
				}}

); // name of the thread;
		
		return threadToSpawn;
	}
	
	private static void countNum() {
		for(int i=0; i<1000000; i++) {
			count++;
		}
		
	}

}
