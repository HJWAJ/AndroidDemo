package com.example.huajiawei.myapplication.flexibility;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 2018/4/28.
 */

public class TestInfateAndAddViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_inflate);
        LinearLayout container = findViewById(R.id.container);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            LayoutInflater.from(this).inflate(R.layout.transition_list_item, container, false);
        }
        Log.d("inflate", (System.currentTimeMillis() - start) + ""); // 6159ms

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setLayoutParams(lp);
            TextView tv = new TextView(this);
            tv.setTextColor(0x333333);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(tv, lp);
            ImageView iv = new ImageView(this);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv.setImageResource(R.mipmap.ic_launcher);
            ll.addView(iv);
        }
        Log.d("new", (System.currentTimeMillis() - start) + ""); // 4437ms

        View v = LayoutInflater.from(this).inflate(R.layout.transition_list_item, container, false);;

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            container.addView(v);
            container.removeView(v);
        }
        Log.d("add and remove", (System.currentTimeMillis() - start) + ""); // 26ms
    }
}
