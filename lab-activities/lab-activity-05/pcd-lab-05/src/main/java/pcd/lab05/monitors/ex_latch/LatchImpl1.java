package pcd.lab05.monitors.ex_latch;

public class LatchImpl1 implements Latch {

    private final int nCountDowns;
    private int nCounts;

    public LatchImpl1(int nCountDowns) {
        this.nCountDowns = nCountDowns;
        nCounts = 0;
    }

    @Override
    public synchronized void countDown() {
        nCounts++;
        if (nCounts == nCountDowns) {
            notifyAll();
        }
    }

    @Override
    public synchronized void await() throws InterruptedException {
        while (nCounts < nCountDowns) {
            wait();
        }
    }
}
