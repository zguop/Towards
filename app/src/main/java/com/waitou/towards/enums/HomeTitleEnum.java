package com.waitou.towards.enums;

import com.waitou.towards.R;

/**
 * Created by waitou on 17/2/27.
 */

public enum HomeTitleEnum {

    icon_recommended(0, R.drawable.icon_recommended),
    icon_cargo(1, R.drawable.icon_cargo),
    icon_android(2, R.drawable.icon_android);

    private int position;
    private int drawableId;

    HomeTitleEnum(int position, int drawableId) {
        this.position = position;
        this.drawableId = drawableId;
    }

    public static int valueOf(int position) {
        switch (position) {
            case 1:
                return icon_cargo.drawableId;
            case 2:
                return icon_android.drawableId;
            default:
                return icon_recommended.drawableId;
        }
    }
}
