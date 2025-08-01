package org.example.level;

import org.example.graphics.Shader;
import org.example.graphics.Texture;
import org.example.graphics.VertexArray;
import org.example.maths.Matrix4f;
import org.example.maths.Vector3f;

public class Level {
    private VertexArray background, fade;
    private Texture bgTexture;
    private int xScroll = 0;
    private int map = 0;
    public Level() {
        float[] vertices = new float[] {
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                -10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                0.0f, -10.0f * 9.0f / 16.0f, 0.0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        background = new VertexArray(vertices, indices, tcs);
        bgTexture = new Texture("src/main/resources/img/bg.jpeg");
    }
    public void update() {
        xScroll--;
        if (-xScroll % 335 == 0) map++;
    }
    public void render() {
        bgTexture.bind();
        Shader.BACK_GROUND.enable();
        background.bind();
        for (int i = 0; i < map + 4; i++) {
            Matrix4f value = Matrix4f.translate(new Vector3f(i * 10 + xScroll, 0.0f, 0.0f));
            Shader.BACK_GROUND.setUniformMatrix4f("vw_matrix", value);
            background.draw();
        }
//        background.render();
        Shader.BACK_GROUND.disable();
        bgTexture.unbind();
    }
}
