package org.example;

import org.example.graphics.Shader;
import org.example.io.Input;
import org.example.io.Window;
import org.example.level.Level;
import org.example.maths.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Main implements Runnable {
    private int width = 1280;
    private int height = 720;

    private Thread thread;
    private boolean running = false;
    private long window;
    private Level level;

    public void start() {
        running = true;
        thread = new Thread(this, "Game Flappy Bird");
        thread.start();
    }
    public void init() {
        // Khởi tạo GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Cấu hình cửa sổ
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Ẩn cửa sổ cho đến khi sẵn sàng

        // Tạo cửa sổ
        window = glfwCreateWindow(width, height, "Flappy Bird", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create GLFW window");
        }

        // Lấy thông tin video mode
        long primaryMonitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(primaryMonitor); // Trả về GLFWVidMode
        if (vidmode != null) {
            glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
        }

        glfwSetKeyCallback(window, new Input());

        // Làm cho ngữ cảnh OpenGL hiện tại
        glfwMakeContextCurrent(window);
        // Hiển thị cửa sổ
        glfwShowWindow(window);
        // Khởi tạo khả năng OpenGL
        GL.createCapabilities();

//        glClearColor(1.0f,1.0f,1.0f,1.0f);
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
        Shader.loadAll();

        org.example.maths.Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
        Shader.BACK_GROUND.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BACK_GROUND.setUniform1i("tex", 1);

//        Shader.BIRD.setUniformMatrix4f("pr_matrix", pr_matrix);
//        Shader.BIRD.setUniform1i("tex", 1);
//
//        Shader.PIPE.setUniformMatrix4f("pr_matrix", pr_matrix);
//        Shader.PIPE.setUniform1i("tex", 1);

        level = new Level();
    }
    public void run() {
        init();

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        int updates = 0;
        int frames = 0;
        long timer = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
            if (glfwWindowShouldClose(window))
                running = false;
        }
        cleanup();
    }

    public void update() {
        // Thêm logic cập nhật game (ví dụ: di chuyển chim)
        glfwPollEvents();
        level.update();
    }

    public void render() {
        // Xóa màn hình
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        level.render();

        int error = glGetError();
        if (error != GL_NO_ERROR)
            System.out.println("ERROR code: " + error);

        glfwSwapBuffers(window);
    }

    public void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}