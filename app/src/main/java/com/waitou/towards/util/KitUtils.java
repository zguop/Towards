package com.waitou.towards.util;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.UriUtils;
import com.blankj.utilcode.util.Utils;
import com.waitou.net_library.http.AsyncOkHttpClient;
import com.waitou.wt_library.action.Action1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * auth aboom
 * date 2019/3/30
 */
public class KitUtils {

    /**
     * 更改view的的背景 带动画
     *
     * @param view   需要更改的view
     * @param bitmap 图片
     */
    public static void startSwitchBackgroundAnim(View view, Bitmap bitmap) {
        Drawable oldDrawable = view.getBackground();
        Drawable oldBitmapDrawable;
        TransitionDrawable oldTransitionDrawable = null;
        if (oldDrawable instanceof TransitionDrawable) {
            oldTransitionDrawable = (TransitionDrawable) oldDrawable;
            oldBitmapDrawable = oldTransitionDrawable.findDrawableByLayerId(oldTransitionDrawable.getId(1));
        } else if (oldDrawable instanceof BitmapDrawable) {
            oldBitmapDrawable = oldDrawable;
        } else {
            oldBitmapDrawable = new ColorDrawable(0xffc2c2c2);
        }
        if (oldTransitionDrawable == null) {
            oldTransitionDrawable = new TransitionDrawable(new Drawable[]{oldBitmapDrawable, new BitmapDrawable(view.getResources(), bitmap)});
            oldTransitionDrawable.setId(0, 0);
            oldTransitionDrawable.setId(1, 1);
            oldTransitionDrawable.setCrossFadeEnabled(true);
            view.setBackground(oldTransitionDrawable);
        } else {
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(0), oldBitmapDrawable);
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(1), new BitmapDrawable(view.getResources(), bitmap));
        }
        oldTransitionDrawable.startTransition(500);
    }

    /**
     * 将bitmap保存到系统图库
     */
    public static void saveImageToGallery(File imgFile) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, imgFile.getAbsolutePath());
        Utils.getApp().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Utils.getApp().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, UriUtils.file2Uri(imgFile)));
    }

    /**
     * 下载
     *
     * @param url            下载路径
     * @param savePath       保存位置
     * @param filePathAction 下载成功回调
     */
    public static void downLoaderImg(String url, String savePath, Action1<String> filePathAction) {
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<String>() {
            @Nullable
            @Override
            public String doInBackground() throws Throwable {
                String fileExtension = FileUtils.getFileExtension(url);
                if (TextUtils.isEmpty(fileExtension)) {
                    fileExtension = "jpg";
                }
                String imageCacheSavePath = savePath + File.separator + "IMAGE_" + System.currentTimeMillis() + "." + fileExtension;
                Request request = new Request.Builder().url(url).build();
                Response response = AsyncOkHttpClient.getOkHttpClient().newCall(request).execute();
                ResponseBody body = response.body();
                if (body != null) {
                    InputStream inputStream = body.byteStream();
                    boolean writeFile = FileIOUtils.writeFileFromIS(imageCacheSavePath, inputStream);
                    if (!writeFile) {
                        throw new IOException("permission denied [READ_EXTERNAL_STORAGE]");
                    }
                }
                return imageCacheSavePath;
            }

            @Override
            public void onSuccess(@Nullable String result) {
                if (filePathAction != null)
                    filePathAction.call(result);
            }
        });
    }


    public static DateFormat getDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }

    /**
     * 时间是否大于选择的时间
     *
     * @param hour   时
     * @param minute 分
     */
    public static boolean isRightTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        return h > hour || (h == hour && m >= minute);
    }
}
