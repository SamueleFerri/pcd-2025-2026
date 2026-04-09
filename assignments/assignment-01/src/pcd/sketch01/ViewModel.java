package pcd.sketch01;

import java.util.ArrayList;
import java.util.List;

record BallViewInfo(P2d pos, double radius) {}

public class ViewModel {

	private final ArrayList<BallViewInfo> balls;
	private BallViewInfo player;
	private int framePerSec;
	private List<Hole> holes;
	private BallViewInfo botBall;
	private int score;
	private GameState state;
	
	public ViewModel() {
		balls = new ArrayList<BallViewInfo>();
		framePerSec = 0;
		score = 0;
		state = GameState.PLAYING;
	}
	
	public synchronized void update(Board board, int framePerSec) {
		balls.clear();
		this.score = board.getScore();
		this.state = board.getGameState();
		for (var b: board.getBalls()) {
			balls.add(new BallViewInfo(b.getPos(), b.getRadius()));
		}
		this.framePerSec = framePerSec;
		var p = board.getPlayerBall();
		if (p != null) {
			player = new BallViewInfo(p.getPos(), p.getRadius());
		} else {
			player = null;
		}
		var b = board.getBotBall();
		if (b != null) {
			botBall = new BallViewInfo(b.getPos(), b.getRadius());
		} else {
			botBall = null;
		}
		holes = board.getHoles();
	}
	
	public synchronized ArrayList<BallViewInfo> getBalls(){
		var copy = new ArrayList<BallViewInfo>();
		copy.addAll(balls);
		return copy;
		
	}

	public synchronized int getFramePerSec() {
		return framePerSec;
	}

	public synchronized BallViewInfo getPlayerBall() {
		return player;
	}

	public synchronized List<Hole> getHoles() { return holes; }

	public synchronized BallViewInfo getBotBall() { return botBall; }

	public synchronized int getScore() { return score; }

	public synchronized GameState getState() { return state; }
}
