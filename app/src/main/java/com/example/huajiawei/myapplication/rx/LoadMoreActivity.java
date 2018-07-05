package com.example.huajiawei.myapplication.rx;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huajiawei.myapplication.R;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class LoadMoreActivity extends Activity {

    RecyclerView rv;
    Adapter adapter;
    Subscription mLoadDataSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler);
        initView();
        mLoadDataSubscription = prepareLoadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadDataSubscription != null && !mLoadDataSubscription.isUnsubscribed()) {
            mLoadDataSubscription.unsubscribe();
        }
    }

    private Subscription prepareLoadData() {
        return RxRecyclerView.scrollEvents(rv)
                .filter(new Func1<RecyclerViewScrollEvent, Boolean>() {
                    @Override
                    public Boolean call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                        if (((Adapter)recyclerViewScrollEvent.view().getAdapter()).isEnd()) return false;
                        return ((LinearLayoutManager)recyclerViewScrollEvent.view().getLayoutManager()).findLastVisibleItemPosition()
                                == recyclerViewScrollEvent.view().getAdapter().getItemCount() - 1;
                    }
                })
                .map(new Func1<RecyclerViewScrollEvent, Integer>() {
                    @Override
                    public Integer call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                        return recyclerViewScrollEvent.view().getAdapter().getItemCount();
                    }
                })
                .distinctUntilChanged() // item count变化才走下面
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(final Subscriber<? super Integer> subscriber) {
                                Log.d("rx", "load data"); // distinctUntilChanged的效果
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Adapter)rv.getAdapter()).append();
                                        subscriber.onNext(1);
                                    }
                                }, 2000);
                            }
                        });
                    }
                })
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        rv.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        rv.setAdapter(adapter);
    }

    private class Adapter extends RecyclerView.Adapter {
        int size = 0;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(LoadMoreActivity.this);
            tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 360));
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff111111);
            tv.setBackgroundColor(0xffffffff);
            return new ViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            if (!isEnd() && position == getItemCount() - 1) {
                tv.setText("loading more...");
            } else {
                tv.setText(position + "");
            }
        }

        @Override
        public int getItemCount() {
            return isEnd() ? size : size + 1;
        }

        public boolean isEnd() {
            return size >= 50;
        }

        public void append() {
            size += 2;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
