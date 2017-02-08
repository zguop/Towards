package com.waitou.towards.model.event;

import com.waitou.towards.bean.ThemeInfo;

/**
 * Created by waitou on 17/1/11.
 */

public class DrawerToggleEvent {

    private ThemeInfo info;

    public DrawerToggleEvent(ThemeInfo info) {
        this.info = info;

    }

    public ThemeInfo getInfo() {
        return info;
    }
}
