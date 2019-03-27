package com.myapplication.imageview;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.huajiawei.myapplication.R;
import com.myapplication.util.ViewUtils;

/**
 * Created by huajiawei on 2017/10/23.
 */

public class ImageMatrixActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix);
        ImageView imageView = (ImageView) findViewById(R.id.img);

        imageView.setImageMatrix(generateMatrix());
    }

    Matrix generateMatrix() {
        final float heightWidthRatio = 28.0f / 75.0f;

        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.moon);

        int viewWidth = ViewUtils.getScreenWidthPixels(this);
        int viewHeight = (int) (viewWidth * heightWidthRatio);
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        float scaleRatio = ((float) viewWidth) / bmpWidth;
        float translateDistanceY = viewHeight - bmpHeight * scaleRatio;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleRatio, scaleRatio);
        matrix.postTranslate(0, translateDistanceY);
        return matrix;
    }
}
