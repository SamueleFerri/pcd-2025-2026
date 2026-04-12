package pcd.ass01.v1;

public class StrikeCmd implements Cmd {
    private final V2d velocity;

    public StrikeCmd(V2d velocity) {
        this.velocity = velocity;
    }

    @Override
    public void execute(Board board) {
        Ball playerBall = board.getPlayerBall();

        if (playerBall != null) {
            //checking if the ball is not moving
            if (playerBall.getVel().abs() < 0.001) {
                playerBall.kick(velocity);
            }
        }
    }
}
