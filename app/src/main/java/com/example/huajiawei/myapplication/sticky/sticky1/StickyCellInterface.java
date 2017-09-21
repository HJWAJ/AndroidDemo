package com.example.huajiawei.myapplication.sticky.sticky1;

import android.support.annotation.IdRes;

/**
 * Created by huajiawei on 17/8/1.
 */

public interface StickyCellInterface {

    int stickyType(int position);

    @IdRes
    int stickyResId(int position);
}
