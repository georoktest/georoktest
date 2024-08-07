import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int FPS = 60;

    private Thread thread;
    private Thread logicThread;
    private Thread soundThread;
    private boolean running = false;

    private GameMap gameMap;
    private List<Siedler> settlers;

    public Game() {
        gameMap = new GameMap(20, 20);
        gameMap.placeBuilding(5, 5, new Building(Building.BuildingType.HOUSE));
        gameMap.placeBuilding(6, 6, new Building(Building.BuildingType.FARM));
        gameMap.placeBuilding(7, 7, new Building(Building.BuildingType.MINE));

        settlers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            settlers.add(new Siedler((int) (Math.random() * 20), (int) (Math.random() * 20)));
        }

        JFrame frame = new JFrame("Siedler 4 Clone");
        frame.add(this);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        logicThread = new Thread(new GameLogic(this));
        soundThread = new Thread(new SoundManager());
        thread.start();
        logicThread.start();
        soundThread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
            logicThread.join();
            soundThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / FPS;

        long lastTimer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void update() {
        for (Siedler settler : settlers) {
            settler.update();
        }
        // Hier die Spiel-Logik aktualisieren
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        // Hier das Spiel rendern
        renderMap(g);
        renderSettlers(g);

        g.dispose();
        bs.show();
    }

    private void renderMap(Graphics g) {
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Tile tile = gameMap.getTile(x, y);
                Color color;
                switch (tile.getTerrainType()) {
                    case GRASS:
                        color = Color.GREEN;
                        break;
                    case WATER:
                        color = Color.BLUE;
                        break;
                    case MOUNTAIN:
                        color = Color.GRAY;
                        break;
                    case FOREST:
                        color = new Color(34, 139, 34); // Forest green
                        break;
                    default:
                        color = Color.BLACK;
                }
                g.setColor(color);
                g.fillRect(x * 32, y * 32, 32, 32);

                // Gebäude zeichnen
                Building building = tile.getBuilding();
                if (building != null) {
                    switch (building.getType()) {
                        case HOUSE:
                            g.setColor(Color.RED);
                            break;
                        case FARM:
                            g.setColor(Color.YELLOW);
                            break;
                        case MINE:
                            g.setColor(Color.DARK_GRAY);
                            break;
                    }
                    g.fillRect(x * 32 + 8, y * 32 + 8, 16, 16); // Kleinere Quadrate für Gebäude
                }
            }
        }
    }

    private void renderSettlers(Graphics g) {
        for (Siedler settler : settlers) {
            g.setColor(Color.WHITE);
            g.fillRect(settler.getX() * 32 + 8, settler.getY() * 32 + 8, 16, 16);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}