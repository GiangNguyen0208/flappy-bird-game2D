package org.example.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;
    private int width, height;

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

        // Làm cho ngữ cảnh OpenGL hiện tại
        glfwMakeContextCurrent(window);

        // Khởi tạo khả năng OpenGL
        GL.createCapabilities();

        // Hiển thị cửa sổ
        glfwShowWindow(window);
    }

    public void update() {
        glfwPollEvents();
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
}