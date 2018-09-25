
package Tournament;


public class TournamentTest {
	private static int count = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int NUM_THREADS = 0;
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println(cores);
		for(int j=1; j<=cores; j++) {
			count = 0;
			NUM_THREADS = j;
			System.out.println("NUMBER OF THREADS   = " + NUM_THREADS);
			final Tournament tournament = new Tournament(NUM_THREADS);
			
			Thread[] threads = new Thread[NUM_THREADS];
			
			
			for(int i=0;i<NUM_THREADS;i++) {
				threads[i] = createNewThread(tournament);
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
	private static Thread createNewThread( final Tournament tournament) {
		
		
		
		Thread threadToSpawn = new Thread(
				new Runnable(){
				public void run(){
					try {
					tournament.lock();
					//critical section
					countNum();
					}finally{tournament.unlock();}
					
				}}); // name of the thread;
		
		return threadToSpawn;
	}
	
	private static void countNum() {
		for(int i=0; i<1000000; i++) {
			count++;
		}
		
	}

}
