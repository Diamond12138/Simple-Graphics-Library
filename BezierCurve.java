package com.Diamond.SGL;

import android.renderscript.*;
import java.util.ArrayList;

public class BezierCurve extends Sprite {
    public ArrayList<Float2> points;
    public float[] vertices;
    
    public BezierCurve() {
        points = new ArrayList<Float2>();
        vertices = new float[0];
    }
    
    
    
    public static float[] createBezierCurve(Float2[] points,float span) {
        ArrayList<Float> vertices = new ArrayList<Float>();
        
        float[] result = new float[vertices.size()];
        return result;
    }
    
    public static long factorial(int number) {
        if(number == 0) {
            return 1;
        }
        
        long result = 1;
        for(int i = number;i > 1;i--) {
            result *= i;
        }
        
        return result;
    }
}
