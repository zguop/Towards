package com.waitou.towards.model.event;

import com.waitou.towards.bean.ThemeInfo;

/**
 * Created by waitou on 17/1/11.
 * 主题更改事件
 */

public class ThemeEvent {

    private ThemeInfo info;

    public ThemeEvent(ThemeInfo info) {
        this.info = info;
    }

    public ThemeInfo getInfo() {
        return info;
    }
}
