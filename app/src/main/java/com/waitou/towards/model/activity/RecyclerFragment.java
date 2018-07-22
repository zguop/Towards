package com.waitou.towards.model.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.to.aboomy.recycler_lib.QyRecyclerAdapter;

/**
 * auth aboom
 * date 2018/7/15
 */
public abstract class RecyclerFragment extends Fragment {

    private QyRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter = new QyRecyclerAdapter();
        recyclerView.setAdapter(mAdapter);


        return recyclerView;
    }

//    public void addItemProvier(BaseItemProvider... baseItemProviders) {
//        mAdapter.addProvider(baseItemProviders);
//    }


}
