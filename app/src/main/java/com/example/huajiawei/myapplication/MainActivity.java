package com.example.huajiawei.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by huajiawei on 15/12/25.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_list_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://list")));
            }
        });
        findViewById(R.id.btn_recycler_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://recycler")));
            }
        });
        findViewById(R.id.btn_map_cluster_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://clustermap")));
            }
        });
        findViewById(R.id.btn_text_linespacing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://linespacing")));
            }
        });
        findViewById(R.id.btn_appbar_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://appbar_recycler")));
            }
        });
        findViewById(R.id.btn_decor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://decor1")));
            }
        });
        findViewById(R.id.btn_ncnn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://ncnn")));
            }
        });
    }
}
