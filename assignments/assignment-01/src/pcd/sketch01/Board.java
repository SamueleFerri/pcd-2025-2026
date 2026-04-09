package pcd.sketch01;

import java.util.*;

public class Board {

    private List<Ball> balls;    
    private Ball playerBall;
    private Boundary bounds;
    private List<Hole> holes;
    private Ball botBall;
    private int score;
    private GameState state;
    
    public Board(){} 
    
    public void init(BoardConf conf) {
    	balls = conf.getSmallBalls();    	
    	playerBall = conf.getPlayerBall(); 
    	bounds = conf.getBoardBoundary();
        holes = conf.getHoles();
        botBall = conf.getBotBall();
        state = GameState.PLAYING;
    }
    
    public void updateState(long dt) {

        if (state != GameState.PLAYING) {
            return;
        }

    	playerBall.updateState(dt, this);
    	
    	for (var b: balls) {
    		b.updateState(dt, this);
    	}       	
    	
    	for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls) {
    		Ball.resolveCollision(playerBall, b);
    	}

        List<Ball> ballsToRemove = new ArrayList<>();
        for (var b: balls) {
            for (var h: holes) {
                double dx = b.getPos().x() - h.pos().x();
                double dy = b.getPos().y() - h.pos().y();
                double dist = Math.hypot(dx, dy);

                if (dist < h.radius()) {
                    ballsToRemove.add(b);
                    score++;
                    break;
                }
            }
        }

        balls.removeAll(ballsToRemove);

        if (playerBall != null) {
            for (var h : holes) {
                double dist = Math.hypot(playerBall.getPos().x() - h.pos().x(), playerBall.getPos().y() - h.pos().y());
                if (dist < h.radius()) {
                    state = GameState.BOT_WON;
                }
            }
        }

        if (botBall != null) {
            for (var h : holes) {
                double dist = Math.hypot(botBall.getPos().x() - h.pos().x(), botBall.getPos().y() - h.pos().y());
                if (dist < h.radius()) {
                    state = GameState.PLAYER_WON;
                }
            }
        }

        if (balls.isEmpty()) {
            // if(playerScore > botScore)...
            state = GameState.PLAYER_WON;
        }
    }

    public List<Ball> getBalls(){
    	return balls;
    }

    public Ball getPlayerBall() {
    	return playerBall;
    }

    public Boundary getBounds(){
        return bounds;
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public int getScore() { return score; }

    public Ball getBotBall() {
        return botBall;
    }

    public GameState getGameState() { return state; }
}
