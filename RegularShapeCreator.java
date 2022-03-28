package com.Diamond.SGL;

import java.util.List;
import android.renderscript.Float3;

public class RegularShapeCreator extends Renderable {
    public static final float RADIUS = 0.5f;
    
    public static float[] toArray(List<Float> array) {
        float[] result = new float[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i);
        }
        return result;
    }
    
    public static void add_Float3_to_List(List<Float> list,Float3 value) {
        list.add(value.x);
        list.add(value.y);
        list.add(value.z);
    }
    
    public static void add_triangle_to_List(List<Float> list,Float3 a,Float3 b,Float3 c) {
        add_Float3_to_List(list,a);
        add_Float3_to_List(list,b);
        add_Float3_to_List(list,c);
    }
    
    public static void swap(float a,float b) {
        float c = a;
        a = b;
        b = c;
    }
}
