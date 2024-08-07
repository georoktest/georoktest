import java.awt.*;

public class DisplayResolutions {
    public static void main(String[] args) {
        // Holen Sie sich die aktuelle Grafikumgebung
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        // Holen Sie sich alle Bildschirmgeräte
        GraphicsDevice[] devices = ge.getScreenDevices();

        // Schleife durch alle Geräte, um ihre Auflösungen anzuzeigen
        for (GraphicsDevice device : devices) {
            DisplayMode[] displayModes = device.getDisplayModes();
            System.out.println("Display Modes for Device: " + device.getIDstring());
            for (DisplayMode mode : displayModes) {
                System.out.printf("Width: %d, Height: %d, Bit Depth: %d, Refresh Rate: %d%n",
                                  mode.getWidth(), mode.getHeight(), mode.getBitDepth(), mode.getRefreshRate());
            }
        }
    }
}