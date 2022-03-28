package com.Diamond.SGL;

import android.opengl.GLES32;

public class Axis extends Renderable {
    public Axis() {
        float[] vertices = new float[]{
            -100,0,0,
            100,0,0,
            
            0,-100,0,
            0,100,0,
            
            0,0,-100,
            0,0,100
        };
        float[] colors = new float[]{
            1,0,0,1,
            1,0,0,1,
            
            0,1,0,1,
            0,1,0,1,
            
            0,0,1,1,
            0,0,1,1
        };
        
        int[] result = BufferUtil.bindVAO(new ObjLoader(vertices,colors),GLES32.GL_STATIC_DRAW);
        VAO = result[0];
        vertex_count = vertices.length / 3;
        BufferUtil.deleteVBO(result);
    }

    @Override
    public Axis beRendered(Program program) {
        postMatrix(program,DEFAULT_MODEL_MATRIX_UNIFORM_NAME);
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_LINES,0,vertex_count);
        GLES32.glBindVertexArray(VAO);
        return this;
    }
}
