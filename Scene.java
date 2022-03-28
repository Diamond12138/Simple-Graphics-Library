package com.Diamond.SGL;

import android.opengl.GLES32;
import android.renderscript.*;

public class Scene {
    public class Enables {
        public boolean depth_test = true;
        public boolean stencil_test = false;
        public boolean scissor_test = false;
    }
    public static final int CLEAR_COLOR_DEPTH_MASK = GLES32.GL_COLOR_BUFFER_BIT|GLES32.GL_DEPTH_BUFFER_BIT;
    
    public Int4 viewport;
    public Float4 backgroundColor;
    public Int4 scissor_area;
    
    public Enables enables;
    
    public Scene(int width,int height) {
        viewport = new Int4(0,0,width,height);
        backgroundColor = new Float4(0.1f,0.1f,0.1f,1.0f);
        enables = new Enables();
        scissor_area = new Int4(0,0,width,height);
    }
    
    
    
    public Scene draw() {
        return clear();
    }
    
    public Scene clear() {
        GLES32.glViewport(viewport.x,viewport.y,viewport.z,viewport.w);
        
        if(enables.scissor_test) {
            enableScissor();
        } else {
            GLES32.glDisable(GLES32.GL_SCISSOR_TEST);
        }
        GLES32.glClearColor(backgroundColor.x,backgroundColor.y,backgroundColor.z,backgroundColor.w);
        
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT);
        if(enables.depth_test) {
            GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT);
        }
        if(enables.stencil_test) {
            GLES32.glClear(GLES32.GL_STENCIL_BUFFER_BIT);
        }
        return this;
    }
    
    public Scene enableScissor() {
        GLES32.glEnable(GLES32.GL_SCISSOR_TEST);
        GLES32.glScissor(scissor_area.x,scissor_area.y,scissor_area.z,scissor_area.w);
        return this;
    }
    
    
    
    public Scene setDepthTest(boolean enable) {
        enables.depth_test = enable;
        return this;
    }
    public Scene setStencilTest(boolean enable) {
        enables.stencil_test = enable;
        return this;
    }
    public Scene setScissorTest(boolean enable) {
        enables.scissor_test = enable;
        return this;
    }
}
