package org.example.graphics;

import org.example.maths.Matrix4f;
import org.example.maths.Vector3f;
import org.example.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;

    public static Shader BACK_GROUND;
    public static Shader BG, BIRD, PIPE, FADE;
    private final int ID;
    private boolean enabled = false;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();
    public Shader(String vertex, String fragment) {
        ID = ShaderUtils.load(vertex, fragment);
    }
    public static void loadAll() {
        BACK_GROUND = new Shader("src/main/resources/shaders/bg.vert", "src/main/resources/shaders/bg.frag");
//        BIRD = new Shader("src/main/resources/shaders/bird.vert", "src/main/resources/shaders/bird.frag");
//        PIPE = new Shader("src/main/resources/shaders/pipe.vert", "src/main/resources/shaders/pipe.frag");
//        FADE = new Shader("src/main/resources/shaders/fade.vert", "src/main/resources/shaders/fade.frag");
    }
    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);
        int result = glGetUniformLocation(ID, name);
        if (result == -1)
            System.out.println("Could not find uniform variable '" + name + "'!");
        else
            locationCache.put(name, result);
        return result;
    }
    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }
    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }
    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }
    public void setUniform2f(String name, Vector3f vector3f) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }
    public void setUniformMatrix4f(String name, Matrix4f matrix4f) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix4f.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(ID);
        enabled = true;
    }
    public void disable() {
        glUseProgram(0);
        enabled = false;
    }
}
