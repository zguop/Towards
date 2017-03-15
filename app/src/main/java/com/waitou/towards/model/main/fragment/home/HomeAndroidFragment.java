package com.waitou.towards.model.main.fragment.home;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.databinding.IncludePullRecyclerBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XPresent;

/**
 * Created by waitou on 17/3/8.
 * 大android 数据
 */

public class HomeAndroidFragment extends XFragment<XPresent, IncludePullRecyclerBinding> {


    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_pull_recycler;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void fragmentVisibleHint() {
        reloadData();
    }

    @Override
    public void reloadData() {
//        String[] split = Kits.Date.getCurrentDate().split("-");
//        Observable<Reply<GankIoDayInfo<GankResultsInfo>>> compose = Repository.getRepository().getGankIoDay(split[0], split[1], split[2])
//                .compose(RxTransformerHelper.applySchedulers());
//
//        compose.subscribe(new Action1<Reply<GankIoDayInfo<GankResultsInfo>>>() {
//            @Override
//            public void call(Reply<GankIoDayInfo<GankResultsInfo>> gankIoDayInfoReply) {
//                showContent();
//                Log.d("aa", " data = " + gankIoDayInfoReply.toString());
//            }
//        });
    }
}
