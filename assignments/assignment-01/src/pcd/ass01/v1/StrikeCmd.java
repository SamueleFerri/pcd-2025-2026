package pcd.ass01.v1;

public class StrikeCmd implements Cmd {
    private final V2d velocity;

    public StrikeCmd(V2d velocity) {
        this.velocity = velocity;
    }

    @Override
    public void execute(Board board) {
        board.kickPlayerBall(velocity);
    }
}
