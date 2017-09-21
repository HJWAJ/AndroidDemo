package com.example.huajiawei.myapplication.sticky.sticky2;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 17/9/8.
 */

public class StickyRecyclerView extends FrameLayout {

    private static final String TAG = "StickyRecyclerView";

    private RecyclerView mRecyclerView;
    private OnScrollListener mOnScrollListener = new OnScrollListener();
    private LinearLayout mStickyContainerView;

    private Adapter mAdapter;

    private SparseArray<View> mStickyViews = new SparseArray<>();
    private SparseIntArray mStickyViewHeight = new SparseIntArray();
    private SparseBooleanArray mStickyViewAdded = new SparseBooleanArray();

    public StickyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public StickyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mStickyContainerView = (LinearLayout) findViewById(R.id.sticky_container);
    }

    /**
     * 设置初始悬浮位置，针对沉浸式banner的场景，只能在一开始设置，在之后设置可能会导致布局错乱。
     * 单位px。
     */
    public void setStickyMargin(int margin) {
        FrameLayout.LayoutParams lp = (LayoutParams) mStickyContainerView.getLayoutParams();
        if (lp.topMargin != margin) {
            lp.topMargin = margin;
            mStickyContainerView.setLayoutParams(lp);
        }
    }

    public static abstract class Adapter {

        private final RecyclerView.Adapter mRecyclerAdapter;

        private StickyRecyclerView mStickyRecyclerView;

        public Adapter(RecyclerView.Adapter recyclerAdapter) {
            mRecyclerAdapter = recyclerAdapter;
        }

        /**
         * 创建这个位置的sticky view，不需要悬浮时return null。
         */
        public View createStickyView(int position) {
            return null;
        }

        /**
         * @return 到该位置为止是需要悬浮的。
         */
        public int getStickyRange(int position) {
            return Integer.MAX_VALUE;
        }

        /**
         * 翻页的时候不需要
         */
        public void notifyDataSetChanged(boolean needResetStickyViews) {
            if (mStickyRecyclerView == null) {
                return;
            }
            mRecyclerAdapter.notifyDataSetChanged();
            if (needResetStickyViews) {
                mStickyRecyclerView.notifyStickyChanged();
            }
        }

        private void setView(StickyRecyclerView stickyRecyclerView) {
            mStickyRecyclerView = stickyRecyclerView;
        }
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(adapter.mRecyclerAdapter);
        mAdapter.setView(this);
    }

    private void notifyStickyChanged() {
        mOnScrollListener.reset();
        mStickyContainerView.removeAllViews();
        mStickyViews.clear();
        mStickyViewHeight.clear();
        mStickyViewAdded.clear();
        mOnScrollListener.onScrollUp(mRecyclerView);
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
                    recentFirstItemPosition = onScrollUp(recyclerView);
                } else if (dy < 0) {
                    recentFirstItemPosition = onScrollDown(recyclerView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private int onScrollUp(RecyclerView recyclerView) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (true) Log.d(TAG, "first item: " + firstVisibleItemPosition + " recent first: " + recentFirstItemPosition);
            for (int i = recentFirstItemPosition; i <= lastVisibleItemPosition; i++) {
                View stickyView = mStickyViews.get(i);
                if (stickyView == null) {
                    stickyView = mAdapter.createStickyView(i);
                    mStickyViews.put(i, stickyView);
                }
                // 需要处理悬浮
                if (stickyView != null) {
                    // 如果当前item的top已经被遮住了，而且未被加过，就把悬浮加上
                    if (!mStickyViewAdded.get(i) && linearLayoutManager.findViewByPosition(i).getTop() < mStickyContainerView.getBottom()) {
                        mStickyContainerView.addView(stickyView);
                        mStickyViewAdded.put(i, true);
                    }
                }
            }

            return firstVisibleItemPosition;
        }

        private int onScrollDown(RecyclerView recyclerView) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (true) Log.d(TAG, "first item: " + firstVisibleItemPosition + " recent first: " + recentFirstItemPosition);
            for (int i = firstVisibleItemPosition; i <= recentFirstItemPosition; i++) {
                View stickyView = mStickyViews.get(i);
                if (stickyView == null) {
                    stickyView = mAdapter.createStickyView(i);
                    mStickyViews.put(i, stickyView);
                }
                // 需要处理悬浮
                if (stickyView != null) {
                    // 如果当前item的top没被遮住了，而且被加过，就把悬浮去掉
                    if (mStickyViewAdded.get(i) && linearLayoutManager.findViewByPosition(i).getTop() > mStickyContainerView.getBottom()) {
                        mStickyContainerView.removeView(stickyView);
                        mStickyViewAdded.put(i, false);
                    }
                }
            }

            return firstVisibleItemPosition;
        }

        public void reset() {
            recentFirstItemPosition = 0;
        }
    }
}
