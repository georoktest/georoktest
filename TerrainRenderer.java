import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TerrainRenderer extends JPanel {
    private BufferedImage terrainImage;
    private BufferedImage heightMapImage;
    private IsometricProjection isoProjection = new IsometricProjection();
    private int[][] heightMapData;
    private int tileWidth = 64;
    private int tileHeight = 32;

    public TerrainRenderer(String terrainPath, String heightMapPath) {
        try {
            terrainImage = ImageIO.read(new File(terrainPath));
            HeightMapLoader heightMapLoader = new HeightMapLoader();
            heightMapImage = heightMapLoader.loadHeightMap(heightMapPath);
            heightMapData = new int[heightMapImage.getWidth()][heightMapImage.getHeight()];

            for (int x = 0; x < heightMapImage.getWidth(); x++) {
                for (int y = 0; y < heightMapImage.getHeight(); y++) {
                    heightMapData[x][y] = heightMapLoader.getHeight(heightMapImage, x, y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = heightMapImage.getWidth();
        int height = heightMapImage.getHeight();

        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height - 1; y++) {
                int height1 = heightMapData[x][y];
                int height2 = heightMapData[x + 1][y];
                int height3 = heightMapData[x][y + 1];
                int height4 = heightMapData[x + 1][y + 1];

                Point p1 = isoProjection.toScreenCoordinates(x, y);
                Point p2 = isoProjection.toScreenCoordinates(x + 1, y);
                Point p3 = isoProjection.toScreenCoordinates(x, y + 1);
                Point p4 = isoProjection.toScreenCoordinates(x + 1, y + 1);

                // Beispiel für einfache Zeichnung der Kacheln
                Polygon p = new Polygon();
                p.addPoint(p1.x, p1.y - height1);
                p.addPoint(p2.x, p2.y - height2);
                p.addPoint(p4.x, p4.y - height4);
                p.addPoint(p3.x, p3.y - height3);

                g2d.setColor(new Color(0x8B4513)); // Beispiel-Farbe für die Kacheln
                g2d.fillPolygon(p);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2.5D Terrain Renderer");
        TerrainRenderer panel = new TerrainRenderer("terrain.png", "heightmap.png");
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}