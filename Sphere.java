package com.Diamond.SGL;

import android.renderscript.Float3;
import java.util.*;
import android.opengl.GLES32;

public class Sphere extends RegularShapeCreator {
    public static Float3 getVertex(float xz_angle,float y_angle) {
        float y = (float)Math.sin(Math.toRadians((float)y_angle)) * RADIUS;
        float h = (float)Math.cos(Math.toRadians((float)y_angle)) * RADIUS;
        float x = (float)Math.sin(Math.toRadians((float)xz_angle)) * h;
        float z = (float)Math.cos(Math.toRadians((float)xz_angle)) * h;
        return new Float3(x,y,z);
    }
    
    public static float[] genVertices(float xz_span,float y_span,float xz_angle_start,float y_angle_start,float xz_angle_count,float y_angle_count) {
        float xz_angle_end = xz_angle_start + xz_angle_count;
        float y_angle_end = y_angle_start + y_angle_count;
        if(xz_angle_start > xz_angle_end) {
            swap(xz_angle_start,xz_angle_end);
        }
        if(y_angle_start > y_angle_end) {
            swap(y_angle_start,y_angle_end);
        }
        
        List<Float> vertices = new ArrayList<Float>();
        for(float xz_angle = xz_angle_start;xz_angle <= (xz_angle_end - xz_span);xz_angle += xz_span) {
            for(float y_angle = y_angle_start;y_angle <= (y_angle_end - y_span);y_angle += y_span) {
                Float3 x0y0 = getVertex(xz_angle,y_angle);
                Float3 x1y0 = getVertex(xz_angle + xz_span,y_angle);
                Float3 x0y1 = getVertex(xz_angle,y_angle + y_span);
                Float3 x1y1 = getVertex(xz_angle + xz_span,y_angle + y_span);
                
                add_triangle_to_List(vertices,x0y0,x0y1,x1y0);
                add_triangle_to_List(vertices,x1y0,x0y1,x1y1);
            }
        }
        return toArray(vertices);
    }
    
    public static ObjLoader createSphere(float xz_span,float y_span,float xz_angle_start,float y_angle_start,float xz_angle_count,float y_angle_count) {
        float[] vertices = genVertices(xz_span,y_span,xz_angle_start,y_angle_start,xz_angle_count,y_angle_count);
        return new ObjLoader(vertices,vertices,null,null);
    }
    
    public static ObjLoader createSphere(int xz_count,int y_count,float xz_angle_start,float y_angle_start,float xz_angle_count,float y_angle_count) {
        float xz_span = 360.0f / (float)xz_count;
        float y_span = 360.0f / (float)y_count;
        return createSphere(xz_span,y_span,xz_angle_start,y_angle_start,xz_angle_count,y_angle_count);
    }
    
    
    
    public Sphere() {
        ObjLoader loader = createSphere(18,18,0,0,360,360);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Sphere(float xz_span,float y_span) {
        ObjLoader loader = createSphere(xz_span,y_span,0,0,360,360);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Sphere(int xz_count,int y_count) {
        ObjLoader loader = createSphere(xz_count,y_count,0,0,360,360);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Sphere(float xz_span,float y_span,float xz_angle_start,float y_angle_start,float xz_angle_count,float y_angle_count) {
        ObjLoader loader = createSphere(xz_span,y_span,xz_angle_start,y_angle_start,xz_angle_count,y_angle_count);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Sphere(int xz_count,int y_count,float xz_angle_start,float y_angle_start,float xz_angle_count,float y_angle_count) {
        ObjLoader loader = createSphere(xz_count,y_count,xz_angle_start,y_angle_start,xz_angle_count,y_angle_count);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
}
