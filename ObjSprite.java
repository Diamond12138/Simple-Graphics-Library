package com.Diamond.SGL;

import java.io.InputStream;
import android.opengl.GLES32;
import java.util.Random;

public class ObjSprite extends Sprite {
    public int VAO;
    public int vCount;
    
    public ObjSprite(InputStream is) {
        ObjLoader result = new ObjLoader(is);
        
        vCount = result.vertices.length / 3;
        
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        float[] colors = new float[vCount * 4];
        for (int i = 0; i < colors.length; i += 4) {
            colors[i] = r.nextFloat();
            colors[i + 1] = r.nextFloat();
            colors[i + 2] = r.nextFloat();
            colors[i + 3] = 1;
        }
        
        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position,3,result.vertices,GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_color,4,colors,GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal,3,result.normals,GLES32.GL_STATIC_DRAW);
        int vbo4 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord,3,result.texCoords,GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3,vbo4});
    }
    
    public ObjSprite draw(Program program) {
        program.setUniform("u_model",getMatrixArray());
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES,0,vCount);
        GLES32.glBindVertexArray(0);
        return this;
    }
}
