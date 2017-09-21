package com.example.huajiawei.myapplication.decorview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.huajiawei.myapplication.R;

/**
 * Created by huajiawei on 17/9/12.
 */

public class TestBadTokenExceptionActivity2 extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bad_token);
        findViewById(R.id.btn_test_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTask().execute();
            }
        });

        findViewById(R.id.btn_test_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToastTask().execute();
            }
        });
    }

    private class DialogTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //if (!TestBadTokenExceptionActivity2.this.isFinishing()) {
            //try {
            createAlertDialog().show();
            //} catch (Exception e) {
            //}
            //}
        }

    }

    private AlertDialog createAlertDialog() {
        return new AlertDialog.Builder(TestBadTokenExceptionActivity2.this).setTitle("测试Dialog")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();
    }

    private class ToastTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //if (!TestBadTokenExceptionActivity2.this.isFinishing()) {
            //try {
            Toast.makeText(TestBadTokenExceptionActivity2.this, "测试Toast", Toast.LENGTH_SHORT).show();
            //} catch (Exception e) {
            //}
            //}
        }

    }
}
