package com.waitou.towards.model.gallery;

import android.Manifest;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.to.aboomy.utils_lib.AlertToast;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.databinding.ActivityGalleryBinding;
import com.waitou.towards.model.gallery.helper.CardAdapter;
import com.waitou.towards.model.gallery.helper.CardScaleHelper;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.recycler.LayoutManagerUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by waitou on 17/2/23.
 * 妹子相册
 */

public class GalleryActivity extends XActivity<GalleryPresenter, ActivityGalleryBinding> {

    private CardAdapter<GankResultsTypeInfo> mAdapter;
    private CardScaleHelper                  mCardScaleHelper;
    private Disposable                       mSubscribe;

    @Override
    public int getContentViewId() {
        return R.layout.activity_gallery;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        transparencyBar();
        mAdapter = new CardAdapter<>(this, R.layout.item_gallery);
        getBinding().setManager(LayoutManagerUtil.getHorizontalLayoutManager(this));
        getBinding().setAdapter(mAdapter);
        getBinding().setPresenter(getP());
        rxClick();
        reloadData();
        initCycle();
    }

    private void initCycle() {
        getBinding().cycle.setOnClickListener(v -> {
            if (mSubscribe != null && !mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            } else {
                mSubscribe = Flowable.interval(1, 2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .onBackpressureDrop()
                        .subscribe(aLong -> {
                            if (mCardScaleHelper != null) {
                                mCardScaleHelper.setCurrentItemPos(mCardScaleHelper.getCurrentItemPos() + 1);
                                mCardScaleHelper.notifyChangeWidth();
                            }
                        });
            }
        });
    }

    @Override
    public void reloadData() {
        showLoading();
        getP().loadData(1);
    }

    @Override
    public GalleryPresenter createPresenter() {
        return new GalleryPresenter();
    }

    public void onError(boolean isReload) {
        if (isReload) {
            showError();
            return;
        }
        AlertToast.show("请检查网络！");
    }

    public void onSuccess(List<GankResultsTypeInfo> galleryInfo) {
        if (galleryInfo == null || galleryInfo.size() == 0) {
            showEmpty();
            return;
        }
        showContent();
        mAdapter.addAll(galleryInfo);
        getBinding().include.xList.setDefaultPageSize();
        if (mCardScaleHelper == null) {
            mCardScaleHelper = new CardScaleHelper();
            mCardScaleHelper.attachToRecyclerView(getBinding().include.xList);
        } else {
            mCardScaleHelper.notifyChangeWidth();
        }
    }

    private void rxClick() {
        pend(RxView.clicks(getBinding().fabBtn)
                .compose(getRxPermissions().ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(permission -> {
                    if (permission.granted) {
                        if (mCardScaleHelper != null) mCardScaleHelper.saveImageToGallery();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        AlertToast.show("保存图片需要授权该权限！"); //拒绝了权限
                    } else {
                        AlertToast.show("请到应用设置中开启权限哦！");//永久拒绝了权限
                    }
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
