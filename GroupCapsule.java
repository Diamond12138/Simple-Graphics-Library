package com.Diamond.SGL;
import android.renderscript.Float3;

public class GroupCapsule extends Group {
    public GroupCapsule(float radius,float length,int count) {
        float half_length = length / 2.0f;
        
        Sphere upper = new Sphere(count,count,0,0,360,180);
        Cylinder center = new Cylinder(count);
        Sphere nether = new Sphere(count,count,0,180,360,180);
        
        upper.setScale(radius).setPositionY(half_length);
        center.setScale(new Float3(radius,length,radius));
        nether.setScale(radius).setPositionY(-half_length);
        
        add(upper).add(center).add(nether);
    }
}
