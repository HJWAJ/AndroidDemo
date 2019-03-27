package com.myapplication.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huajiawei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajiawei on 17/4/19.
 */

public class RecyclerViewActivity extends Activity {
    List<String> stringList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        for (int i = 0; i < 100; i++) {
            stringList.add(String.valueOf(i));
        }

        final RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_btn, parent, false));
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final TextView view = (TextView) holder.itemView;
                view.setText(stringList.get(position));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RecyclerViewActivity.this, view.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return stringList.size();
            }
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringList.clear();
                for (int i = 0; i < 50; i++) {
                    stringList.add(String.valueOf(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
