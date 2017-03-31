package com.waitou.towards.model.graffiti;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.waitou.towards.R;
import com.waitou.towards.bean.GraffitiToolInfo;
import com.waitou.towards.databinding.ItemSeekBarBinding;
import com.waitou.towards.enums.GraffitiToolEnum;
import com.waitou.towards.util.AlertToast;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.towards.view.dialog.ListOfDialog;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.UDimens;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 17/3/27.
 * 涂鸦
 */

public class GraffitiPresenter extends XPresent<GraffitiActivity> implements BaseViewAdapter.Presenter {

    //工具选择的type
    public ObservableInt   toolType  = new ObservableInt(0);
    //画笔粗细控制
    public ObservableInt   toolWidth = new ObservableInt(14);
    //画笔颜色
    public ObservableInt   toolColor = new ObservableInt(Color.BLUE);
    //图片缩放
    public ObservableFloat scale     = new ObservableFloat(1.0f);

    private SingleTypeAdapter<GraffitiToolInfo> mGraffitiToolAdapter;
    private BaseDialog                          mToolDialog;
    private BaseDialog                          mSeekBarDialog;

    /*--------------- 选择工具 ---------------*/
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

    public void selectToolItemClick(int position) {
        toolType.set(position);
        mToolDialog.dismiss();
    }
    /*--------------- 选择工具 end---------------*/

    /*--------------- 画笔宽度 ---------------*/
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
    /*--------------- 画笔宽度 end ---------------*/

    /*--------------- 颜色选择 ---------------*/
    public void selectColorDialog() {
        new ColorPickerDialog(getV(), toolColor.get(), color -> toolColor.set(color)).show();
    }
    /*--------------- 颜色选择end ---------------*/


    public void scaleBigPic() {
        this.scale.set(scale.get() + 0.05f);
    }

    public void scaleSmallPic() {
        this.scale.set(scale.get() - 0.05f);
    }

    /*--------------- 图片保存 ---------------*/
    public void save(GraffitiView graffitiView, GraffitiPicView graffitiPicView) {
        getV().pend(getV().getRxPermissions().requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        Bitmap bitmap = Bitmap.createBitmap(UDimens.getDeviceWidth(), UDimens.getDeviceHeight(), Bitmap.Config.ARGB_8888);
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
