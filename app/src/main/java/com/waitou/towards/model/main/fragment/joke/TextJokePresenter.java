package com.waitou.towards.model.main.fragment.joke;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.waitou.basic_lib.DialogUtils;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.helper.SimpleErrorVerify;
import com.waitou.net_library.model.RequestParams;
import com.waitou.three_library.share.ShareInfo;
import com.waitou.three_library.share.UShare;
import com.waitou.towards.bean.JokeInfo;
import com.waitou.towards.common.Values;
import com.waitou.towards.net.DataLoader;
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
            bundle.putInt(Values.JOKE_CONTENT_TYPE, 0);
            mFragmentJoke.setArguments(bundle);
        }

        if (mFragmentPic == null) {
            mFragmentPic = new JokeContentFragment();
            mFragmentPic.setPresenter(this);
            Bundle bundle = new Bundle();
            bundle.putInt(Values.JOKE_CONTENT_TYPE, 1);
            mFragmentPic.setArguments(bundle);
        }
        return type == 0 ? mFragmentJoke : mFragmentPic;
    }

    public void share(JokeInfo item) {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.content = item.content;
        shareInfo.imageUrl = item.url;
        shareInfo.type = ObjectUtils.isNotEmpty(shareInfo.imageUrl) ? ShareInfo.TEXT_AND_IMAGE : ShareInfo.TEXT;
        FragmentActivity activity = getV().getActivity();
        if(activity == null){
            return;
        }
        DialogUtils.showShareDialog(activity, shareInfo, new UShare.OnShareListener() {
            @Override
            public void onShare(SHARE_MEDIA share_media) {
                ToastUtils.showShort("分享成功");

            }
        });
    }

    /**
     * 笑话数据加载
     */
    void loadJokeData(int page, int type) {
        RequestParams params = new RequestParams();
        params.put("key", Values.KEY);
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
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(new SimpleErrorVerify() {
                    @Override
                    public void call(Throwable throwable) {
                        getFragment(type).showError(page == 1);
                    }
                }))
                .filter(baseResponse -> {
                    boolean success = Integer.valueOf(baseResponse.errorCode) == 0;
                    if (!success) {
                        LogUtils.e("返回错误码：" + baseResponse.errorCode + "\t\t\t错误信息：" + baseResponse.reason);
                        ToastUtils.showShort(baseResponse.reason);
                        getFragment(type).showError(page == 1);
                    }
                    return success;
                }).map(listBaseResponse -> listBaseResponse.result)
                .subscribe(jokeInfo -> getFragment(type).success(page, jokeInfo)));
    }
}
