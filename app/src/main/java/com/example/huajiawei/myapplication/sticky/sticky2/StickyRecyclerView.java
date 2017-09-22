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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.huajiawei.myapplication.R;
import com.example.huajiawei.myapplication.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajiawei on 17/9/8.
 */

public class StickyRecyclerView extends FrameLayout {

    private static final String TAG = "StickyRecyclerView";

    private RecyclerView mRecyclerView;
    private LinearLayout mStickyContainerView;

    private StickyRecyclerAdapter mAdapter;
    private OnScrollListener mOnScrollListener = new OnScrollListener();
    private StickyLayoutManager mStickyLayoutManager = new StickyLayoutManager(this);

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

    public void setAdapter(StickyRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter.mRecyclerAdapter);
        mStickyLayoutManager.setAdapter(adapter);
        adapter.setView(this);
        mAdapter = adapter;
    }

    void notifyStickyChanged() {
        mStickyLayoutManager.notifyStickyChanged();
    }

    void removeAllStickyViews() {
        mStickyContainerView.removeAllViews();
    }

    List<View> getStickyViews() {
        List<View> stickyViews = new ArrayList<>();
        for (int i = 0; i < mStickyContainerView.getChildCount(); i++) {
            stickyViews.add(mStickyContainerView.getChildAt(i));
        }
        return stickyViews;
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {
        /*
         * 经debug发现，滑动过程中，并不是每个position对应的item都能成为一次first/last item
         * 因此需要记录上次回调的first/last item是哪个，然后处理上次到这次之间的item的悬浮
         */
        private int recentFirstItemPosition = 0;
        private int recentLastItemPosition = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            try {
                if (dy > 0) {
                    recentFirstItemPosition = onScrollUp(recyclerView);
                } else if (dy < 0) {
                    recentLastItemPosition = onScrollDown(recyclerView);
                }
            } catch (Exception e) {

            }
        }

        private int onScrollUp(RecyclerView recyclerView) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            // 上次回调到这次回调之间，需要处理的position段
            for (int i = recentFirstItemPosition; i <= lastVisibleItemPosition; i++) {
                View stickyView = mStickyLayoutManager.getStickyViewAt(i);
                // 比较当前位置view的top是否小于已经悬浮的view总高度，若小于则出悬浮
                // 当然，如果当前位置view已经往上滑出列表了，那对应的悬浮也该出
                if (stickyView != null && (i < firstVisibleItemPosition
                        || linearLayoutManager.findViewByPosition(i).getTop() < getStickyContainterBottom())) {
                    // 已经出悬浮的就不再增加
                    if (!mStickyLayoutManager.hasAddedStickyView(i)) {
                        mStickyContainerView.addView(stickyView);
                        if (mAdapter.getStickyRange(i) != Integer.MAX_VALUE) {
                            mStickyLayoutManager.addRange(mAdapter.getStickyRange(i), i);
                        }
                        mStickyLayoutManager.setStickyViewAdded(i, true);
                    }
                }
                // 处理悬浮范围，如果i是某个悬浮view的最后区间则需要处理
                if (mStickyLayoutManager.getRange(i) != null && mStickyLayoutManager.getRange(i).size() > 0) {
                    // 遍历以当前位置为止的悬浮view
                    for (Integer start : mStickyLayoutManager.getRange(i)) {
                        View potentialMoveOutStickyView = mStickyLayoutManager.getStickyViewAt(start);
                        if (i < firstVisibleItemPosition) {
                            // 悬浮范围的最后一个view已经滑出，则悬浮的view往上推到完全消失
                            moveUpStickyView(potentialMoveOutStickyView, Integer.MAX_VALUE);
                        } else {
                            // 悬浮范围的最后一个view在列表中，若view的bottom小于悬浮view的bottom
                            // 则悬浮view的bottom要对齐该view的bottom
                            int bottomOfLastShouldStickView = linearLayoutManager.findViewByPosition(i).getBottom();
                            int bottomOfStickyView = mStickyLayoutManager.getStickyViewBottom(potentialMoveOutStickyView);
                            if (bottomOfLastShouldStickView < bottomOfStickyView) {
                                moveUpStickyView(potentialMoveOutStickyView, bottomOfStickyView - bottomOfLastShouldStickView);
                            }
                        }
                    }
                }
            }
            return firstVisibleItemPosition;
        }

        private int onScrollDown(RecyclerView recyclerView) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            // 上次回调到这次回调之间，需要处理的position段
            for (int i = firstVisibleItemPosition; i <= recentLastItemPosition; i++) {
                View stickyView = mStickyLayoutManager.getStickyViewAt(i);
                // 比较当前位置view的top是否不小于已经悬浮的view总高度，若不小于则去掉悬浮
                // 当然，如果当前位置view已经往下滑出列表了，那对应的悬浮也该去掉
                if (stickyView != null && (i > lastVisibleItemPosition
                        || linearLayoutManager.findViewByPosition(i).getTop() >= getStickyContainterBottom())) {
                    // 已经去除悬浮的就不处理
                    if (mStickyLayoutManager.hasAddedStickyView(i)) {
                        mStickyContainerView.removeView(stickyView);
                        mStickyLayoutManager.setStickyViewAdded(i, false);
                    }
                }
                // 处理悬浮范围，如果i是某个悬浮view的最后区间则需要处理
                if (mStickyLayoutManager.getRange(i) != null && mStickyLayoutManager.getRange(i).size() > 0) {
                    // 遍历以当前位置为止的悬浮view
                    for (Integer start : mStickyLayoutManager.getRange(i)) {
                        View potentialMoveInStickyView = mStickyLayoutManager.getStickyViewAt(start);
                        // 悬浮范围的最后一个view已经滑出，则悬浮的view完全展示出来
                        if (i > lastVisibleItemPosition) {
                            moveDownStickyView(potentialMoveInStickyView, Integer.MAX_VALUE);
                        } else {
                            // 悬浮范围的最后一个view在列表中，若view的bottom小于悬浮view的bottom
                            // 则悬浮view的bottom要对齐该view的bottom
                            int bottomOfLastShouldStickView = linearLayoutManager.findViewByPosition(i).getBottom();
                            int bottomOfStickyView = mStickyLayoutManager.getStickyViewBottom(potentialMoveInStickyView);
                            if (bottomOfLastShouldStickView > bottomOfStickyView) {
                                moveDownStickyView(potentialMoveInStickyView, bottomOfLastShouldStickView - bottomOfStickyView);
                            }
                        }
                    }
                }
            }
            return lastVisibleItemPosition;
        }
    }

    /**
     * 将悬浮view上移，最大只上移view的高度以免影响它下面的view
     */
    private void moveUpStickyView(View stickyView, int distance) {
        if (stickyView.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams lp = ((MarginLayoutParams) stickyView.getLayoutParams());
            int marginToMove = Math.min(ViewUtils.getViewHeight(stickyView) - (-lp.topMargin), distance);
            if (marginToMove != 0) {
                lp.topMargin -= marginToMove;
                stickyView.setLayoutParams(lp);
            }
        }
    }

    /**
     * 将悬浮view下移，最大下移到view完全露出
     */
    private void moveDownStickyView(View stickyView, int distance) {
        if (stickyView.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams lp = ((MarginLayoutParams) stickyView.getLayoutParams());
            int marginToMove = Math.min(-lp.topMargin, distance);
            if (marginToMove != 0) {
                lp.topMargin += marginToMove;
                stickyView.setLayoutParams(lp);
            }
        }
    }

    private int getStickyContainterBottom() {
        if (!(mStickyContainerView.getLayoutParams() instanceof LayoutParams)) {
            return 0;
        }
        return ((LayoutParams) mStickyContainerView.getLayoutParams()).topMargin + ViewUtils.getViewHeight(mStickyContainerView);
    }
}
