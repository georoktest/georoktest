import javax.swing.*;
import java.awt.*;

public class Pseudo3DExample extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Positionen der Objekte (x, y, z)
    private double[][] objects = {
        {100, 100, 100},
        {300, 100, 200},
        {500, 100, 300},
    };

    // Kameraeinstellungen
    private double cameraZ = -500;

    public static void main(String[] args) {
        JFrame frame = new JFrame("2.5D Example");
        Pseudo3DExample panel = new Pseudo3DExample();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (double[] obj : objects) {
            // 3D zu 2D Projektion
            double scale = cameraZ / (cameraZ + obj[2]);
            int x = (int) (WIDTH / 2 + scale * (obj[0] - WIDTH / 2));
            int y = (int) (HEIGHT / 2 + scale * (obj[1] - HEIGHT / 2));
            int size = (int) (scale * 100); // Skalierung für Größenänderung

            g2d.fillRect(x - size / 2, y - size / 2, size, size);
        }
    }
}