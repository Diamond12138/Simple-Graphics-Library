package com.Diamond.SGLSample;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.view.WindowManager;
import android.view.Window;
import android.view.View;

public class MainActivity extends Activity {
    public MySurfaceView surfaceView;
    public RelativeLayout relativeLayout1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        surfaceView = new MySurfaceView(this);
        surfaceView.requestFocus();
        surfaceView.setFocusableInTouchMode(true);
        surfaceView.setClickable(true);
        
        relativeLayout1 = (RelativeLayout)findViewById(R.id.activitymainRelativeLayout1);
        relativeLayout1.addView(surfaceView);
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onResume();
    }
} 
