package pcd.ass01.v1;

import java.util.Random;

public class Sketch01 {
	
	public static void main(String[] argv) {

		/* 
		 * Different board configs to try:
		 * - minimal: 2 small balls
		 * - large: 400 small balls
		 * - massive: 4500 small balls 
		 */
		
		var boardConf = new MinimalBoardConf();
		// var boardConf = new LargeBoardConf();
		// var boardConf = new MassiveBoardConf();
		
		Board board = new Board();
		board.init(boardConf);
		
		ViewModel viewModel = new ViewModel();
		View view = new View(viewModel, 1200, 800);
						
		viewModel.update(board, 0);			
		view.render();
		waitAbit();
		InputThread inputThread = new InputThread(view, board);
		BotThread botThread = new BotThread(board);
		PhysicsThread physicsThread = new PhysicsThread(board);
		RenderThread renderThread = new RenderThread(view, viewModel, board);

		inputThread.start();
		botThread.start();
		physicsThread.start();
		renderThread.start();

		System.out.println("Multithread executed");

	}
	
	private static void waitAbit() {
		try {
			Thread.sleep(2000);
		} catch (Exception ex) {}
	}
	
}
