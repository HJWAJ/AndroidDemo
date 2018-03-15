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
        // listview crash问题
        findViewById(R.id.btn_list_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://list")));
            }
        });
        // recyclerview crash问题
        findViewById(R.id.btn_recycler_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://recycler")));
            }
        });
        // 地图聚合demo
        findViewById(R.id.btn_map_cluster_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://clustermap")));
            }
        });
        // 行间距demo
        findViewById(R.id.btn_text_linespacing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://linespacing")));
            }
        });
        // 标题上推隐藏下拉悬停
        findViewById(R.id.btn_appbar_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://appbar_recycler")));
            }
        });
        // 使用AppBarLayout做悬浮，有跳变，不可行
        // 使用滚动监听盖上一层做悬浮，逻辑较负责，可行，对于复杂view可能有性能问题
        findViewById(R.id.btn_sticky_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://sticky_recycler")));
            }
        });
        // TODO 测试LayoutManager做悬浮

        // 测试BadTokenException
        findViewById(R.id.btn_decor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://decor1")));
            }
        });
        // 测试ScaleType_Matrix
        findViewById(R.id.btn_image_matrix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://image_matrix")));
            }
        });
        // 测试RecyclerView item动画
        findViewById(R.id.btn_item_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://recycler_item_animation")));
            }
        });
        // 测试转场
        findViewById(R.id.btn_start_activity_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://transition1")));
            }
        });
    }
}
