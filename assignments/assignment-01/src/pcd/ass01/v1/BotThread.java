package pcd.ass01.v1;

import java.util.Random;

public class BotThread extends Thread {
    private final Board board;

    public BotThread(Board board) {
        this.board = board;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        long lastKickTime = System.currentTimeMillis();
        Random rand = new Random();

        while (true) {
            if (board.getGameState() != GameState.PLAYING) {
                break;
            }

            var botBall = board.getBotBall();
            long currentTime = System.currentTimeMillis();

            if (botBall != null && botBall.getVel().abs() < 0.05 && (currentTime - lastKickTime > 2000)) {
                var angle = rand.nextDouble() * Math.PI * 2;
                var v = new V2d(Math.cos(angle), Math.sin(angle)).mul(1.5);
                // use the monitor, avoiding problems
                board.kickBotBall(v);
                lastKickTime = currentTime;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}