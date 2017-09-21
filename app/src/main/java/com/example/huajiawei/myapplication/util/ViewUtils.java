package com.example.huajiawei.myapplication.util;

import android.view.View;

/**
 * Created by huajiawei on 17/9/13.
 */

public class ViewUtils {

    public static int getViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }
}
