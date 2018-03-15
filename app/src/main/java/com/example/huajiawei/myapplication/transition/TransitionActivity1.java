package com.example.huajiawei.myapplication.transition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;
import com.example.huajiawei.myapplication.recyclerview.common.LinearLayoutManagerWithSmoothOffset;

/**
 * Created by huajiawei on 2018/3/7.
 */

public class TransitionActivity1 extends Activity {

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition1);
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothOffset(this));
        recyclerView.setAdapter(new Adapter());
    }

    private class Adapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(TransitionActivity1.this).inflate(R.layout.transition_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            final View view = holder.itemView;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.findViewById(R.id.image).setTransitionName("thumb" + position);
            }
            ((TextView) view.findViewById(R.id.text)).setText("" + position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://transition2?id=" + holder.getAdapterPosition())),
                                ActivityOptions.makeSceneTransitionAnimation(
                                        TransitionActivity1.this,
                                        view.findViewById(R.id.image),
                                        "image").toBundle());
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://transition2")));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
