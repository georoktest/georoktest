import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HeightMapLoader {
    public BufferedImage loadHeightMap(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getHeight(BufferedImage heightMap, int x, int y) {
        int color = heightMap.getRGB(x, y);
        return (color >> 16) & 0xFF; // Nur den Rot-Kanal verwenden (8-Bit-Höhe)
    }
}