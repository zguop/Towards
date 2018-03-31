package com.waitou.towards.model.gallery;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.jakewharton.rxbinding2.view.RxView;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.databinding.ActivityGalleryBinding;
import com.waitou.towards.model.gallery.helper.CardAdapter;
import com.waitou.towards.model.gallery.helper.CardScaleHelper;
import com.waitou.wt_library.base.CollapsingXActivity;
import com.waitou.wt_library.kit.AlertToast;
import com.waitou.wt_library.recycler.LayoutManagerUtil;

import java.util.List;

/**
 * Created by waitou on 17/2/23.
 * 妹子相册
 */

public class GalleryActivity extends CollapsingXActivity<GalleryPresenter, ActivityGalleryBinding> {

    private CardAdapter<GankResultsTypeInfo> mAdapter;
    private CardScaleHelper                  mCardScaleHelper;

    @Override
    protected int getCollContentViewId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initCollData(Bundle savedInstanceState) {
        goneToolBar();
        transparentStatusBar(this);
        mAdapter = new CardAdapter<>(this, R.layout.item_gallery);
        getCollBinding().setManager(LayoutManagerUtil.getHorizontalLayoutManager(this));
        getCollBinding().setAdapter(mAdapter);
        getCollBinding().setPresenter(getP());
        rxClick();
        reloadData();
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
        getCollBinding().include.xList.setDefaultPageSize();
        if (mCardScaleHelper == null) {
            mCardScaleHelper = new CardScaleHelper();
            mCardScaleHelper.attachToRecyclerView(getCollBinding().include.xList);
        } else {
            mCardScaleHelper.notifyChangeWidth();
        }
    }

    private void rxClick() {
        pend(RxView.clicks(getCollBinding().fabBtn)
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
