public class Siedler {
    private int x, y;
    private int targetX, targetY;
    private boolean moving;
    
    public Siedler(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        this.targetY = startY;
        this.moving = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTarget(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.moving = true;
    }

    public void update() {
        if (moving) {
            if (x < targetX) x++;
            if (x > targetX) x--;
            if (y < targetY) y++;
            if (y > targetY) y--;

            if (x == targetX && y == targetY) {
                moving = false;
            }
        }
    }
}