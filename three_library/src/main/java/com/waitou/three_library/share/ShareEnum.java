package com.waitou.three_library.share;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.waitou.three_library.R;

/**
 * Created by waitou on 17/5/16.
 */

public enum ShareEnum {

    WX("微信", R.drawable.svg_ic_wx, SHARE_MEDIA.WEIXIN),

    WX_CIRCLE("朋友圈", R.drawable.svg_ic_wx_friend, SHARE_MEDIA.WEIXIN_CIRCLE),

    QQ("QQ", R.drawable.svg_ic_qq, SHARE_MEDIA.QQ);

    private int         resId;
    private String      name;
    private SHARE_MEDIA media;

    ShareEnum(String name, int resId, SHARE_MEDIA media) {
        this.name = name;
        this.resId = resId;
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    public SHARE_MEDIA getMedia() {
        return media;
    }

    public static SHARE_MEDIA valueOf(int id) {
        if (id == R.id.menu_wx) {
            return WX.getMedia();
        } else if (id == R.id.menu_wx_friend) {
            return WX_CIRCLE.getMedia();
        } else {
            return QQ.getMedia();
        }
    }
}
