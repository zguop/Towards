package com.waitou.photo_library.event;

import com.waitou.photo_library.bean.PhotoInfo;

import java.util.List;

/**
 * Created by waitou on 17/4/13.
 * 回调的图片事件
 */

public class PhotoEvent {

    private List<PhotoInfo> selectionList;

    public PhotoEvent(List<PhotoInfo> selectionList) {
        this.selectionList = selectionList;
    }

    public List<PhotoInfo> getSelectionList() {
        return selectionList;
    }
}
