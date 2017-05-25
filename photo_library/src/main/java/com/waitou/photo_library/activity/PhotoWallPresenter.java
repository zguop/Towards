package com.waitou.photo_library.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.waitou.net_library.log.LogUtil;
import com.waitou.photo_library.PhotoPickerFinal;
import com.waitou.photo_library.R;
import com.waitou.photo_library.bean.PhotoFolderInfo;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.photo_library.util.MediaScanner;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.photo_library.view.FolderPopUpWindow;
import com.waitou.photo_library.view.ProgressDialogFragment;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.AlertToast;
import com.waitou.wt_library.kit.UDate;
import com.waitou.wt_library.kit.UDimens;
import com.waitou.wt_library.kit.UFile;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.kit.USDCard;
import com.waitou.wt_library.kit.Util;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.waitou.wt_library.router.Router;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by waitou on 17/4/3.
 * 图片选择
 */

public class PhotoWallPresenter extends XPresent<PhotoWallActivity> implements LoaderManager.LoaderCallbacks<Cursor>, BaseViewAdapter.Presenter {

    public PhotoPickerFinal mPhotoPickerFinal;

    public PhotoWallPresenter() {
        mPhotoPickerFinal = PhotoPickerFinal.get();
    }

    private static final int      LOADER_ALL       = 0;         //加载所有图片
    private static final int      LOADER_CATEGORY  = 1;    //分类加载图片
    private static final int      LOADER_PHOTO     = 2;
    private static final String[] IMAGE_PROJECTION = {     //查询图片需要的数据列
            MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
            MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
            MediaStore.Images.Media.WIDTH,          //图片的宽度，int型  1920
            MediaStore.Images.Media.HEIGHT,         //图片的高度，int型  1080
            MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
            MediaStore.Images.Media.DATE_ADDED};    //图片被添加的时间，long型  1450518608

    private ArrayList<PhotoFolderInfo> photoFolderInfoList = new ArrayList<>();   //所有的图片文件夹

    /**
     * @param path 指定扫描的文件夹目录，可以为 null，表示扫描所有图片
     */
    void imageDataSource(String path) {
        LoaderManager loaderManager = getV().getSupportLoaderManager();
        if (TextUtils.isEmpty(path)) {
            loaderManager.initLoader(LOADER_ALL, null, this);//加载所有的图片
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_PHOTO, bundle, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        switch (id) {
            case LOADER_ALL: //扫描所有图片
                cursorLoader = new CursorLoader(getV(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[6] + " DESC");
                break;
            case LOADER_CATEGORY: //扫描某个图片文件夹
                cursorLoader = new CursorLoader(getV(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[6] + " DESC");
                break;
            case LOADER_PHOTO: //扫描某个图片信息
                cursorLoader = new CursorLoader(getV(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + "=?", new String[]{args.getString("path")}, null);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            switch (loader.getId()) {
                case LOADER_ALL:
                    loaderAll(data);
                    break;
                case LOADER_CATEGORY:
                    break;
                case LOADER_PHOTO:
                    loaderPhoto(data);
                    break;
            }
        }
        getV().getSupportLoaderManager().destroyLoader(loader.getId());
    }

    private void loaderPhoto(Cursor data) {
        while (data.moveToNext()) {
            selectionList.add(createPhotoInfo(data));
        }
        getV().submit();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    /**
     * 加载所有图片信息
     */
    private void loaderAll(Cursor data) {
        ArrayList<PhotoInfo> allPhotoList = new ArrayList<>();   //所有图片的集合,不分文件夹
        PhotoInfo headPhotoInfo = new PhotoInfo();
        headPhotoInfo.photoPath = "";
        while (data.moveToNext()) {
            //查询数据
            PhotoInfo photoInfo = createPhotoInfo(data);
            allPhotoList.add(photoInfo);
            //根据父路径分类存放图片
            File photoFile = new File(photoInfo.photoPath);
            File parentFile = photoFile.getParentFile();
            PhotoFolderInfo photoFolderInfo = new PhotoFolderInfo();
            photoFolderInfo.folderName = parentFile.getName();
            photoFolderInfo.folderPath = parentFile.getAbsolutePath();
            //f分类图片集合
            if (!photoFolderInfoList.contains(photoFolderInfo)) {
                photoFolderInfo.cover = photoInfo;
                photoFolderInfo.photoList = new ArrayList<>();
                if (mPhotoPickerFinal.isShowCamera()) {
                    photoFolderInfo.photoList.add(headPhotoInfo);
                }
                photoFolderInfo.photoList.add(photoInfo);
                photoFolderInfoList.add(photoFolderInfo);
            } else {
                photoFolderInfoList.get(photoFolderInfoList.indexOf(photoFolderInfo)).photoList.add(photoInfo);
            }
        }
        //防止没有图片报异常
        if (data.getCount() > 0) {
            //构造所有图片的集合
            PhotoFolderInfo folderInfo = new PhotoFolderInfo();
            folderInfo.folderName = "全部图片";
            folderInfo.folderPath = "/";
            folderInfo.cover = allPhotoList.get(0);
            folderInfo.photoList = allPhotoList;

            if (mPhotoPickerFinal.isShowCamera()) {
                folderInfo.photoList.add(0, headPhotoInfo);
            }
            photoFolderInfoList.add(0, folderInfo);  //确保第一条是所有图片
        }

        if (photoFolderInfoList.size() > 0) {
            PhotoFolderInfo photoFolderInfo = photoFolderInfoList.get(0);
            getV().onPhotoLoaded(photoFolderInfo.photoList);
            folderName.set(photoFolderInfo.folderName);
            //将当期相册的所有图片设置到单利中
            mPhotoPickerFinal.setPhotoList(mPhotoPickerFinal.isShowCamera()
                    ? photoFolderInfo.photoList.subList(1, photoFolderInfo.photoList.size())
                    : photoFolderInfo.photoList);
        }
    }

    private PhotoInfo createPhotoInfo(Cursor data) {
        String photoName = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
        String photoPath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
        long photoSize = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
        int photoWidth = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
        int photoHeight = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
        String photoMimeType = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));
        long photoAddTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[6]));
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.photoName = photoName;
        photoInfo.photoPath = photoPath;
        photoInfo.photoSize = photoSize;
        photoInfo.photoWidth = photoWidth;
        photoInfo.photoHeight = photoHeight;
        photoInfo.photoMimeType = photoMimeType;
        photoInfo.photoAddTime = photoAddTime;
        return photoInfo;
    }

    /*--------------- 以下查看相册 ---------------*/

    private SingleTypeAdapter<PhotoFolderInfo> mFolderAdapter;
    private FolderPopUpWindow                  mFolderPopUpWindow;
    /**
     * 当前文件夹的index
     */
    public ObservableInt           selectedIndex = new ObservableInt();
    /**
     * 当前文件夹的描述
     */
    public ObservableField<String> folderName    = new ObservableField<>("全部图片");

    /**
     * 弹出文件选框
     */
    public void createPopupFolder(RelativeLayout footerBar) {
        if (mFolderAdapter == null) {
            mFolderAdapter = new SingleTypeAdapter<>(getV(), R.layout.item_photo_folder);
            mFolderAdapter.setPresenter(this);
        }
        mFolderAdapter.set(photoFolderInfoList);
        if (mFolderPopUpWindow == null) {
            mFolderPopUpWindow = new FolderPopUpWindow(getV(), mFolderAdapter);
        }
        if (mFolderPopUpWindow.isShowing()) {
            mFolderPopUpWindow.dismiss();
        }
        mFolderPopUpWindow.setMargin(footerBar.getHeight());
        mFolderPopUpWindow.showAtLocation(footerBar, Gravity.CENTER, 0, 0);
    }

    /**
     * mFolderAdapter 点击事件
     */
    public void onItemClick(int position, PhotoFolderInfo photoFolderInfo) {
        selectedIndex.set(position);
        mFolderPopUpWindow.dismiss();
        getV().onPhotoLoaded(photoFolderInfo.photoList);
        folderName.set(photoFolderInfo.folderName);
    }

    @BindingAdapter({"marginBottom"})
    public static void marginBottom(View view, int dp) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.bottomMargin = UDimens.dip2pxInt(dp);
        view.setLayoutParams(params);
    }

    /*---------------  列表图片操作 ---------------*/

    /**
     * 选中的图片集合
     */
    public ObservableArrayList<PhotoInfo> selectionList = new ObservableArrayList<>();
    /**
     * 选中的图片集合大小
     */
    public ObservableInt                  preview       = new ObservableInt();

    /**
     * 列表checkbox点击
     */
    public void onItemCheckBoxChange(CompoundButton buttonView, boolean isChecked, PhotoInfo info, View mask) {
        if (isChecked) {
            if (!selectionList.contains(info)) {
                if (selectionList.size() == mPhotoPickerFinal.getSelectLimit()) {
                    buttonView.setChecked(false);
                    AlertToast.show("最多选择" + mPhotoPickerFinal.getSelectLimit() + "张图片");
                    return;
                }
                mask.setVisibility(View.VISIBLE);
                selectionList.add(info);
            }
        } else {
            if (selectionList.contains(info)) {
                selectionList.remove(info);
                mask.setVisibility(View.GONE);
            }
        }
        refreshFooterPreviewUI();
    }

    /**
     * 刷新footer选中图片的UI
     */
    void refreshFooterPreviewUI() {
        if (mPhotoPickerFinal.isMultiMode()) {
            preview.set(selectionList.size());
            getV().setRightText();
        }
    }

    /**
     * 图片的点击事件
     */
    public void onPhotoItemClick(PhotoInfo info, int position) {
        boolean showCamera = mPhotoPickerFinal.isShowCamera();
        //点击相机的位置
        if (showCamera && position == 0) {
            getV().getRxPermissions().requestEach(Manifest.permission.CAMERA)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            takePicture();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            AlertToast.show("权限被禁止，无法打开相机！"); //拒绝了权限
                        } else {
                            AlertToast.show("请到应用设置中开启权限！");//永久拒绝了权限
                        }
                    });
            return;
        }
        //之后点击的都是图片 多选的情况
        if (mPhotoPickerFinal.isMultiMode()) {
            //将当期相册的所有图片设置到单利中
            mPhotoPickerFinal.setPhotoList(mPhotoPickerFinal.isShowCamera()
                    ? photoFolderInfoList.get(selectedIndex.get()).photoList.subList(1, photoFolderInfoList.get(selectedIndex.get()).photoList.size())
                    : photoFolderInfoList.get(selectedIndex.get()).photoList);
            //进入预览界面 减去相机的位置
            startPreview(mPhotoPickerFinal.isShowCamera() ? position - 1 : position);
        } else {
            if (mPhotoPickerFinal.isCrop()) {
                //进入裁剪页面
                startCrop(info.photoPath);
            } else {
                selectionList.add(info);
                getV().submit();
            }
        }
    }

    /**
     * 拍照后的图片路径
     */
    private File scanPath;
    private File deletePath;
    private static final String SCAN_TYPE = "image/jpeg";
    private ProgressDialogFragment mDialogFragment;

    /**
     * 打开相机进行拍照
     */
    private void takePicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        scanPath = UFile.getFileByPath(USDCard.getSDCardPublicPath(Environment.DIRECTORY_PICTURES) + "IMAGE_" + UDate.date2String(UDate.getNowDate(), "yyyy_MM_dd_HH_mm_ss") + UImage.JPG);
        LogUtil.e(" takePicture path = " + scanPath);
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(scanPath);
        } else {
            uri = FileProvider.getUriForFile(getV(), UImage.FILE_PROVIDER_NAME, scanPath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        getV().startActivityForResult(intent, PhotoWallActivity.PHOTO_REQUEST_CODE);
    }

    /**
     * 相机选择 图片裁剪回调
     */
    void takePictureResult(File path) {
        //相机回调 单选 需要裁剪 进入裁剪页面
        if (path == null && !mPhotoPickerFinal.isMultiMode() && mPhotoPickerFinal.isCrop()) {
            startCrop(scanPath.getPath());
            deletePath = scanPath;
            return;
        }
        //删除掉相机拍摄进入裁剪之前的照片
        if (deletePath != null) {
            UFile.deleteFile(deletePath);
            deletePath = null;
        }
        //不等于null 裁剪回调
        if (path != null) {
            this.scanPath = path;
        }
        //扫描图片 通过cursor获取图片对象信息返回
        if (scanPath.isFile()) {
            showProgress();
            MediaScanner.scan(scanPath.getPath(), SCAN_TYPE, () ->
                    getV().pend(Util.safelyTask(() ->
                            imageDataSource(scanPath.getPath())
                    ))
            );
        }
    }

    /**
     * 回调后获取图片信息略有延迟,显示dialog蒙层
     */
    private void showProgress() {
        if (mDialogFragment == null) {
            mDialogFragment = new ProgressDialogFragment();
        }
        mDialogFragment.show(getV().getFragmentManager(), ProgressDialogFragment.TAG);
    }

    /**
     * 预览选中的图片
     */
    public void onPreview() {
        mPhotoPickerFinal.setPhotoList(selectionList);
        startPreview(0);
    }

    /**
     * 进入到预览图片界面
     */
    private void startPreview(int position) {
        Router.newIntent()
                .from(getV())
                .to(PhotoPreviewActivity.class)
                .putParcelableArrayList(PhotoValue.EXTRA_PHOTO_ITEMS, selectionList)
                .putInt(PhotoValue.EXTRA_SELECTED_PHOTO_POSITION, position)
                .requestCode(PhotoWallActivity.PREVIEW_REQUEST_CODE)
                .launch();
    }

    /**
     * 进入图片裁剪页面
     */
    private void startCrop(String photoPath) {
        Router.newIntent()
                .from(getV())
                .to(PhotoCropActivity.class)
                .putString(PhotoValue.EXTRA_URL, photoPath)
                .requestCode(PhotoWallActivity.CROP_REQUEST_CODE)
                .launch();
    }
}
