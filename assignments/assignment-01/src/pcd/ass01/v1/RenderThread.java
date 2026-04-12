package pcd.ass01.v1;

public class RenderThread extends Thread {
    private final View view;

    public RenderThread(View view) {
        this.view = view;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while (true) {
            view.render();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
