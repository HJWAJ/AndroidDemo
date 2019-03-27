package com.myapplication.transition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;
import com.myapplication.recyclerview.common.LinearLayoutManagerWithSmoothOffset;

import java.util.List;

/**
 * Created by huajiawei on 2018/3/7.
 */

public class TransitionActivity1 extends Activity {

    RecyclerView recyclerView;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition1);
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
        findViewById(R.id.item0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.stopScroll();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "demo://transition5")),
                        ActivityOptions.makeSceneTransitionAnimation(
                                TransitionActivity1.this,
                                Pair.create(v.findViewById(R.id.image), "image"),
                                Pair.create(v.findViewById(R.id.text), "text")
                        ).toBundle());
            }
        });
        ActivityCompat.setExitSharedElementCallback(this, new SharedElementCallback() {

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames,
                                           List<View> sharedElements,
                                           List<View> sharedElementSnapshots) {

                super.onSharedElementEnd(sharedElementNames, sharedElements,
                        sharedElementSnapshots);

                for (View view : sharedElements) {
                    view.setVisibility(View.VISIBLE);
                    view.setAlpha(1);
                }
            }
        });
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
                        if (holder.getAdapterPosition() == 0) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "demo://transition5")),
                                    ActivityOptions.makeSceneTransitionAnimation(
                                            TransitionActivity1.this,
                                            Pair.create(view.findViewById(R.id.image), "image"),
                                            Pair.create(view.findViewById(R.id.text), "text")
                                    ).toBundle());
                        } else if ((holder.getAdapterPosition() & 2) == 2) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    ((holder.getAdapterPosition() & 1) == 1) ? "demo://transition2" : "demo://transition3")),
                                    ActivityOptions.makeSceneTransitionAnimation(
                                            TransitionActivity1.this,
                                            Pair.create(view.findViewById(R.id.image), "image"),
                                            Pair.create(view.findViewById(R.id.text), "text")
                                    ).toBundle());
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    ((holder.getAdapterPosition() & 1) == 1) ? "demo://transition2" : "demo://transition3")),
                                    ActivityOptions.makeSceneTransitionAnimation(
                                            TransitionActivity1.this,
                                            Pair.create(view.findViewById(R.id.image), "image"),
                                            Pair.create(view.findViewById(R.id.text), "text")
                                    ).toBundle());
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "demo://transition4")),
                                    ActivityOptions.makeSceneTransitionAnimation(
                                            TransitionActivity1.this,
                                            Pair.create(view.findViewById(R.id.image), "image"),
                                            Pair.create(view.findViewById(R.id.text), "text")
                                    ).toBundle());
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "demo://transition4")),
                                    ActivityOptions.makeSceneTransitionAnimation(
                                            TransitionActivity1.this,
                                            Pair.create(view.findViewById(R.id.image), "image"),
                                            Pair.create(view.findViewById(R.id.text), "text")
                                    ).toBundle());
                        }
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("demo://transition2")));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 1000;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
