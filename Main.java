import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class Main {
    private long window;
    private GLFWGamepadState gamepadState = GLFWGamepadState.create();

    public void run() {
        init();
        loop();

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

        long monitor = GLFW.glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);

        window = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), "Borderless Window", MemoryUtil.NULL, MemoryUtil.NULL);

        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // V-Sync aktivieren
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        // Tastatur-Callback
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                System.out.println("Key pressed: " + key);
            }
        });

        // Maus-Callback
        GLFW.glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            System.out.println("Mouse moved to: " + xpos + ", " + ypos);
        });

        GLFW.glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                System.out.println("Mouse button pressed: " + button);
            }
        });
    }

    private void loop() {
        final double nsPerTick = 1.0e9 / 60.0;
        long lastTime = System.nanoTime();
        double unprocessed = 0.0;
        int frames = 0;
        int updates = 0;

        while (!GLFW.glfwWindowShouldClose(window)) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            boolean shouldRender = false;

            while (unprocessed >= 1.0) {
                updates++;
                handleInput();
                unprocessed -= 1.0;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
                GLFW.glfwSwapBuffers(window);
            }

            GLFW.glfwPollEvents();
        }

        System.out.println("FPS: " + frames);
        System.out.println("UPS: " + updates);
    }

    private void handleInput() {
        // Tastatur und Maus-Eingaben wurden bereits in den Callbacks behandelt
        // Hier kannst du zusätzlich Gamepad-Eingaben verarbeiten
        if (GLFW.glfwGetGamepadState(GLFW.GLFW_GAMEPAD1, gamepadState)) {
            System.out.println("Gamepad A Button: " + (gamepadState.buttons(GLFW.GLFW_GAMEPAD_BUTTON_A) ? "Pressed" : "Not Pressed"));
        }
    }

    private void render() {
        // Beispiel-Rendermethode
        GL.createCapabilities();
        GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Weitere Rendering-Befehle hier
    }

    public static void main(String[] args) {
        new Main().run();
    }
}