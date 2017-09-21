package com.example.huajiawei.myapplication.sticky.sticky2;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;

import com.example.huajiawei.myapplication.util.ViewUtils;

/**
 * Created by huajiawei on 17/9/21.
 */

class StickyLayoutManager {

    private final StickyRecyclerView mStickyRecyclerView;
    private StickyRecyclerAdapter mAdapter;

    // 缓存需要悬浮的view，避免重复inflate
    private SparseArray<View> mStickyViews = new SparseArray<>();
    // 缓存悬浮view的高度，避免重复measure
    private SparseIntArray mStickyViewHeight = new SparseIntArray();
    // 记录该悬浮是否已经存在，区别于悬浮的view是否已创建
    private SparseBooleanArray mStickyViewAdded = new SparseBooleanArray();

    StickyLayoutManager(StickyRecyclerView stickyRecyclerView) {
        mStickyRecyclerView = stickyRecyclerView;
    }

    void notifyStickyChanged() {

    }

    void resetSticky() {
        mStickyRecyclerView.removeAllStickyViews();
        mStickyViews.clear();
        mStickyViewHeight.clear();
        mStickyViewAdded.clear();
    }

    void setAdapter(StickyRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    View getStickyViewAt(int position) {
        if (mStickyViews.get(position) != null) {
            return mStickyViews.get(position);
        }
        View view = mAdapter.createStickyView(mStickyRecyclerView, position);
        mStickyViews.put(position, view);
        return view;
    }

    boolean hasAddedStickyView(int position) {
        return mStickyViewAdded.get(position);
    }

    void setStickyViewAdded(int position, boolean added) {
        mStickyViewAdded.put(position, added);
    }

    int getStickyHeight() {
        return 0;
    }
}
