package com.Diamond.SGL;

import com.Diamond.SGL.*;
import android.renderscript.Float2;
import android.view.MotionEvent;

public class CameraController {
    public static final float DEFAULT_SPEED = 0.1f;

    public Camera camera;
    public Float2[] lasts;
    public float speed;

    public CameraController(Camera c) {
        camera = c;
        speed = DEFAULT_SPEED;
        lasts = new Float2[2];
    }
    public CameraController(Camera c, float s) {
        camera = c;
        speed = s;
        lasts = new Float2[2];
    }

    public CameraController update(MotionEvent event) {
        int pointer_count = event.getPointerCount();
        int mode = camera.getOrbitMode();

        if (pointer_count > 0) {
            Float2[] nows = new Float2[2];

            nows[0] = new Float2(event.getX(0), event.getY(0));
            if (pointer_count > 1) {
                nows[1] = new Float2(event.getX(1), event.getY(1));
            } else {
                nows[1] = nows[0];
            }

            int action = event.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                lasts = nows;
            } else if (action == MotionEvent.ACTION_MOVE) {
                Float2 delta = new Float2(nows[0].x - lasts[0].x, nows[0].y - lasts[0].y);

                delta.x *= speed;
                delta.y *= speed;

                if (pointer_count == 1) {
                    if (mode == Camera.ORBIT_CENTER) {
                        camera.rotate(-delta.x, delta.y);
                    } else if (mode == Camera.ORBIT_POSITION) {
                        camera.rotate(-delta.x, -delta.y);
                    }
                } else if (pointer_count > 1) {
                    float fovy = camera.getFovy();

                    Float2 d1 = new Float2(nows[0].x - nows[1].x, nows[0].y - nows[1].y);
                    Float2 d2 = new Float2(lasts[0].x - lasts[1].x, lasts[0].y - lasts[1].y);
                    float nowh = (float)Math.hypot((double)d1.x, (double)d1.y);
                    float lasth = (float)Math.hypot((double)d2.x, (double)d2.y);
                    
                    fovy *= lasth / nowh;
                    camera.setFovy(fovy);
                }
            }

            lasts = nows;
        }

        return this;
    }
}
