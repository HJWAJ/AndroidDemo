package com.example.huajiawei.myapplication.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by huajiawei on 17/9/13.
 */

public class ViewUtils {

    private static int screenWidthPixels;

    public static int getViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }

    public static int getScreenWidthPixels(Context context) {

        if (context == null) {
            //Log.e("Can't get screen size while the activity is null!");
            return 0;
        }

        if (screenWidthPixels > 0) {
            return screenWidthPixels;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        if (display != null) {
            display.getMetrics(dm);
            screenWidthPixels = dm.widthPixels;
        }
        return screenWidthPixels;
    }
}
