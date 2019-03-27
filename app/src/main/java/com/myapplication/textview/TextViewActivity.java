package com.myapplication.textview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 17/5/12.
 */

public class TextViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        TextView lineSpacingTextView = (TextView) findViewById(R.id.linespacing);
        // fuck: 6.0以上时，行间距不应用于最后一行；6.0以下时，行间距应用于最后一行
        lineSpacingTextView.setLineSpacing(100, 0);
        lineSpacingTextView.setText("测试行间距测试行间距测试行间距测试行间距测试行间距测试行间距测试行间距测试行间距测试行间距测试行间距");

        TextView includePaddingTextView = (TextView) findViewById(R.id.includePadding);
        includePaddingTextView.setIncludeFontPadding(false);
        includePaddingTextView.setText("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试FontPadding bp () （）");
    }
}
