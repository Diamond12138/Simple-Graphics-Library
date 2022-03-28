package com.Diamond.SGL;

import android.opengl.GLES32;

public class Renderable extends Sprite {
    public int VAO = 0;
    public int vertex_count = 0;

    public Renderable() {}

    public Renderable beRendered(Program program) {
        postMatrix(program,DEFAULT_MODEL_MATRIX_UNIFORM_NAME);
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES,0,vertex_count);
        GLES32.glBindVertexArray(0);
        return this;
    }
    
    public static final String DEFAULT_MODEL_MATRIX_UNIFORM_NAME = "u_model";
    public Renderable postMatrix(Program program,String uniform_name) {
        program.setUniform(uniform_name,getMatrixArray());
        return this;
    }
}
