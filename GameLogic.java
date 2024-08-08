public class GameLogic implements Runnable {
    private Game game;

    public GameLogic(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (game.isRunning()) {
            game.update();
            try {
                Thread.sleep(16); // Ungefähr 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}