package com.example.huajiawei.myapplication.recyclerview.itemanimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothOffset(this));
        recyclerView.setAdapter(new RemoveAndAddAdapter());
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

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if ((fold && position == FORMER_SIZE)
                    || (!fold && position == FORMER_SIZE + FOLD_SIZE)) {
                ((TextView) holder.itemView).setText(fold ? "展开" : "折叠");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fold = !fold;
                        if (fold) {
                            notifyItemRangeRemoved(FORMER_SIZE, FOLD_SIZE);
                        } else {
                            notifyItemRangeInserted(FORMER_SIZE, FOLD_SIZE);
                        }
                        notifyItemChanged(fold ? FORMER_SIZE : FORMER_SIZE + FOLD_SIZE);
                        if (!fold) {
                            ((LinearLayoutManagerWithSmoothOffset) recyclerView.getLayoutManager()).smoothScrollToPosition(
                                    holder.getAdapterPosition() - FOLD_SIZE, 0);
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
                ((TextView) holder.itemView).setText("" + (position - FORMER_SIZE + 1));
                holder.itemView.setOnClickListener(null);
            }
        }

        @Override
        public int getItemCount() {
            return fold ? FORMER_SIZE + 1 + LATTER_SIZE : FORMER_SIZE + FOLD_SIZE + 1 + LATTER_SIZE;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
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

    private class TestExceptionAdapter extends RecyclerView.Adapter {

        int count = 20;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(createTextView());
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText("" + position);
            if (position == 0) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = 10;
                        notifyItemRangeRemoved(10, 5);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private class RemoveAndAddAdapter extends RecyclerView.Adapter {

        int count = 20;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(createTextView());
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText("" + position);
            if (position == 0) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = 1;
                        notifyItemRangeRemoved(1, 19);
                        count = 20;
                        notifyItemRangeInserted(1, 19);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private class UpdateAdapter extends RecyclerView.Adapter {

        int count = 20;
        boolean change;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(createTextView());
        }

        int p(int position) {
            return position + (!change ? 0 : 100);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText("" + p(position));
            if (position == 0) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change = true;
                        notifyItemRangeChanged(0, 5);
                        count = 5;
                        notifyItemRangeRemoved(5, 15);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}
