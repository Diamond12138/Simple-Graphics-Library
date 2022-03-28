package com.Diamond.SGL;

import java.util.*;

public class Group extends Renderable {
    public List<Renderable> objects;
    
    
    
    public Group() {
        objects = new ArrayList<Renderable>();
    }
    
    public Group(Renderable[] objList) {
        objects = new ArrayList<Renderable>();
        add(objList);
    }
    
    
    
    public Group add(Renderable object) {
        objects.add(object);
        return this;
    }
    
    public Group add(Renderable[] objList) {
        for (int i = 0; i < objList.length; i++) {
            objects.add(objList[i]);
        }
        return this;
    }
    
    
    
    @Override
    public Group beRendered(Program program) {
        program.setUniform("u_isGroup",true);
        postMatrix(program,"u_group_model");
        for (int i = 0; i < objects.size(); i++) {
            program.render(objects.get(i));
        }
        program.setUniform("u_isGroup",false);
        return this;
    }
}
