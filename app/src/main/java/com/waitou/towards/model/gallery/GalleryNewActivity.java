package com.waitou.towards.model.gallery;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.clans.fab.FloatingActionMenu;
import com.to.aboomy.recycler_lib.PullRecyclerView;
import com.to.aboomy.recycler_lib.adapter.Displayable;
import com.to.aboomy.recycler_lib.adapter.MultipleAdapter;
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate;
import com.to.aboomy.statusbar_lib.StatusBarUtil;
import com.waitou.imgloader_lib.ImageLoader;
import com.waitou.net_library.model.APIResult;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.model.gallery.helper.CurveTransformer;
import com.waitou.towards.model.gallery.helper.ScaleTransformer;
import com.waitou.towards.util.KitUtils;
import com.waitou.wt_library.base.BasePageActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * auth aboom
 * date 2019/3/31
 */
public class GalleryNewActivity extends BasePageActivity implements PullRecyclerView.OnLoadMoreListener {

    private MultipleAdapter adapter;
    private GalleryLayoutManager    layoutManager;

    private Disposable         subscribe;
    private boolean            transformer;
    private int                choosePosition;
    private FloatingActionMenu floatingActionMenu;
    private PullRecyclerView   pullRecyclerView;
    private GalleryViewModule  galleryViewModule;

    @Override
    public View getContentView() {
        return ff(R.layout.activity_new_gallery);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        StatusBarUtil.transparencyBar(this, false);
        pullRecyclerView = f(R.id.list);
        floatingActionMenu = f(R.id.menu);
        layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(pullRecyclerView.getContentView());
        layoutManager.setItemTransformer(new ScaleTransformer());
        layoutManager.setOnItemSelectedListener((recyclerView, item, position) -> {
            choosePosition = position;
            GankResultsTypeInfo info = (GankResultsTypeInfo) adapter.getItem(position);
            if (info != null && info.isShowImageUrl) {
                item.post(() -> {
                    item.setDrawingCacheEnabled(true);
                    Bitmap drawingCache = item.getDrawingCache();
                    KitUtils.startSwitchBackgroundAnim(pullRecyclerView, ImageUtils.fastBlur(drawingCache, 1, 25));
                    item.setDrawingCacheEnabled(false);
                });
            }
        });
        adapter = new MultipleAdapter();
        adapter.addDelegate(new AdapterDelegate() {
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
                ImageView imageView = helper.getView(R.id.image);
                ImageLoader.displayImage(imageView, info.url, R.drawable.place_holder, new DrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        info.isShowImageUrl = true;
                        if (choosePosition == position) {
                            KitUtils.startSwitchBackgroundAnim(pullRecyclerView, ImageUtils.fastBlur(ImageUtils.drawable2Bitmap(resource), 1, 25));
                        }
                    }
                });
            }
        });
        pullRecyclerView.setAdapter(adapter);
        pullRecyclerView.setOnLoadMoreListener(this);
        f(R.id.cycle).setOnClickListener(v -> cycle());
        f(R.id.style).setOnClickListener(v -> transformer());
        f(R.id.save).setOnClickListener(v -> requestStoragePermission(this::saveImage));

        galleryViewModule = ViewModelProviders.of(this).get(GalleryViewModule.class);
        galleryViewModule.getLiveData().observe(this, this::bindUI);
        reloadData();
    }

    @Override
    public void reloadData() {
        showLoading();
        galleryViewModule.getGirlPics();
    }

    @Override
    public void onLoadMore(int page) {
        galleryViewModule.getGirlPics(page);
    }

    public void bindUI(APIResult<List<GankResultsTypeInfo>> apiResult) {
        if (!apiResult.isSuccess()) {
            if (apiResult.getPageIndex() == 1) {
                showFailed();
            }
            return;
        }
        List<GankResultsTypeInfo> data = apiResult.getData();
        showContent();
        adapter.addData(data);
        pullRecyclerView.loadComplete(data.size() < 10);
    }

    private void cycle() {
        floatingActionMenu.close(true);
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        } else {
            subscribe = Flowable.interval(1, 2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .onBackpressureDrop()
                    .subscribe(aLong -> {
                        int currentPosition = layoutManager.getCurSelectedPosition();
                        pullRecyclerView.getContentView().smoothScrollToPosition(currentPosition + 1);
                    });
        }
    }

    private void transformer() {
        floatingActionMenu.close(true);
        transformer = !transformer;
        if (transformer) {
            layoutManager.setItemTransformer(new CurveTransformer());
        } else {
            layoutManager.setItemTransformer(new ScaleTransformer());
        }
        pullRecyclerView.setAdapter(adapter);
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
        floatingActionMenu.close(true);
        String url = ((GankResultsTypeInfo) item).url;
        KitUtils.downLoaderImg(url, PathUtils.getExternalPicturesPath(), path -> {
            LogUtils.e("图片下载完成路径 " + path);
            KitUtils.saveImageToGallery(new File(path));
            ToastUtils.showShort("图片已保存到相册");
        });
    }
}
