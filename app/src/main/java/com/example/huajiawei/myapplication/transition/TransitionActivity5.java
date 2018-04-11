package com.example.huajiawei.myapplication.transition;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MyItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 2018/4/10.
 */

public class TransitionActivity5 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.text).setTransitionName("image");
            findViewById(R.id.image).setTransitionName("text");
        }
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new MyItemAnimator());
        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                adapter.itemCount = 3;
                adapter.notifyItemRangeInserted(0, adapter.itemCount);
//            }
//        }, 5000);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class Adapter extends RecyclerView.Adapter {
        public int itemCount = 0;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new TextView(TransitionActivity5.this));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText(position + "");
            ((TextView)holder.itemView).setTextSize(30);
        }

        @Override
        public int getItemCount() {
            return itemCount;
        }
    }
}
