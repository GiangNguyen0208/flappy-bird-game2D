package org.example;

import org.example.io.Input;
import org.example.io.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
    private Window window;
    private Thread thread;
    private Input input;
    private boolean running = false;

    public Main() {
        window = new Window(1280, 720); // Kích thước mặc định
    }

    public void start() {
        running = true;
        thread = new Thread(this, "Game Flappy Bird");
        thread.start();
    }

    public void init() {
        window.init();
    }

    public void run() {
        init();
        while (running) {
            update();
            render();
            if (window.shouldClose()) {
                running = false;
            }
            window.swapBuffers();
        }
        cleanup();
    }

    public void update() {
        // Thêm logic cập nhật game (ví dụ: di chuyển chim)
        glfwPollEvents();
        if (Input.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindow(), true);
        }
        if (Input.isKeyDown(GLFW_KEY_SPACE)) {
            System.out.println("OK");
        }
    }

    public void render() {
        // Xóa màn hình
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Ví dụ: Vẽ hình vuông
        glBegin(GL_TRIANGLES);
        glVertex2f(-0.5f, 0.5f);  // Top Left
        glVertex2f(0.5f, 0.5f);   // Top Right
        glVertex2f(0.5f, -0.5f);  // Bottom Right
        glVertex2f(0.5f, -0.5f);  // Bottom Right
        glVertex2f(-0.5f, -0.5f); // Bottom Left
        glVertex2f(-0.5f, 0.5f);  // Top Left
        glEnd();
    }

    public void cleanup() {
        window.destroy();
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }
}