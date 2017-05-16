package com.waitou.three_library.share;

import android.app.Activity;

import com.cocosw.bottomsheet.BottomSheet;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.waitou.three_library.R;
import com.waitou.wt_library.kit.AlertToast;

import java.util.function.Consumer;

/**
 * Created by waitou on 17/5/16.
 * 调用友盟分享
 */

public class UShare {

    public static void share(Activity activity, ShareInfo info, Consumer<SHARE_MEDIA> action) {
        new BottomSheet.Builder(activity, R.style.BottomSheet_Dialog)
                .title("分享到").sheet(R.menu.menu_share_bottom_sheet)
                .listener((dialog, which) -> share(activity, ShareEnum.valueOf(which), info, new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                action.accept(share_media);
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                AlertToast.show(throwable.getMessage());
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                AlertToast.show("分享取消了...");
                            }
                        })
                ).grid().show();
    }


    private static void share(Activity activity, SHARE_MEDIA media, ShareInfo info, UMShareListener listener) {
        new ShareAction(activity).setPlatform(media)
                .withText(info.content)
                .withMedia(new UMImage(activity, info.imageUrl))
                .setCallback(listener)
                .share();
    }
}
