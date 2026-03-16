package pcd.lab05.monitors.ex_latch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LatchImpl2 implements Latch {

    private final int nCountDowns;
    private int nCounts;
    private final Lock lock;
    private final Condition allCountsDone;

    public LatchImpl2(int nCountDowns) {
        this.nCountDowns = nCountDowns;
        nCounts = 0;
        lock = new ReentrantLock();
        allCountsDone = lock.newCondition();
    }

    @Override
    public void countDown() {
        try {
            lock.lock();
            nCounts++;
            if (nCounts == nCountDowns) {
                allCountsDone.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void await() throws InterruptedException {
        try {
            lock.lock();
            while (nCounts < nCountDowns) {
                allCountsDone.await();
            }
        } finally {
            lock.unlock();
        }
    }
}
