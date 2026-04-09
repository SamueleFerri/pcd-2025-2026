package pcd.sketch01;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class View {

	private final ViewFrame frame;
	private final ViewModel viewModel;
	private final BlockingQueue<Cmd> cmdQueue;
	
	public View(ViewModel model, int w, int h) {
		frame = new ViewFrame(model, w, h);	
		frame.setVisible(true);
		this.viewModel = model;
		this.cmdQueue = new ArrayBlockingQueue<>(100);
	}
		
	public void render() {
		frame.render();
	}
	
	public ViewModel getViewModel() {
		return viewModel;
	}

	public BlockingQueue<Cmd> getCmdQueue() { return cmdQueue; }
}
