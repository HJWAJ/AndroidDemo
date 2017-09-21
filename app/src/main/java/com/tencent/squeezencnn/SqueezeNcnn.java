package com.tencent.squeezencnn;

import android.graphics.Bitmap;
import android.content.Context;
import android.util.Log;

/**
 * Created by huajiawei on 17/9/15.
 */

public class SqueezeNcnn {

    public native boolean Init(byte[] param, byte[] bin, byte[] words);

    public native String Detect(Bitmap bitmap);

    static {
        System.loadLibrary("squeezencnn");
    }
}
