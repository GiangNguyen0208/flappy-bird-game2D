package org.example.io;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] keysLast = new boolean[GLFW_KEY_LAST];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
    public static boolean isKeyPressedOnce(int key) {
        return keys[key] && !keysLast[key];
    }

}
