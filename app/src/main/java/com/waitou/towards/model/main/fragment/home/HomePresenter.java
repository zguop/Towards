package com.waitou.towards.model.main.fragment.home;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;

import com.cocosw.bottomsheet.BottomSheet;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.R;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.bean.GankResultsInfo;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.common.ExtraValue;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.cache.Repository;
import com.waitou.towards.util.AlertToast;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.cache.SharedPref;
import com.waitou.wt_library.kit.Kits;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.rx_cache.Reply;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.waitou.towards.R.id.menu_all;
import static com.waitou.towards.R.id.menu_app;
import static com.waitou.towards.R.id.menu_front;
import static com.waitou.towards.R.id.menu_ios;
import static com.waitou.towards.R.id.menu_movie;
import static com.waitou.towards.R.id.menu_source;

/**
 * Created by waitou on 17/3/5.
 * 首页presenter
 */

public class HomePresenter extends XPresent<HomeFragment> implements SingleViewPagerAdapter.Presenter, MultiTypeAdapter.Presenter {

    private HomeCommendFragment mHomeCommendFragment;
    private HomeCargoFragment   mCargoFragment;
    private HomeAndroidFragment mHomeAndroidFragment;

    public ObservableField<CharSequence> txName = new ObservableField<>("全部");

    /**
     * 广告位的点击方法
     */
    public void onBannerItemClick(BannerPageInfo url, int position) {

    }

    /**
     * 加载HomeCommendFragment首页数据
     */
    public void loadHomeData() {

        getV().pend(Observable.zip(DataLoader.getGithubApi().getBannerPage(), DataLoader.getGithubApi().getHomeData(), Pair::create)
                .compose(RxTransformerHelper.applySchedulers())
                .map(pair -> {
                    if (pair.first != null && pair.first.result != null && pair.first.result.size() > 0) {
                        int dayOfWeek = Kits.Date.getDayOfWeek(new Date()) - 1;
                        getHomeCommendFragment().onBannerSuccess(pair.first.result.get(dayOfWeek));
                    }
                    if (pair.second != null && pair.second.result != null && pair.second.result.function.size() > 0) {
                        getHomeCommendFragment().onFunctionSuccess(pair.second.result.function);
                    }
                    return Kits.Date.getCurrentDate().split("-");
                })
                .observeOn(Schedulers.io())
                .flatMap(currentDate -> getGankIoDay(currentDate[0], currentDate[1], currentDate[2]))
                .flatMap(gankResultsInfo -> {
                    List<List<GankResultsTypeInfo>> lists = new ArrayList<>();
                    if (gankResultsInfo != null) {
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.福利)) {
                            lists.add(gankResultsInfo.福利);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.休息视频)) {
                            lists.add(gankResultsInfo.休息视频);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.Android)) {
                            lists.add(gankResultsInfo.Android);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.瞎推荐)) {
                            lists.add(gankResultsInfo.瞎推荐);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.App)) {
                            lists.add(gankResultsInfo.App);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.iOS)) {
                            lists.add(gankResultsInfo.iOS);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.拓展资源)) {
                            lists.add(gankResultsInfo.拓展资源);
                        }
                        if (Kits.Util.isNotEmptyList(gankResultsInfo.前端)) {
                            lists.add(gankResultsInfo.前端);
                        }
                        if (lists.size() > 0) {
                            lists.get(0).get(0).isShowTitle = true;
                        }
                    }
                    return Observable.just(lists);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(lists -> {
                    if (lists.size() > 0) {
                        for (List<GankResultsTypeInfo> list : lists) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).type.equals("福利")) {
                                    list.get(i).desc = "今日美图";
                                    if (list.get(i).images == null) {
                                        list.get(i).images = new ArrayList<>();
                                        list.get(i).images.add(list.get(i).url);
                                    }
                                }
                            }
                        }
                    }
                })
                .subscribe(lists -> getHomeCommendFragment().onSuccess(lists)
                        , throwable -> getHomeCommendFragment().onError(throwable)));
    }

    private Observable<GankResultsInfo> getGankIoDay(String year, String month, String day) {
        String everyday = SharedPref.get().getString(ExtraValue.EVERYDAY_DATA, "2017-03-04");
        String currentEveryday = Kits.Date.getCurrentDate();
        boolean isReload = false;
        if (!everyday.equals(currentEveryday)) { //第二天
            if (!Kits.Date.isRightTime(12, 30)) { //如果是早上 取缓存 如果缓存没有 请求前一天数据
                day = (Integer.parseInt(day) - 1) + "";
            } else {
                isReload = true;
            }
        }
        Observable<Reply<GankResultsInfo>> gankIoDay = Repository.getRepository().getGankIoDay(year, month, day, isReload);
        return gankIoDay.map(reply -> {
            Log.e("aa",reply.toString());
            SharedPref.get().put(ExtraValue.EVERYDAY_DATA, currentEveryday);
            return reply.getData();
        });
    }

    public void loadCargoData(String type, int page) {
        getV().pend(DataLoader.getGankApi().getGankIoData(type, page)
                .compose(RxTransformerHelper.applySchedulers())
                .filter(RxTransformerHelper.verifyNotEmpty())
                .map(dayInfo -> dayInfo.results)
                .subscribe(info -> mCargoFragment.onSuccess(info, page == 1),
                        throwable -> mCargoFragment.showError(page == 1)));
    }

    public void showBottomSheet(View view) {
        new BottomSheet.Builder((Activity) view.getContext())
                .title("选择分类").sheet(R.menu.menu_gank_bottom_sheet)
                .listener(item -> {
                    if (txName.get().equals(item.getTitle())) {
                        AlertToast.show("当前已经是 " + txName.get() + " 分类");
                        return true;
                    }
                    txName.set(item.getTitle());
                    mCargoFragment.showLoading();
                    switch (item.getItemId()) {
                        case menu_all:
                            loadCargoData("all", 1);
                            break;
                        case menu_ios:
                            loadCargoData("iOS", 1);
                            break;
                        case menu_app:
                            loadCargoData("App", 1);
                            break;
                        case menu_front:
                            loadCargoData("前端", 1);
                            break;
                        case menu_movie:
                            loadCargoData("休息视频", 1);
                            break;
                        case menu_source:
                            loadCargoData("拓展资源", 1);
                            break;
                    }
                    return true;
                }).grid().show();
    }

    public String setGankPageTime(String publishedAt) {
        String date = publishedAt.replace('T', ' ').replace('Z', ' ');
        return Kits.Date.friendlyTime(date);
    }

    public HomeCommendFragment getHomeCommendFragment() {
        if (mHomeCommendFragment == null) {
            mHomeCommendFragment = new HomeCommendFragment();
            mHomeCommendFragment.setPresenter(this);
        }
        return mHomeCommendFragment;
    }

    public HomeCargoFragment getCargoFragment() {
        if (mCargoFragment == null) {
            mCargoFragment = new HomeCargoFragment();
            mCargoFragment.setPresenter(this);
        }
        return mCargoFragment;
    }

    public HomeAndroidFragment getHomeAndroidFragment() {
        if (mHomeAndroidFragment == null) {
            mHomeAndroidFragment = new HomeAndroidFragment();
        }
        return mHomeAndroidFragment;
    }
}
