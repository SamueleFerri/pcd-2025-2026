package pcd.lab05.monitors.ex_barrier;

public class BarrierImpl1 implements Barrier {

    private final int nParticipants;
    private int nArrived;

    public BarrierImpl1(int nParticipants) {
        this.nParticipants = nParticipants;
        nArrived = 0;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        nArrived++;
        if (nArrived < nParticipants) {
            while (nArrived < nParticipants) {
                wait();
            }
        } else {
            notifyAll();
        }
    }
}
