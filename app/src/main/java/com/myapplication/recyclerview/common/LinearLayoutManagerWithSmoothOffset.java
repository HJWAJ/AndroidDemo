package com.myapplication.recyclerview.common;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huajiawei on 2018/3/5.
 */

public class LinearLayoutManagerWithSmoothOffset extends LinearLayoutManager {

    protected Context context;
    private boolean isScrollEnabled = true;

    public LinearLayoutManagerWithSmoothOffset(Context context) {
        super(context, VERTICAL, false);
        this.context = context;
    }

    public LinearLayoutManagerWithSmoothOffset(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public Context getContext() {
        return context;
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }

    public void smoothScrollToPosition(int position, int offset) {
        LinearSmoothScrollerWithOffset linearSmoothScroller = new LinearSmoothScrollerWithOffset(getContext(), this);
        linearSmoothScroller.setTargetPosition(position);
        linearSmoothScroller.setOffset(offset);
        startSmoothScroll(linearSmoothScroller);
    }

    public static class LinearSmoothScrollerWithOffset extends LinearSmoothScroller {

        protected int offset;
        protected LinearLayoutManager llm;

        public LinearSmoothScrollerWithOffset(Context context, @NonNull LinearLayoutManager llm) {
            super(context);
            this.llm = llm;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        @Override
        protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
            int xOffset = 0;
            int yOffset = 0;
            if (llm.getOrientation() == LinearLayoutManager.VERTICAL) {
                yOffset = offset;
            } else if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                xOffset = offset;
            }
            final int dx = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference()) + xOffset;
            final int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference()) + yOffset;
            final int distance = (int) Math.sqrt(dx * dx + dy * dy);
            final int time = calculateTimeForDeceleration(distance);
            if (time > 0) {
                action.update(-dx, -dy, time, mDecelerateInterpolator);
            }
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return llm.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }
    }
}
