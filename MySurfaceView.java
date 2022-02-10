package com.Diamond.SGLSample;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.content.Context;
import android.renderscript.Float3;
import android.opengl.GLES32;
import android.view.MotionEvent;
import java.io.IOException;
import android.content.res.AssetManager;
import android.graphics.RectF;
import android.renderscript.Float4;
import com.Diamond.SGL.*;

public class MySurfaceView extends GLSurfaceView {
    public class MyRenderer implements GLSurfaceView.Renderer {
        public Program program;
        public Axis axis;
        public Camera camera;
        public GLES32 gl;
        
        @Override
        public void onSurfaceCreated(GL10 p1, EGLConfig p2) {
            program = new Program("v.vert","usual.frag",null,getResources());
            
            axis = new Axis();
            
            camera = new Camera();
            camera.setView(new Float3(1,1,1),new Float3(0,0,0),new Float3(0,1,0));
            camera.setPerspective(120.0f,0.5f,0.1f,100.0f);
            
            gl.glEnable(gl.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 p1, int p2, int p3) {
            
        }

        @Override
        public void onDrawFrame(GL10 p1) {
            gl.glClearColor(0.1f,0.1f,0.1f,1.0f);
            gl.glClear(gl.GL_COLOR_BUFFER_BIT|gl.GL_DEPTH_BUFFER_BIT);
            
            program.useProgram();
            camera.draw(program);
            axis.draw(program);
        }
    }
    
    
    
    public MyRenderer renderer;
    public float lastX,lastY;
    public MySurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        renderer = new MyRenderer();
        setRenderer(renderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            float nowX = event.getX();
            float nowY = event.getY();
            
            float dx = nowX - lastX;
            float dy = nowY - lastY;
            
            dx *= 0.1f;
            dy *= 0.1f;
            
                renderer.camera.rotate(dx,dy);
            
            lastX = nowX;
            lastY = nowY;
        }
        return super.onTouchEvent(event);
    }
}
