package com.waitou.towards.model.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityRecommendedBinding;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.recycler.LayoutManagerUtil;
import com.waitou.wt_library.recycler.PullRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by waitou on 17/1/5.
 */

public class RecommendedActivity extends XActivity<RecommendedPresenter, ActivityRecommendedBinding> implements RecommendedContract.RecommendedView {

    List<String> list = new ArrayList<>();
    private Adapter adapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        getBinding().list.setLayoutManager(LayoutManagerUtil.getVerticalLayoutManager(this));
        adapter = new Adapter();
        getBinding().list.setAdapter(adapter);

        getBinding().list.setOnRefreshAndLoadMoreListener(new PullRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Thread(() -> {
                    SystemClock.sleep(4000);
                    runOnUiThread(() -> {
                        for (int i = 0; i < 40; i++) {
                            list.add("我是item = " + i);
                        }
                        adapter.addDataC(list);
                    });
                }).start();
            }

            @Override
            public void onLoadMore(int page) {
                Log.d("aa" , "  page = " + page);
                new Thread(() -> {
                    SystemClock.sleep(4000);
                    add(10);
                }).start();

            }
        });
        showLoading();
        reloadData();


    }

    @Override
    public void reloadData() {
        add(40);
    }

    private void add(int b){
        list.clear();
        runOnUiThread(() -> {
            for (int i = 0; i < b; i++) {
                list.add("我是item = " + i);
            }
            adapter.addData(list);
        });

    }

    @Override
    public RecommendedPresenter createPresenter() {
        return new RecommendedPresenter();
    }

    @Override
    public boolean defaultXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recommended;
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void showContent() {
    }

    @Override
    public void setText(String text) {
    }


    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<String> list = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new TextView(RecommendedActivity.this));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(list.get(position) + "  ----  position " + position);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View itemView) {
                super(itemView);
                TextView textView = (TextView) itemView;
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
            }
        }


        public void addData(List<String> list) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public void addDataC(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

}
