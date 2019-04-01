package com.waitou.towards.model.main.fragment.joke;

import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.RequestParams;
import com.waitou.three_library.share.ShareInfo;
import com.waitou.three_library.share.UShare;
import com.waitou.towards.bean.JokeInfo;
import com.waitou.towards.common.ExtraValue;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;

/**
 * Created by waitou on 17/3/5.
 * 笑话presenter
 */

public class TextJokePresenter extends XPresent<TextJokeFragment> implements BaseViewAdapter.Presenter {

    private JokeContentFragment mFragmentJoke;
    private JokeContentFragment mFragmentPic;

    JokeContentFragment getFragment(int type) {
        if (mFragmentJoke == null) {
            mFragmentJoke = new JokeContentFragment();
            mFragmentJoke.setPresenter(this);
            Bundle bundle = new Bundle();
            bundle.putInt(ExtraValue.JOKE_CONTENT_TYPE, 0);
            mFragmentJoke.setArguments(bundle);
        }

        if (mFragmentPic == null) {
            mFragmentPic = new JokeContentFragment();
            mFragmentPic.setPresenter(this);
            Bundle bundle = new Bundle();
            bundle.putInt(ExtraValue.JOKE_CONTENT_TYPE, 1);
            mFragmentPic.setArguments(bundle);
        }
        return type == 0 ? mFragmentJoke : mFragmentPic;
    }

    public void share(JokeInfo item) {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.content = item.content;
        shareInfo.imageUrl = item.url;
        shareInfo.type = ObjectUtils.isNotEmpty(shareInfo.imageUrl) ? ShareInfo.TEXT_AND_IMAGE : ShareInfo.TEXT;
        UShare.share(getV().getActivity(), shareInfo, media -> {
            ToastUtils.showShort("分享成功");
            Log.d("aa", " 分享成功");
        });
    }

    /**
     * 笑话数据加载
     */
    void loadJokeData(int page, int type) {
        RequestParams params = new RequestParams();
        params.put("key", ExtraValue.KEY);
        params.put("page", page);
        switch (type) {
            case 0:
                params.put("type", "");
                break;
            case 1:
                params.put("type", "pic");
                break;
            case 2:

                break;
        }

        getV().pend(DataLoader.getJokeApi().getTextJoke(params)
                .compose(RxTransformerHelper.applySchedulersResult( new SimpleErrorVerify() {
                    @Override
                    public void call(String code, String desc) {
                        super.call(code, desc);
                        getFragment(type).showError(page == 1);
                    }

                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        getFragment(type).showError(page == 1);
                    }
                }))
                .subscribe(jokeInfo -> getFragment(type).success(page, jokeInfo)));
    }
}
