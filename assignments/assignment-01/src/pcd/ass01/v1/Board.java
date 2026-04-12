package pcd.ass01.v1;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Ball> balls;
    private Ball playerBall;
    private Boundary bounds;
    private List<Hole> holes;
    private Ball botBall;
    private int playerScore;
    private int botScore;
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
    
    public synchronized void updateState(long dt) {

        if (state != GameState.PLAYING) {
            return;
        }

    	playerBall.updateState(dt, this);

        if (botBall != null) {
            botBall.updateState(dt, this);
        }
    	
    	for (var b: balls) {
    		b.updateState(dt, this);
    	}

        if (botBall != null) {
            for (var b: balls) {
                double dist = getDist(botBall.getPos(), b.getPos());
                if (dist <= botBall.getRadius() + b.getRadius()) {
                    b.setLastHitter(2);
                }
                Ball.resolveCollision(botBall, b);
            }
            if (playerBall != null) {
                Ball.resolveCollision(playerBall, botBall);
            }
        }
    	
    	for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }

    	for (var b: balls) {
            if (playerBall != null) {
                double dist = getDist(playerBall.getPos(), b.getPos());
                if (dist <= playerBall.getRadius() + b.getRadius()) {
                    b.setLastHitter(1);
                }
                Ball.resolveCollision(playerBall, b);
            }
    	}

        List<Ball> ballsToRemove = new ArrayList<>();
        for (var b: balls) {
            for (var h: holes) {
                double dx = b.getPos().x() - h.pos().x();
                double dy = b.getPos().y() - h.pos().y();
                double dist = Math.hypot(dx, dy);

                if (dist < h.radius()) {
                    ballsToRemove.add(b);
                    if (b.getLastHitter() == 2) {
                        botScore++;
                    } else {
                        playerScore++;
                    }
                    break;
                }
            }
        }

        balls.removeAll(ballsToRemove);

        if (playerBall != null) {
            for (var h : holes) {
                double dist = getDist(playerBall.getPos(), h.pos());
                if (dist < h.radius()) {
                    state = GameState.BOT_WON;
                    break;
                }
            }
        }

        if (botBall != null) {
            for (var h : holes) {
                double dist = getDist(botBall.getPos(), h.pos());
                if (dist < h.radius()) {
                    state = GameState.PLAYER_WON;
                    break;
                }
            }
        }

        if (balls.isEmpty()) {
            if (playerScore > botScore) {
                state = GameState.PLAYER_WON;
            } else if (botScore > playerScore) {
                state = GameState.BOT_WON;
            } else {
                state = GameState.TIE;
            }
        }
    }

    private double getDist(P2d p1, P2d p2) {
        return Math.hypot(p1.x() - p2.x(), p1.y() - p2.y());
    }

    public synchronized List<Ball> getBalls(){
        //return a copy so the render has no problem if the engine modifies it
        return new ArrayList<>(balls);
    }

    public synchronized Ball getPlayerBall() {
    	return playerBall;
    }

    public synchronized Boundary getBounds(){
        return bounds;
    }

    public synchronized List<Hole> getHoles() {
        //good practice, no need
        return new ArrayList<>(holes);
    }

    public synchronized void kickBotBall(V2d velocity) {
        if (botBall != null) {
            botBall.kick(velocity);
        }
    }

    public synchronized int getPlayerScore() { return playerScore; }

    public synchronized int getBotScore() { return botScore; }

    public synchronized Ball getBotBall() {
        return botBall;
    }

    public synchronized GameState getGameState() { return state; }
}
