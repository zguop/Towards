package com.waitou.towards.model.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.recycler_lib.BindingItemProvider;
import com.to.aboomy.recycler_lib.Displayable;
import com.to.aboomy.recycler_lib.MuRecyclerAdapter;
import com.to.aboomy.recycler_lib.PullRecyclerView;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.databinding.ActivityNewGalleryBinding;
import com.waitou.towards.model.gallery.helper.CurveTransformer;
import com.waitou.towards.model.gallery.helper.ScaleTransformer;
import com.waitou.towards.util.KitUtils;
import com.waitou.wt_library.base.XActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * auth aboom
 * date 2019/3/31
 */
public class GalleryNewActivity extends XActivity<GalleryNewPresenter, ActivityNewGalleryBinding> implements PullRecyclerView.OnLoadMoreListener {

    private MuRecyclerAdapter    adapter;
    private GalleryLayoutManager layoutManager;

    private Disposable subscribe;
    private Disposable cacheSubscribe;
    private boolean    transformer;
    private int        choosePosition = -1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_new_gallery;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        transparencyBar();
        layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(getBinding().list.getContentView());
        layoutManager.setItemTransformer(new ScaleTransformer());
        layoutManager.setOnItemSelectedListener((recyclerView, item, position) -> {
            if (choosePosition == position) {
                return;
            }
            choosePosition = position;
            GankResultsTypeInfo info = (GankResultsTypeInfo) adapter.getItem(position);
            if (info != null) {
                if (cacheSubscribe != null && !cacheSubscribe.isDisposed()) {
                    cacheSubscribe.dispose();
                }
                cacheSubscribe = Observable.just(item)
                        .doOnNext(view -> {
                            while (ObjectUtils.isEmpty(info.isShowImageUrl)) {
                                SystemClock.sleep(50);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view -> {
                            item.setDrawingCacheEnabled(true);
                            Bitmap drawingCache = item.getDrawingCache();
                            KitUtils.startSwitchBackgroundAnim(getBinding().list, ImageUtils.fastBlur(drawingCache, 1, 25));
                            item.setDrawingCacheEnabled(false);
                        });
            }
        });
        adapter = new MuRecyclerAdapter();
        adapter.addProvider(new BindingItemProvider() {
            int width = (int) (ScreenUtils.getScreenWidth() * 0.75f);
            int height = (int) (ScreenUtils.getScreenHeight() * 0.85f);

            @Override
            public boolean isForViewType(@NonNull Displayable item) {
                return true;
            }

            @Override
            public int layout() {
                return R.layout.item_gallery;
            }

            @Override
            public void convert(BaseViewHolder helper, Displayable data, int position) {
                helper.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                GankResultsTypeInfo info = (GankResultsTypeInfo) data;
                if (info.isShowImageUrl != null && !info.isShowImageUrl) {
                    info.isShowImageUrl = null;
                }
                ImageView imageView = helper.getView(R.id.image);
                Glide.with(GalleryNewActivity.this)
                        .load(info.url)
                        .placeholder(R.drawable.place_holder)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                imageView.setImageDrawable(placeholder);
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                imageView.setImageDrawable(resource);
                                info.isShowImageUrl = true;
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                info.isShowImageUrl = false;
                            }
                        });
            }
        });
        getBinding().list.setAdapter(adapter);
        getBinding().list.setOnLoadMoreListener(this);
        getBinding().setPresenter(getP());
        getBinding().cycle.setOnClickListener(v -> cycle());
        getBinding().style.setOnClickListener(v -> transformer());
        getBinding().save.setOnClickListener(v -> requestStoragePermission(this::saveImage));
        reloadData();
    }

    @Override
    public GalleryNewPresenter createPresenter() {
        return new GalleryNewPresenter();
    }

    @Override
    public void reloadData() {
        showLoading();
        getP().loadData(1);
    }

    @Override
    public void onLoadMore(int page) {
        getP().loadData(page);
    }

    public void onError(boolean isReload) {
        if (isReload) {
            showError();
            return;
        }
        ToastUtils.showShort("请检查网络！");
    }

    public void onSuccess(List<GankResultsTypeInfo> galleryInfo) {
        if (ObjectUtils.isEmpty(galleryInfo)) {
            showEmpty();
            return;
        }
        showContent();
        adapter.addData(galleryInfo);
        getBinding().list.loadComplete(galleryInfo.size() < 10);
    }

    private void cycle() {
        getBinding().menu.close(true);
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        } else {
            subscribe = Flowable.interval(1, 2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .onBackpressureDrop()
                    .subscribe(aLong -> {
                        int currentPosition = layoutManager.getCurSelectedPosition();
                        getBinding().list.getContentView().smoothScrollToPosition(currentPosition + 1);
                    });
        }
    }

    private void transformer() {
        getBinding().menu.close(true);
        transformer = !transformer;
        if (transformer) {
            layoutManager.setItemTransformer(new CurveTransformer());
        } else {
            layoutManager.setItemTransformer(new ScaleTransformer());
        }
        getBinding().list.setAdapter(adapter);
    }

    private void requestStoragePermission(Action action) {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(shouldRequest -> {
                    LogUtils.e("设置拒绝权限后再次请求的回调接口");
                    shouldRequest.again(true);
                }).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                try {
                    action.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                ToastUtils.showShort(ObjectUtils.isEmpty(permissionsDeniedForever) ? "保存图片需要授权该权限!" : "请到应用设置中开启存储权限!"); //拒绝了权限
            }
        }).request();
    }

    private void saveImage() {
        int curSelectedPosition = layoutManager.getCurSelectedPosition();
        Displayable item = adapter.getItem(curSelectedPosition);
        if (item == null) {
            return;
        }
        getBinding().menu.close(true);
        String url = ((GankResultsTypeInfo) item).url;
        KitUtils.downLoaderImg(url, PathUtils.getExternalPicturesPath(), path -> {
            LogUtils.e("图片下载完成路径 " + path);
            KitUtils.saveImageToGallery(new File(path));
            ToastUtils.showShort("图片已保存到相册");
        });
    }
}
