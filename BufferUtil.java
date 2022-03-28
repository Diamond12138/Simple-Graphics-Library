package com.Diamond.SGL;

import android.opengl.GLES32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;

public class BufferUtil {
    public static ByteBuffer newByteBuffer(int capacity) {
        ByteBuffer bb = ByteBuffer.allocateDirect(capacity);
        bb.order(ByteOrder.nativeOrder());
        return bb;
    }
    public static FloatBuffer toFloatBuffer(float[] array) {
        FloatBuffer fb = newByteBuffer(array.length * 4).asFloatBuffer();
        fb.put(array).position(0);
        return fb;
    }
    public static IntBuffer toIntBuffer(int[] array) {
        IntBuffer ib = newByteBuffer(array.length * 4).asIntBuffer();
        ib.put(array).position(0);
        return ib;
    }



    public static void enableVertexAttrib(int location, int vertex_size) {
        GLES32.glEnableVertexAttribArray(location);
        GLES32.glVertexAttribPointer(location, vertex_size, GLES32.GL_FLOAT, false, vertex_size * 4, 0);
    }
    public static void bindBufferData(int buffer, int type, int data_size, Buffer data, int usage) {
        GLES32.glBindBuffer(type, buffer);
        GLES32.glBufferData(type, data_size, data, usage);
    }



    public static int genBuffer() {
        int[] buffer = new int[1];
        GLES32.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }
    public static int[] genBuffers(int number) {
        number = Math.max(number, 1);
        int[] buffers = new int[number];
        GLES32.glGenBuffers(number, buffers, 0);
        return buffers;
    }
    public static void deleteBuffer(int buffer) {
        if (buffer != 0) {
            GLES32.glDeleteBuffers(1, new int[]{ buffer }, 0);
        }
    }
    public static void deleteBuffers(int[] buffers) {
        GLES32.glDeleteBuffers(buffers.length, buffers, 0);
    }



    public static int genVertexArray() {
        int[] buffer = new int[1];
        GLES32.glGenVertexArrays(1, buffer, 0);
        return buffer[0];
    }
    public static int[] genVertexArray(int number) {
        number = Math.max(number, 1);
        int[] buffers = new int[number];
        GLES32.glGenVertexArrays(number, buffers, 0);
        return buffers;
    }
    public static void deleteVertexArray(int buffer) {
        if (buffer != 0) {
            GLES32.glDeleteVertexArrays(1, new int[]{ buffer }, 0);
        }
    }
    public static void deleteVertexArray(int[] buffers) {
        GLES32.glDeleteVertexArrays(buffers.length, buffers, 0);
    }



    public static int bindVBO(int location, int vertex_size, float[] vertices, int usage) {
        int VBO = genBuffer();
        bindBufferData(VBO, GLES32.GL_ARRAY_BUFFER, vertices.length * 4, toFloatBuffer(vertices), usage);
        enableVertexAttrib(location, vertex_size);
        return VBO;
    }
    public static int bindEBO(int[] indices, int usage) {
        int EBO = genBuffer();
        bindBufferData(EBO, GLES32.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, toIntBuffer(indices), usage);
        return EBO;
    }



    public static boolean bindMapBuffer(int type, int offset, int data_size, Buffer data, int access) {
        ByteBuffer bb = (ByteBuffer)GLES32.glMapBufferRange(type, offset, data_size, access);
        if (bb == null) {
            return false;
        }
        bb.order(ByteOrder.nativeOrder());
        bb = (ByteBuffer)data;
        bb.position(0);
        return GLES32.glUnmapBuffer(type);
    }
    public static boolean bindMapBuffer(int type, int offset, float[] array, int access) {
        return bindMapBuffer(type, offset, array.length * 4, toFloatBuffer(array), access);
    }
    public static boolean bindMapBuffer(int type, int offset, int[] array, int access) {
        return bindMapBuffer(type, offset, array.length * 4, toIntBuffer(array), access);
    }

    public static boolean bindVBOMap(int offset, float[] array, int access) {
        return bindMapBuffer(GLES32.GL_ARRAY_BUFFER, offset, array, access);
    }
    public static boolean bindEBOMap(int offset, int[] array, int access) {
        return bindMapBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, offset, array, access);
    }



    public static int genFBO() {
        int[] fbo = new int[1];
        GLES32.glGenFramebuffers(1, fbo, 0);
        return fbo[0];
    }

    public static void bindFBO(int fbo) {
        GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER, fbo);
    }

    public static void deleteFBO(int fbo) {
        if (fbo != 0) {
            GLES32.glDeleteFramebuffers(1, new int[]{ fbo }, 0);
        }
    }



    public static int genRBO() {
        int[] rbo = new int[1];
        GLES32.glGenRenderbuffers(1, rbo, 0);
        return rbo[0];
    }

    public static void bindRBO(int rbo) {
        GLES32.glBindRenderbuffer(GLES32.GL_RENDERBUFFER, rbo);
    }

    public static void bindRBO(int rbo, int type, int width, int height) {
        bindRBO(rbo);
        GLES32.glRenderbufferStorage(GLES32.GL_RENDERBUFFER, type, width, height);
        bindRBO(0);
    }

    public static void deleteRBO(int rbo) {
        if (rbo != 0) {
            GLES32.glDeleteRenderbuffers(1, new int[]{ rbo }, 0);
        }
    }

    public static int[] bindVAO(ObjLoader loader, int usage) {
    	int VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position, 3, loader.vertices, usage);
        int vbo2 = 0;
        if (loader.colors != null) {
            vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_color, 4, loader.colors, usage);
        }
        int vbo3 = 0;
        if (loader.normals != null) {
            vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal, 3, loader.normals, usage);
        }
        int vbo4 = 0;
        if (loader.texCoords != null) {
            vbo4 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord, 3, loader.texCoords, usage);
        }
        int ebo = 0;
        if (loader.indices != null) {
            ebo = BufferUtil.bindEBO(loader.indices, usage);
        }
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, 0);
        return new int[]{VAO,vbo1,vbo2,vbo3,vbo4,ebo};
    }
    
    public static void deleteVAO(int[] buffers) {
        GLES32.glDeleteVertexArrays(1,new int[]{ buffers[0] },0);
        for (int i = 1; i < buffers.length; i++) {
            GLES32.glDeleteBuffers(1,new int[]{ buffers[i] },0);
        }
    }
    
    public static void deleteVBO(int[] buffers) {
        for (int i = 1; i < buffers.length; i++) {
            GLES32.glDeleteBuffers(1,new int[]{ buffers[i] },0);
        }
    }
}
