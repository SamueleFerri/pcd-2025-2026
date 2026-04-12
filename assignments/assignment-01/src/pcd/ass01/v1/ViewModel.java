package pcd.ass01.v1;

import java.util.ArrayList;
import java.util.List;

record BallViewInfo(P2d pos, double radius) {}

public class ViewModel {

	private final ArrayList<BallViewInfo> balls;
	private BallViewInfo player;
	private int framePerSec;
	private List<Hole> holes;
	private BallViewInfo botBall;
	private int playerScore;
	private int botScore;
	private GameState state;
	
	public ViewModel() {
		balls = new ArrayList<>();
		framePerSec = 0;
		playerScore = 0;
		botScore = 0;
		state = GameState.PLAYING;
	}
	
	public synchronized void update(Board board, int framePerSec) {
		balls.clear();
		this.playerScore = board.getPlayerScore();
		this.botScore = board.getBotScore();
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
        return new ArrayList<>(balls);
		
	}

	public synchronized int getFramePerSec() {
		return framePerSec;
	}

	public synchronized BallViewInfo getPlayerBall() {
		return player;
	}

	public synchronized List<Hole> getHoles() { return holes; }

	public synchronized BallViewInfo getBotBall() { return botBall; }

	public synchronized int getPlayerScore() { return playerScore; }

	public synchronized int getBotScore() { return botScore; }

	public synchronized GameState getState() { return state; }
}
