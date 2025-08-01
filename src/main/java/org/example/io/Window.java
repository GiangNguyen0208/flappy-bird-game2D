package org.example.io;

import org.example.graphics.Shader;
import org.example.level.Level;
import org.example.maths.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL12;


import java.nio.ByteBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Window {
    private long window;
    private int width, height;
    private Level level;
    public Window(int width, int height) {
        this.width = width;
        this.height = height;
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

        Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
        Shader.BACK_GROUND.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BACK_GROUND.setUniform1i("tex", 1);

//        Shader.BIRD.setUniformMatrix4f("pr_matrix", pr_matrix);
//        Shader.BIRD.setUniform1i("tex", 1);
//
//        Shader.PIPE.setUniformMatrix4f("pr_matrix", pr_matrix);
//        Shader.PIPE.setUniform1i("tex", 1);

        level = new Level();
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public void destroy() {
        glfwDestroyWindow(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Level getLevel() {
        return level;
    }
}