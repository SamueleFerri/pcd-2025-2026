package pcd.sketch01;

import java.util.List;

public interface BoardConf {

	Boundary getBoardBoundary();
	
	Ball getPlayerBall();

	Ball getBotBall();

	List<Hole> getHoles();
	
	List<Ball> getSmallBalls();
}
