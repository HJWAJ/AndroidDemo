package com.example.huajiawei.myapplication.sticky.sticky1;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajiawei on 17/8/1.
 */

public class StickyLayout extends CoordinatorLayout {

    private static final int NO_STICK = -1;
    private static final String TAG = "StickyLayout";

    private SparseArray<View> mStickyViews = new SparseArray<>();

    private RecyclerView mRecyclerView;
    private AppBarLayout mAppBarLayout;
    private StickyLayoutAdapter mAdapter;

    private OnScrollListener mOnScrollListener = new OnScrollListener();

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    /**
     *
     * @param recentFirstItemPosition
     * @param recyclerView
     * @return 这次回调的第一个item position
     */
    private int handleStickyWhenScrollUp(int recentFirstItemPosition, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        if (true) Log.d(TAG, "first item: " + first + " recent first: " + recentFirstItemPosition);
        for (int i = recentFirstItemPosition; i <= first; i++) {
            View itemView = linearLayoutManager.findViewByPosition(i);
            if (mAdapter.stickyType(i) != NO_STICK
                    && (itemView == null || itemView.getTop() < 0)
                    && mStickyViews.get(i) == null) {
                addItemToAppBar(i);
            }
        }
        return first;
    }

    /**
     *
     * @param recentFirstItemPosition
     * @param recyclerView
     * @return 这次回调的第一个item position
     */
    private int handleStickyWhenScrollDown(int recentFirstItemPosition, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        if (true) Log.d(TAG, "first item: " + first + " recent first: " + recentFirstItemPosition);
        if (first == 2) {
            View view = linearLayoutManager.findViewByPosition(2);
            Log.d(TAG, "sticky top: " + view.getTop());
        }
        for (int i = first; i <= recentFirstItemPosition; i++) {
            View itemView = linearLayoutManager.findViewByPosition(i);
            if (mAdapter.stickyType(i) != NO_STICK
                    && (itemView == null || itemView.getTop() >= -itemView.getHeight())
                    && mStickyViews.get(i) != null) {
                removeItemFromAppBar(i);
            }
        }
        return first;
    }

    private void addItemToAppBar(int position) {
        RecyclerView.Adapter recyclerAdapter = mAdapter.mAdapter;
        int viewType = recyclerAdapter.getItemViewType(position);
        RecyclerView.ViewHolder viewHolder = recyclerAdapter.onCreateViewHolder(mAppBarLayout, viewType);
        recyclerAdapter.onBindViewHolder(viewHolder, position);
        View itemView = viewHolder.itemView;
        AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) itemView.getLayoutParams();
        lp.setScrollFlags(mAdapter.stickyType(position));
        mAppBarLayout.addView(itemView);
        mStickyViews.put(position, itemView);
    }

    private void removeItemFromAppBar(int position) {
        View view = mStickyViews.get(position);
        mAppBarLayout.removeView(view);
        mStickyViews.remove(position);
    }

    public void setAdapter(StickyLayoutAdapter adapter) {
        mAdapter = adapter;
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter.mAdapter);
            mOnScrollListener.recentFirstItemPosition = 0;
        }
        adapter.setLayout(this);
        clearSticky();
    }

    private void clearSticky() {
        mAppBarLayout.removeAllViews();
        mStickyViews.clear();
    }

    public static class StickyLayoutAdapter {

        private List<String> mStringList = new ArrayList<>();

        private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {
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
        };

        private StickyLayout mStickyLayout;

        public StickyLayoutAdapter() {
            for (int i = 0; i < 100; i++) {
                mStringList.add("我是第" + i + "个item");
            }
        }

        public void notifyDataSetChanged() {
            mAdapter.notifyDataSetChanged();
            if (mStickyLayout != null) {
                mStickyLayout.clearSticky();
            }
        }

        public int stickyType(int position) {
            if (position == 6) {
                return 0;
            }
            if (position == 20) {
                return 0;
            }
            return NO_STICK;
        }

        public int stickyResId(int position) {
            return 0;
        }

        public void setLayout(StickyLayout stickyLayout) {
            mStickyLayout = stickyLayout;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {
        /*
         * 经debug发现，滑动过程中，并不是每个position对应的item都能成为一次first item
         * 因此需要记录上次回调first item是哪个，然后处理上次到这次之间的item的悬浮
         */
        private int recentFirstItemPosition = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (true) Log.d(TAG, "dy: " + dy);

            try {
                if (dy > 0) {
                    recentFirstItemPosition = handleStickyWhenScrollUp(recentFirstItemPosition, recyclerView);
                } else if (dy < 0) {
                    recentFirstItemPosition = handleStickyWhenScrollDown(recentFirstItemPosition, recyclerView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (true) Log.d(TAG, "AppBarLayoutCount: " + mAppBarLayout.getChildCount());
        }
    }
}
