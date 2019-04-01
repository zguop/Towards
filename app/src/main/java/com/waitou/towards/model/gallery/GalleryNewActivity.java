package com.waitou.towards.model.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ObjectUtils;
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
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.kit.UImage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * auth aboom
 * date 2019/3/31
 */
public class GalleryNewActivity extends XActivity<GalleryNewPresenter, ActivityNewGalleryBinding> implements PullRecyclerView.OnLoadMoreListener {

    private MuRecyclerAdapter adapter;
    private Disposable        subscribe;
    private Disposable        cacheSubscribe;
    private int               choosePosition = -1;
    private boolean           transformer;

    @Override
    public int getContentViewId() {
        return R.layout.activity_new_gallery;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        transparencyBar();
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
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
                            UImage.startSwitchBackgroundAnim(getBinding().list, ImageUtils.fastBlur(drawingCache, 1, 25));
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
                        .error(R.drawable.place_holder)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                imageView.setImageDrawable(resource);
                                info.isShowImageUrl = true;
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                imageView.setImageDrawable(errorDrawable);
                                info.isShowImageUrl = false;
                            }
                        });
            }
        });
        getBinding().list.setAdapter(adapter);
        getBinding().list.setOnLoadMoreListener(this);
        getBinding().setPresenter(getP());
        getBinding().cycle.setOnClickListener(v -> {
            getBinding().menu.hideMenu(true);
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
        });
        getBinding().style.setOnClickListener(v -> {
            getBinding().menu.close(true);
            transformer = !transformer;
            if (transformer) {
                layoutManager.setItemTransformer(new CurveTransformer());
            } else {
                layoutManager.setItemTransformer(new ScaleTransformer());
            }
            getBinding().list.setAdapter(adapter);
        });
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
}
