package com.waitou.towards.model.graffiti;

import android.Manifest;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.waitou.photo_library.PhotoPickerFinal;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.towards.R;
import com.waitou.towards.bean.GraffitiToolInfo;
import com.waitou.towards.databinding.ItemSeekBarBinding;
import com.waitou.towards.enums.GraffitiToolEnum;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.towards.view.dialog.ListOfDialog;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.AlertToast;
import com.waitou.wt_library.kit.UDimens;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.xw.repo.BubbleSeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.waitou.wt_library.kit.UDimens.getDeviceWidth;

/**
 * Created by waitou on 17/3/27.
 * 涂鸦
 */

public class GraffitiPresenter extends XPresent<GraffitiActivity> implements BaseViewAdapter.Presenter {

    //工具选择的type
    public ObservableInt           toolType      = new ObservableInt(0);
    //画笔粗细控制
    public ObservableInt           toolWidth     = new ObservableInt(14);
    //画笔颜色
    public ObservableInt           toolColor     = new ObservableInt(Color.parseColor("#99ff0000"));
    //图片缩放
    public ObservableFloat         scale         = new ObservableFloat(1.0f);
    //图片旋转
    public ObservableInt           rotate        = new ObservableInt(0);
    //左移右移
    public ObservableInt           leftMoveRight = new ObservableInt(0);
    //上移下移
    public ObservableInt           topMoveBottom = new ObservableInt(0);
    //上传的图片
    public ObservableField<Bitmap> bitmapField   = new ObservableField<>();
    //图片是否可以操作
    public ObservableBoolean       enable        = new ObservableBoolean(checkBitmap());

    private SingleTypeAdapter<GraffitiToolInfo> mGraffitiToolAdapter;
    private BaseDialog                          mToolDialog;
    private BaseDialog                          mSeekBarDialog;


    /**
     * 选择工具
     */
    public void selectToolShowDialog() {
        if (mGraffitiToolAdapter == null) {
            mGraffitiToolAdapter = new SingleTypeAdapter<>(getV(), R.layout.item_select_tool);
            mGraffitiToolAdapter.setPresenter(this);
            List<GraffitiToolInfo> toolInfoList = new ArrayList<>();
            for (GraffitiToolEnum toolEnum : GraffitiToolEnum.values()) {
                GraffitiToolInfo info = new GraffitiToolInfo();
                info.drawable = ContextCompat.getDrawable(getV(), toolEnum.getRedId());
                info.tool = toolEnum.getTool();
                toolInfoList.add(info);
            }
            mGraffitiToolAdapter.set(toolInfoList);
        }
        if (mToolDialog == null) {
            mToolDialog = new ListOfDialog(getV())
                    .setLayoutManager(LayoutManagerUtli.getGridLayoutManager(getV(), 3))
                    .setAdapter(mGraffitiToolAdapter)
                    .setTitle("工具选择");
        }
        mToolDialog.show();
    }

    /**
     * 选择工具的点击回调方法
     */
    public void selectToolItemClick(int position) {
        toolType.set(position);
        mToolDialog.dismiss();
    }

    /**
     * 调整画笔宽度
     */
    public void seekToolWidthDialog() {
        if (mSeekBarDialog == null) {
            mSeekBarDialog = new BaseDialog(getV());
            ItemSeekBarBinding inflate = DataBindingUtil.inflate(getV().getLayoutInflater(), R.layout.item_seek_bar, null, false);
            inflate.seekBar.setProgress(toolWidth.get());
            inflate.seekBar.setOnProgressChangedListener(mChangedListener);
            mSeekBarDialog.setDialogContentView(inflate.getRoot());
            mSeekBarDialog.setTitle("画笔大小");
        }
        mSeekBarDialog.show();
    }

    private BubbleSeekBar.OnProgressChangedListenerAdapter mChangedListener = new BubbleSeekBar.OnProgressChangedListenerAdapter() {
        @Override
        public void getProgressOnActionUp(int progress, float progressFloat) {
            toolWidth.set(progress);
            mSeekBarDialog.dismiss();
        }
    };

    /**
     * 选择颜色
     */
    public void selectColorDialog() {
        new ColorPickerDialog(getV(), toolColor.get(), color -> toolColor.set(color)).show();
    }

    /**
     * 上传图片
     */
    public void uploadPic() {
        getV().pend(PhotoPickerFinal.get()
                .with(getV())
                .isMultiMode(false)
                .isCrop(true)
                .executePhoto(info -> {
                    PhotoInfo photoInfo = info.get(0);
                    Glide.with(getV())
                            .load(new File(photoInfo.photoPath))
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(new SimpleTarget<Bitmap>(photoInfo.photoWidth, photoInfo.photoHeight) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    reset();
                                    bitmapField.set(resource);
                                    enable.set(checkBitmap());
                                }
                            });
                }));
    }

    /**
     * 放大图片
     */
    public void scaleBigPic() {
        this.scale.set(scale.get() + 0.05f);
    }

    /**
     * 缩小图片
     */
    public void scaleSmallPic() {
        float scale = this.scale.get() - 0.05f;
        if (scale < 0.1) {
            return;
        }
        this.scale.set(scale);
    }

    /**
     * 旋转图片 90
     */
    public void rotatePic() {
        this.rotate.set((rotate.get() + 90) % 360);
    }

    /**
     * 重置图片属性
     */
    public void reset() {
        this.scale.set(1f);
        this.rotate.set(0);
        this.leftMoveRight.set(0);
        this.topMoveBottom.set(0);
    }

    /**
     * 图片向右移动
     */
    public Consumer<Integer> moveRight() {
        return integer -> interval(integer, aLong -> this.leftMoveRight.set(this.leftMoveRight.get() + 1));
    }

    /**
     * 图片向左移动
     */
    public Consumer<Integer> moveLeft() {
        return integer -> interval(integer, aLong -> this.leftMoveRight.set(this.leftMoveRight.get() - 1));
    }

    /**
     * 图片向上移动
     */
    public Consumer<Integer> moveTop() {
        return integer -> interval(integer, aLong -> this.topMoveBottom.set(this.topMoveBottom.get() - 1));
    }

    /**
     * 图片向下移动
     */
    public Consumer<Integer> moveBottom() {
        return integer -> interval(integer, aLong -> this.topMoveBottom.set(this.topMoveBottom.get() + 1));
    }

    private boolean checkBitmap() {
        return bitmapField.get() != null;
    }

    private Disposable Disposable;

    private void interval(int actionMasked, Consumer<Long> action) {
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            Disposable = Flowable.interval(0, 10, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .onBackpressureDrop()
                    .subscribe(action);
        }
        if (actionMasked == MotionEvent.ACTION_UP) {
            if (Disposable != null && !Disposable.isDisposed()) {
                Disposable.dispose();
            }
        }
    }

    @BindingAdapter("move")
    public static void onTouch(View view, Consumer<Integer> action) {
        view.setOnTouchListener((v, event) -> {
            try {
                action.accept(MotionEventCompat.getActionMasked(event));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    /**
     * 图片保存
     */
    public void save(GraffitiView graffitiView, GraffitiPicView graffitiPicView) {
        getV().pend(getV().getRxPermissions().requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        Bitmap bitmap = Bitmap.createBitmap(getDeviceWidth(), UDimens.getDeviceHeight(), Bitmap.Config.ARGB_8888);
                        Canvas bitCanvas = new Canvas(bitmap);
                        if (graffitiPicView.checkSave() || graffitiView.checkSave()) {
                            graffitiPicView.doDraw(bitCanvas);
                            graffitiView.doDraw(bitCanvas);
                            UImage.saveImageToGallery(getV(), bitmap);
                            bitmap.recycle();
                            AlertToast.show("图片成功保存到相册O(∩_∩)O~");
                        } else {
                            AlertToast.show("先绘制点什么吧!╮(╯▽╰)╭");
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        AlertToast.show("保存图片需要授权该权限！"); //拒绝了权限
                    } else {
                        AlertToast.show("请到应用设置中开启权限哦！");//永久拒绝了权限
                    }
                }));
    }
}
