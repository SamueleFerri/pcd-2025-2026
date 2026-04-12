package pcd.ass01.v1;

public class PhysicsThread extends Thread {
    private final Board board;

    public PhysicsThread(Board board) {
        this.board = board;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        // the start fo the thread
        long lastUpdateTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();
            long dt = currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            board.updateState(dt);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
