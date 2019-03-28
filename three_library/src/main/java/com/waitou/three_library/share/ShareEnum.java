package com.waitou.three_library.share;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.waitou.three_library.R;

/**
 * Created by waitou on 17/5/16.
 */

public enum ShareEnum {

    WX(R.id.menu_wx, SHARE_MEDIA.WEIXIN),

    WX_CIRCLE(R.id.menu_wx_friend, SHARE_MEDIA.WEIXIN_CIRCLE),

    QQ(R.id.menu_qq, SHARE_MEDIA.QQ);

    private int         id;
    private SHARE_MEDIA media;

    ShareEnum(int id, SHARE_MEDIA media) {
        this.id = id;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public SHARE_MEDIA getMedia() {
        return media;
    }

    public static SHARE_MEDIA valueOf(int id) {
        if (id == R.id.menu_wx) {
            return WX.getMedia();
        }
        else if (id == R.id.menu_wx_friend) {
            return WX_CIRCLE.getMedia();
        }
        else {
            return QQ.getMedia();
        }
    }
}
