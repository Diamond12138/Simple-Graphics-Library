package com.Diamond.SGL;

import java.io.InputStream;
import android.opengl.GLES32;
import java.util.Random;

public class ObjSprite extends Renderable {
    public ObjSprite(int vao,int vcount) {
        VAO = vao;
        vertex_count = vcount;
    }
    public ObjSprite(InputStream is) {
        ObjLoader result = new ObjLoader(is);
        
        vertex_count = result.vertices.length / 3;
        
        int[] buffers = BufferUtil.bindVAO(result,GLES32.GL_STATIC_DRAW);
        VAO = buffers[0];
        BufferUtil.deleteVBO(buffers);
    }
    public ObjSprite(ObjLoader loader) {
        vertex_count = loader.vertices.length / 3;
        
        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position,3,loader.vertices,GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal,3,loader.normals,GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord,3,loader.texCoords,GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3});
    }
}
