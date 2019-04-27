package com.waitou.three_library.share;

import android.app.Activity;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by waitou on 17/5/16.
 * 调用友盟分享
 */

public class UShare {

    public interface OnShareListener {
        void onShare(SHARE_MEDIA share_media);
    }

    public static void share(Activity activity, SHARE_MEDIA media, ShareInfo info, OnShareListener listener) {
        ShareAction shareAction = new ShareAction(activity).setPlatform(media);
        switch (info.type) {
            case ShareInfo.TEXT: //文本
                shareAction.withText(info.content);
                break;
            case ShareInfo.IMAGE: //图片
                shareAction.withMedia(new UMImage(activity, info.imageUrl));
                break;
            case ShareInfo.TEXT_AND_IMAGE://图片and文本
                shareAction.withText(info.content).withMedia(new UMImage(activity, info.imageUrl));
                break;
            case ShareInfo.WEB0:
                shareAction.withMedia(
                        new UMWeb(info.targetUrl, info.title, info.content,
                                ObjectUtils.isNotEmpty(info.imageUrl) ? new UMImage(activity, info.imageUrl) : null));
                break;
        }
        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                if (listener != null) {
                    listener.onShare(share_media);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtils.showShort(throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                ToastUtils.showShort("分享取消了...");
            }
        }).share();
    }
}
