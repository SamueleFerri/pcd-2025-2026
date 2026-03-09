package pcd.lab04.ex01_synchwithsem;

import java.util.concurrent.Semaphore;

public class Ponger extends ActiveComponent {

	private Semaphore pongDoneEvent;
	private Semaphore pingDoneEvent;
	
	public Ponger(Semaphore lock1, Semaphore lock2) {
		this.pongDoneEvent = lock1;
		this.pingDoneEvent = lock2;
	}	
	
	public void run() {
		while (true) {
			try{
				pingDoneEvent.acquire();
				println("pong");
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
				pongDoneEvent.release();
			}
		}
	}
}