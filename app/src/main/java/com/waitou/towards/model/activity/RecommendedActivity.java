package com.waitou.towards.model.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.to.aboomy.recycler_lib.Displayable;
import com.to.aboomy.recycler_lib.QyRecyclerAdapter;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityRecommendedBinding;
import com.waitou.wt_library.base.XActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by waitou on 17/1/5.
 */

public class RecommendedActivity extends XActivity<RecommendedPresenter, ActivityRecommendedBinding> implements RecommendedContract.RecommendedView {

    public static final String EXTRA_LIST = "extra_list";

    List<Displayable> mMultiItemEntities = new ArrayList<>();
    private QyRecyclerAdapter mBAdapter;
    private RecyclerIntentVO  mRecyclerIntentVO;

    @Override
    public void reloadData() {
    }


    @Override
    public RecommendedPresenter createPresenter() {
        return new RecommendedPresenter();
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_recommended;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mRecyclerIntentVO = (RecyclerIntentVO) getIntent().getSerializableExtra(EXTRA_LIST);
        mBAdapter = new QyRecyclerAdapter();
        mBAdapter.addProvider(new TextItemProvider(), new SubmitProvider());
        mBAdapter.setQyPresenter(new RecommendHelper());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().list.setLayoutManager(linearLayoutManager);
        getBinding().list.setAdapter(mBAdapter);

        TextBean textBean = new TextBean();
        textBean.title = "手机号";
        mMultiItemEntities.add(textBean);

        TextBean textBean2 = new TextBean();
        textBean2.title = "密码";
        mMultiItemEntities.add(textBean2);

        ItemButton itemButton = new ItemButton();
        itemButton.buttonDes = "登陆";
        mMultiItemEntities.add(itemButton);
        mBAdapter.addData(mMultiItemEntities);

        getBinding().list.post(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolderForAdapterPosition = getBinding().list.findViewHolderForAdapterPosition(0);
                int itemViewType = viewHolderForAdapterPosition.getItemViewType();
                long itemId = viewHolderForAdapterPosition.getItemId();


                Log.e("aa" ," itemviewtype = " + itemViewType);
                Log.e("aa" ," itemId = " + itemId);


            }
        });
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


    public RecyclerView.LayoutManager getLayoutManager() {
        switch (mRecyclerIntentVO.recyclerLayoutManager) {
            case 0:

            case 1:

            case 2:

            case 3:

            default:
                LinearLayoutManager manager = new LinearLayoutManager(this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                return manager;
        }
    }
}
