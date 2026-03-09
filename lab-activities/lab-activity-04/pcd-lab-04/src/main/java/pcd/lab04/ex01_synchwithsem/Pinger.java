package pcd.lab04.ex01_synchwithsem;

import java.util.concurrent.Semaphore;

public class Pinger extends ActiveComponent {

	private Semaphore pongDoneEvent;
	private Semaphore pingDoneEvent;

	public Pinger(Semaphore lock1, Semaphore lock2) {
		this.pongDoneEvent = lock1;
		this.pingDoneEvent = lock2;
	}	
	
	public void run() {
		while (true) {
			try{
				pongDoneEvent.acquire();
				println("ping");
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
				pingDoneEvent.release();
			}
		}
	}
}