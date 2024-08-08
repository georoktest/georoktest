public class SoundManager implements Runnable {
    @Override
    public void run() {
        while (true) {
            // Hier die Sound-Logik implementieren
            try {
                Thread.sleep(100); // Sound-Thread pausieren
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}