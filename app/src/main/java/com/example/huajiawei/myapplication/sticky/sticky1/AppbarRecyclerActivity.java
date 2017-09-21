package com.example.huajiawei.myapplication.sticky.sticky1;

import android.app.Activity;
import android.os.Bundle;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 17/6/30.
 * 这个方案会导致列表跳一下，舍弃。
 */

public class AppbarRecyclerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_recycler);
        StickyLayout stickyLayout = (StickyLayout) findViewById(R.id.sticky);
        stickyLayout.setAdapter(new StickyLayout.StickyLayoutAdapter());
    }
}
