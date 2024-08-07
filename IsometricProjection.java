public class IsometricProjection {
    private int tileWidth = 64; // Breite des Tiles
    private int tileHeight = 32; // Höhe des Tiles

    public Point toScreenCoordinates(int x, int y) {
        int screenX = (x - y) * tileWidth / 2;
        int screenY = (x + y) * tileHeight / 2;
        return new Point(screenX, screenY);
    }
}