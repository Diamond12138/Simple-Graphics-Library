package com.Diamond.SGL;

import android.renderscript.Float3;
import java.util.*;
import android.opengl.GLES32;

public class Cylinder extends RegularShapeCreator {
    public static Float3 getVertex(float angle,float y) {
        float x = (float)Math.sin(Math.toRadians((float)angle)) * RADIUS;
        float z = (float)Math.cos(Math.toRadians((float)angle)) * RADIUS;
        return new Float3(x,y,z);
    }
    
    public static Float3 getNormal(float angle) {
        float x = (float)Math.sin(Math.toRadians((float)angle));
        float z = (float)Math.cos(Math.toRadians((float)angle));
        return VectorUtil.normalize(new Float3(x,0,z));
    }
    
    public static float[] genVertices(float start,float count,float span) {
        float end = start + count;
        if(start > end) {
            swap(start,end);
        }
        
        final float upper = 0.5f;
        final float nether = -0.5f;
        Float3 upperCenter = new Float3(0,upper,0);
        Float3 netherCenter = new Float3(0,nether,0);
        
        List<Float> vertices = new ArrayList<Float>();
        for(float angle = start;angle <= (end - span);angle += span) {
            Float3 x0u = getVertex(angle,upper);
            Float3 x1u = getVertex(angle + span,upper);
            Float3 x0n = getVertex(angle,nether);
            Float3 x1n = getVertex(angle + span,nether);
            
            add_triangle_to_List(vertices,upperCenter,x0u,x1u);
            add_triangle_to_List(vertices,x0u,x0n,x1u);
            add_triangle_to_List(vertices,x1u,x0n,x1n);
            add_triangle_to_List(vertices,x0n,netherCenter,x1n);
        }
        return toArray(vertices);
    }
    
    public static float[] genNormals(float start,float count,float span) {
    	float end = start + count;
        if(start > end) {
            swap(start,end);
        }
        
        final Float3 upper = new Float3(0,1,0);
        final Float3 nether = new Float3(0,-1,0);
        
        List<Float> normals = new ArrayList<Float>();
        for(float angle = start;angle <= (end - span);angle += span) {
            Float3 x0 = getNormal(angle);
            Float3 x1 = getNormal(angle + span);
            
            add_triangle_to_List(normals,upper,upper,upper);
            add_triangle_to_List(normals,x0,x0,x1);
            add_triangle_to_List(normals,x1,x0,x1);
            add_triangle_to_List(normals,nether,nether,nether);
        }
        return toArray(normals);
    }
    
    public static ObjLoader createCylinder(float start,float count,float span) {
        float[] vertices = genVertices(start,count,span);
        float[] normals = genNormals(start,count,span);
        return new ObjLoader(vertices,normals,null,null);
    }
    
    public static ObjLoader createCylinder(float start,float angle_count,int count) {
    	float span = 1.0f / (float)count;
        return createCylinder(start,angle_count,span);
    }
    
    
    
    public Cylinder() {
        ObjLoader loader = createCylinder(0,360,18);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Cylinder(int count) {
        ObjLoader loader = createCylinder(0,360,count);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Cylinder(float span) {
        ObjLoader loader = createCylinder(0,360,span);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Cylinder(float start,float angle_count,int count) {
        ObjLoader loader = createCylinder(start,angle_count,count);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
    public Cylinder(float start,float count,float span) {
        ObjLoader loader = createCylinder(start,count,span);
        int[] bufs = BufferUtil.bindVAO(loader,GLES32.GL_STATIC_DRAW);
        VAO = bufs[0];
        BufferUtil.deleteVBO(bufs);
        vertex_count = loader.vertices.length / 3;
    }
}
