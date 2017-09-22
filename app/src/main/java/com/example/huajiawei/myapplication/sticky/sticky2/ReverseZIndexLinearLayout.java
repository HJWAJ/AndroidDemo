package com.example.huajiawei.myapplication.sticky.sticky2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by huajiawei on 17/9/22.
 */

public class ReverseZIndexLinearLayout extends LinearLayout {

    public ReverseZIndexLinearLayout(Context context) {
        this(context, null);
    }

    public ReverseZIndexLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReverseZIndexLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return childCount - i - 1;
    }
}
