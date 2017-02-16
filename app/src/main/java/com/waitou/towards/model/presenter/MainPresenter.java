package com.waitou.towards.model.presenter;

import android.support.v4.app.Fragment;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.ExtraValue;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.model.main.MainActivity;
import com.waitou.towards.model.main.contract.MainContract;
import com.waitou.towards.model.main.fragment.CircleFragment;
import com.waitou.towards.model.main.fragment.home.HomeCommendFragment;
import com.waitou.towards.model.main.fragment.joke.JokeContentFragment;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.Kits;
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by waitou on 17/1/27.
 */

public class MainPresenter extends XPresent<MainActivity> implements MainContract.MainPresenter, SingleViewPagerAdapter.Presenter {

    private List<Fragment> homeFragments = new ArrayList<>();
    private List<Fragment> jokeFragments = new ArrayList<>();

    private MainContract.TextJokeView mTextJokeView;
    private MainContract.HomeView     mHomeView;

    public MainPresenter(MainContract.HomeView homeView, MainContract.TextJokeView textJokeView) {
        this.mTextJokeView = textJokeView;
        this.mHomeView = homeView;
        mTextJokeView.setPresenter(this);
        mHomeView.setPresenter(this);
    }

    @Override
    public List<Fragment> getHomeFragmentList() {
        if (homeFragments.size() > 0) {
            return homeFragments;
        }
        homeFragments.add(HomeCommendFragment.getInstance(this));
        homeFragments.add(new CircleFragment());
        homeFragments.add(new CircleFragment());
        return homeFragments;
    }

    @Override
    public List<Fragment> getJokeFragmentList() {
        if (jokeFragments.size() > 0) {
            return jokeFragments;
        }
        jokeFragments.add(JokeContentFragment.getInstance(0, this));
        jokeFragments.add(JokeContentFragment.getInstance(1, this));
        return jokeFragments;
    }

    /**
     * 笑话数据加载
     */
    @Override
    public void loadJokeData(int page, int type) {
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
                .compose(RxTransformerHelper.applySchedulersResult(getV(), new SimpleErrorVerify() {
                    @Override
                    public void call(String code, String desc) {
                        super.call(code, desc);
                        mTextJokeView.getCurrentJokeFragment().showError(page == 1);
                    }

                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        mTextJokeView.getCurrentJokeFragment().showError(page == 1);
                    }
                }))
                .subscribe(jokeInfo -> {
                    mTextJokeView.getCurrentJokeFragment().success(page, jokeInfo);
                }));
    }

    @Override
    public void start() {
    }

    /**
     * 广告位的点击方法
     */
    public void onBannerItemClick(BannerPageInfo.BannerInfo.BannerDataInfo url, int position) {

    }

    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg"
    };

    /**
     * 加载HomeCommendFragment首页数据
     */
    public void loadHomeData() {
        getV().pend(DataLoader.getBannerApi().getBannerPage()
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(getV(), new SimpleErrorVerify() {
                    @Override
                    public void call(String code, String desc) {
                        super.call(code, desc);
                        List<BannerPageInfo.BannerInfo.BannerDataInfo> bannerDataInfoList = new ArrayList<>();
                        for (String image : images) {
                            BannerPageInfo.BannerInfo.BannerDataInfo dataInfo = new BannerPageInfo.BannerInfo.BannerDataInfo();
                            dataInfo.picUrl = image;
                            bannerDataInfoList.add(dataInfo);
                        }
                        ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).bannerSuccess(bannerDataInfoList);
                    }
                }))
                .subscribe(bannerPageInfo -> {
                    if (bannerPageInfo.data == null || bannerPageInfo.data.size() == 0) {
                        return;
                    }
                    int dayOfWeek = Kits.Date.getDayOfWeek(new Date());
                    BannerPageInfo.BannerInfo bannerInfo = bannerPageInfo.data.get(dayOfWeek);

                    ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).bannerSuccess(bannerInfo.data);
                }));
    }
}
