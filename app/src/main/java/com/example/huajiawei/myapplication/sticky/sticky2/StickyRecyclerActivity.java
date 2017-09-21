package com.example.huajiawei.myapplication.sticky.sticky2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;
import com.example.huajiawei.myapplication.sticky.sticky1.StickyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajiawei on 17/9/19.
 */

public class StickyRecyclerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recycler);
        FrameLayout containerView = (FrameLayout) findViewById(R.id.container);
        StickyRecyclerView stickyRecyclerView = (StickyRecyclerView)
                getLayoutInflater().inflate(R.layout.sticky_recycler_view, containerView, false);
        containerView.addView(stickyRecyclerView);
        stickyRecyclerView.setAdapter(new MyStickyRecyclerAdapter(new RecyclerAdapter()));
    }

    public class MyStickyRecyclerAdapter extends StickyRecyclerAdapter {

        public MyStickyRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {
            super(recyclerAdapter);
        }

        @Override
        public View createStickyView(ViewGroup parent, int position) {
            if (position == 6 || position == 20 || position == 40) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn, parent, false);
                tv.setText("我是第" + position + "个item");
                tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setTextSize(14);
                return tv;
            }
            return null;
        }

        @Override
        public int getStickyRange(int position) {
            if (position == 20) {
                return 50;
            }
            return Integer.MAX_VALUE;
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter {

        private List<String> mStringList = new ArrayList<>();

        public RecyclerAdapter() {
            for (int i = 0; i < 100; i++) {
                mStringList.add("我是第" + i + "个item");
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
