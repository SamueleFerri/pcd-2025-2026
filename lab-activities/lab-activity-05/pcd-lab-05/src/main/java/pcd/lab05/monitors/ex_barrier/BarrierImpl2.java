package pcd.lab05.monitors.ex_barrier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierImpl2 implements Barrier {

    private final int nParticipants;
    private int nArrived;
    private final Lock lock;
    private final Condition allArrived;

    public BarrierImpl2(int nParticipants) {
        this.nParticipants = nParticipants;
        nArrived = 0;
        lock = new ReentrantLock();
        allArrived = lock.newCondition();
    }

    @Override
    public void hitAndWaitAll() throws InterruptedException {
        try {
            lock.lock();
            nArrived++;
            if (nArrived < nParticipants) {
                while (nArrived < nParticipants) {
                    allArrived.await();
                }
            } else {
                allArrived.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
