package org.example.graphics;

import org.example.maths.Matrix4f;
import org.example.maths.Vector3f;
import org.example.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int ID;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();
    public Shader(String vertex, String fragment) {
        ID = ShaderUtils.create(vertex, fragment);
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
        glUniform1i(getUniform(name), value);
    }
    public void setUniform1f(String name, float value) {
        glUniform1f(getUniform(name), value);
    }
    public void setUniform2f(String name, float x, float y) {
        glUniform2f(getUniform(name), x, y);
    }
    public void setUniform2f(String name, Vector3f vector3f) {
        glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }
    public void setUniformMatrix4f(String name, Matrix4f matrix4f) {
        glUniformMatrix4fv(getUniform(name), false, matrix4f.toFloatBuffer());
    }
    public void enable() {
        glUseProgram(ID);
    }
    public void disable() {
        glUseProgram(0);
    }
}
