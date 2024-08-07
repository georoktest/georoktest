import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pseudo3DWithLighting extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Positionen der Objekte (x, y, z)
    private double[][] objects = {
        {100, 100, 100},
        {300, 100, 200},
        {500, 100, 300},
    };

    // Texturen für die Objekte
    private BufferedImage[] textures;

    // Lichtquelle (Position in 3D)
    private double[] lightSource = {400, 300, -300};

    // Kameraeinstellungen
    private double cameraZ = -500;

    public static void main(String[] args) {
        JFrame frame = new JFrame("2.5D with Lighting and Textures");
        Pseudo3DWithLighting panel = new Pseudo3DWithLighting();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Pseudo3DWithLighting() {
        try {
            textures = new BufferedImage[]{
                ImageIO.read(new File("texture1.png")),
                ImageIO.read(new File("texture2.png")),
                ImageIO.read(new File("texture3.png"))
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < objects.length; i++) {
            double[] obj = objects[i];

            // 3D zu 2D Projektion
            double scale = cameraZ / (cameraZ + obj[2]);
            int x = (int) (WIDTH / 2 + scale * (obj[0] - WIDTH / 2));
            int y = (int) (HEIGHT / 2 + scale * (obj[1] - HEIGHT / 2));
            int size = (int) (scale * 100);

            // Beleuchtung berechnen (Lambert'sches Modell)
            double[] normal = {0, 0, 1}; // Normalvektor des Quadrats
            double[] lightVector = {lightSource[0] - obj[0], lightSource[1] - obj[1], lightSource[2] - obj[2]};
            double lightDist = Math.sqrt(lightVector[0] * lightVector[0] + lightVector[1] * lightVector[1] + lightVector[2] * lightVector[2]);
            lightVector[0] /= lightDist;
            lightVector[1] /= lightDist;
            lightVector[2] /= lightDist;
            double dotProduct = normal[0] * lightVector[0] + normal[1] * lightVector[1] + normal[2] * lightVector[2];
            double brightness = Math.max(0, dotProduct);

            // Textur und Beleuchtung anwenden
            g2d.drawImage(applyLighting(textures[i], brightness), x - size / 2, y - size / 2, size, size, null);
        }
    }

    private BufferedImage applyLighting(BufferedImage img, double brightness) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgba = img.getRGB(x, y);
                Color col = new Color(rgba, true);
                int r = (int) (col.getRed() * brightness);
                int g = (int) (col.getGreen() * brightness);
                int b = (int) (col.getBlue() * brightness);
                int a = col.getAlpha();
                newImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
        return newImage;
    }
}