package pcd.ass01.seq;

import java.util.List;

public interface BoardConf {

	Boundary getBoardBoundary();
	
	Ball getPlayerBall();

	Ball getBotBall();

	List<Hole> getHoles();
	
	List<Ball> getSmallBalls();
}
