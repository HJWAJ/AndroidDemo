package com.myapplication.sticky.sticky2;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.myapplication.util.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StickyRecyclerView悬浮操作的辅助类，不公开给外部使用。目前用于缓存一些计算过的值
 *
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
    // 缓存每个悬浮view的对应的位置
    private Map<View, Integer> mStickyViewPosition = new HashMap<>();
    // 注册每个位置是哪些悬浮view的终点
    private SparseArray<ArrayList<Integer>> mStickyRanges = new SparseArray<>();

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
        mStickyViewPosition.clear();
    }

    void setAdapter(StickyRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    View getStickyViewAt(int position) {
        if (mStickyViews.get(position) != null) {
            return mStickyViews.get(position);
        }
        View view = mAdapter.createStickyView(mStickyRecyclerView, position);
        mStickyViewHeight.put(position, ViewUtils.getViewHeight(view));
        mStickyViews.put(position, view);
        mStickyViewPosition.put(view, position);
        return view;
    }

    boolean hasAddedStickyView(int position) {
        return mStickyViewAdded.get(position);
    }

    void setStickyViewAdded(int position, boolean added) {
        mStickyViewAdded.put(position, added);
    }

    /**
     * 获取某个悬浮view底边距悬浮层顶端高度。如果这个view不在悬浮列表中，返回整个悬浮层高度。
     */
    int getStickyViewBottom(View stickyView) {
        int bottom = 0;
        List<View> stickyViews = mStickyRecyclerView.getStickyViews();
        for (View view : stickyViews) {
            bottom += mStickyViewHeight.get(mStickyViewPosition.get(view));
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                bottom += ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin;
            }
            if (view == stickyView) {
                break;
            }
        }
        return bottom;
    }

    /**
     * 注册position是哪个悬浮位置的终点
     * @param position 右端点
     * @param from 左端点
     */
    void addRange(int position, int from) {
        if (mStickyRanges.get(position) == null) {
            mStickyRanges.put(position, new ArrayList<Integer>());
        }
        mStickyRanges.get(position).add(from);
    }

    ArrayList<Integer> getRange(int position) {
        return mStickyRanges.get(position);
    }
}
