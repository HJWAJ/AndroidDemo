package com.example.huajiawei.myapplication.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huajiawei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajiawei on 16/12/5.
 */

public class ListActivity extends Activity {

    List<String> stringList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listView = (ListView) findViewById(R.id.list);
        for (int i = 0; i < 100; i++) {
            stringList.add(String.valueOf(i));
        }
        listView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return stringList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (position == 0) {
                    View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.item_btn, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            stringList.clear();
                            for (int i = 0; i < 50; i++) {
                                stringList.add(String.valueOf(i));
                            }
                            notifyDataSetChanged();

                        }
                    });
                    return view;
                } else {
                    final TextView view = (TextView) LayoutInflater.from(ListActivity.this).inflate(R.layout.item_btn, parent, false);
                    view.setText(stringList.get(position));
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ListActivity.this, view.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return view;
                }
            }
        });
    }
}
