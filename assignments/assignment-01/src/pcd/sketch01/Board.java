package pcd.sketch01;

import java.util.*;

public class Board {

    private List<Ball> balls;    
    private Ball playerBall;
    private Boundary bounds;
    private List<Hole> holes;
    private Ball botBall;
    
    public Board(){} 
    
    public void init(BoardConf conf) {
    	balls = conf.getSmallBalls();    	
    	playerBall = conf.getPlayerBall(); 
    	bounds = conf.getBoardBoundary();
        holes = conf.getHoles();
        botBall = conf.getBotBall();
    }
    
    public void updateState(long dt) {

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

    public Ball getBotBall() {
        return botBall;
    }
}
