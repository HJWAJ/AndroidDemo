package com.example.huajiawei.myapplication.recyclerview.itemanimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;
import com.example.huajiawei.myapplication.recyclerview.common.LinearLayoutManagerWithSmoothOffset;
import com.example.huajiawei.myapplication.util.ViewUtils;

/**
 * Created by huajiawei on 2018/3/4.
 */

public class ItemAnimationActivity extends Activity {

    RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_animation);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothOffset(this));
        recyclerView.setAdapter(new FoldAdapter());
    }

    private class FoldAdapter extends RecyclerView.Adapter {

        static final int FORMER_SIZE = 10;

        static final int FOLD_SIZE = 5;

        static final int LATTER_SIZE = 10;

        boolean fold = true;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(createTextView());
        }

        private View createTextView() {
            TextView textView = new TextView(ItemAnimationActivity.this);
            textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(30);
            int padding = (int) ViewUtils.dip2px(20);
            textView.setPadding(padding, padding, padding, padding);
            return textView;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (position == FORMER_SIZE) {
                ((TextView) holder.itemView).setText(fold ? "展开" : "折叠");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fold = !fold;
                        ((LinearLayoutManagerWithSmoothOffset) recyclerView.getLayoutManager()).smoothScrollToPosition(holder.getAdapterPosition(), 0);
                        notifyItemChanged(FORMER_SIZE);
                        if (fold) {
                            notifyItemRangeRemoved(FORMER_SIZE + 1, FOLD_SIZE);
                        } else {
                            notifyItemRangeInserted(FORMER_SIZE + 1, FOLD_SIZE);
                        }
                    }
                });
            } else if (position < FORMER_SIZE) {
                ((TextView) holder.itemView).setText("former");
                holder.itemView.setOnClickListener(null);
            } else if ((!fold && position >= FORMER_SIZE + 1 + FOLD_SIZE)
                    || (fold && position >= FORMER_SIZE + 1)) {
                ((TextView) holder.itemView).setText("latter");
                holder.itemView.setOnClickListener(null);
            } else {
                ((TextView) holder.itemView).setText("" + position);
                holder.itemView.setOnClickListener(null);
            }
        }

        @Override
        public int getItemCount() {
            return fold ? FORMER_SIZE + 1 + LATTER_SIZE : FORMER_SIZE + 1 + FOLD_SIZE + LATTER_SIZE;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
