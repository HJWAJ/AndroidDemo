package com.example.huajiawei.myapplication.sticky.sticky2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 管理悬浮的adapter。为了能够接入各种定制的RecyclerView.Adapter，选择以组合而非继承的方式来实现这个adapter。
 *
 * Created by huajiawei on 17/9/21.
 */

public class StickyRecyclerAdapter {

    final RecyclerView.Adapter mRecyclerAdapter;

    private StickyRecyclerView mStickyRecyclerView;

    public StickyRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {
        mRecyclerAdapter = recyclerAdapter;
    }

    public void setView(StickyRecyclerView stickyRecyclerView) {
        mStickyRecyclerView = stickyRecyclerView;
    }

    /**
     * 创建这个位置的sticky view，不需要悬浮时return null。
     */
    public View createStickyView(ViewGroup parent, int position) {
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
}
