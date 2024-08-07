import java.util.ArrayList;
import java.util.List;

class Particle {
    public int x, y;
    public boolean active;

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    public void update() {
        // Update particle state
        if (x > 100) { // Dummy condition to deactivate particle
            active = false;
        }
        x++;
    }
}

class ParticlePool {
    private List<Particle> pool;

    public ParticlePool(int size) {
        pool = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Particle());
        }
    }

    public Particle getParticle(int x, int y) {
        for (Particle p : pool) {
            if (!p.active) {
                p.reset(x, y);
                return p;
            }
        }
        return null; // Pool exhausted
    }

    public void updateAll() {
        for (Particle p : pool) {
            if (p.active) {
                p.update();
            }
        }
    }
}

public class ParticleSystem {
    private ParticlePool particlePool;

    public ParticleSystem(int poolSize) {
        particlePool = new ParticlePool(poolSize);
    }

    public void createParticle(int x, int y) {
        particlePool.getParticle(x, y);
    }

    public void update() {
        particlePool.updateAll();
    }

    public static void main(String[] args) {
        ParticleSystem ps = new ParticleSystem(100);

        for (int i = 0; i < 10; i++) {
            ps.createParticle(i * 10, i * 10);
        }

        ps.update();
    }
}