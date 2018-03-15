package com.example.huajiawei.myapplication.transition;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 2018/3/15.
 */

public class TransitionActivity2 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.text).setTransitionName("image");
        }
    }
}
