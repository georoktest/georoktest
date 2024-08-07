class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

public class AvoidTempObjects {
    private Vector2 position = new Vector2(0, 0);

    public void updatePosition(float newX, float newY) {
        // Statt: position = new Vector2(newX, newY);
        position.set(newX, newY);
    }

    public static void main(String[] args) {
        AvoidTempObjects obj = new AvoidTempObjects();
        for (int i = 0; i < 1000; i++) {
            obj.updatePosition(i, i);
        }
    }
}