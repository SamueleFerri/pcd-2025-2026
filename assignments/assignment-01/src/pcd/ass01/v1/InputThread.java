package pcd.ass01.v1;

public class InputThread extends Thread {
    private final View view;
    private final Board board;

    public InputThread(View view, Board board) {
        this.view = view;
        this.board = board;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while (true) {
            Cmd cmd = view.getCmdQueue().poll();
            while (cmd != null) {
                cmd.execute(board);
                cmd = view.getCmdQueue().poll();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}