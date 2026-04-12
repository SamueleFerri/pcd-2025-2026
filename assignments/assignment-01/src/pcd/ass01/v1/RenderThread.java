package pcd.ass01.v1;

public class RenderThread extends Thread {
    private final View view;
    private final ViewModel viewModel;
    private final Board board;

    public RenderThread(View view, ViewModel viewModel, Board board) {
        this.view = view;
        this.viewModel = viewModel;
        this.board = board;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        int nFrames = 0;

        while (true) {
            nFrames++;
            int framePerSec = 0;
            long dt = (System.currentTimeMillis() - t0);

            if (dt > 0) {
                framePerSec = (int) (nFrames * 1000 / dt);
            }

            viewModel.update(board, framePerSec);
            view.render();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}