package com.waitou.wt_library.adapter;

import android.view.View;

import com.billy.android.loading.Gloading;
import com.waitou.wt_library.R;

import cn.droidlover.xstatecontroller.XStateController;

/**
 * auth aboom
 * date 2019/4/6
 */
public class GlobalAdapter implements Gloading.Adapter {
    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        XStateController stateController = null;
        if (convertView instanceof XStateController) {
            stateController = (XStateController) convertView;
        }
        if (stateController == null) {
            stateController = (XStateController) holder.getWrapper().getChildAt(0);
        }
        int state = convertState(holder, stateController, status);
        stateController.setDisplayState(state);
        return stateController;
    }


    private int convertState(Gloading.Holder holder, XStateController stateController, int status) {
        int xState = 0;
        if (Gloading.STATUS_LOADING == status) {
            xState = XStateController.STATE_LOADING;
        } else if (Gloading.STATUS_LOAD_SUCCESS == status) {
            xState = XStateController.STATE_CONTENT;
        } else if (Gloading.STATUS_LOAD_FAILED == status) {
            xState = XStateController.STATE_ERROR;
            stateController.getErrorView().findViewById(R.id.error).setOnClickListener(v -> holder.getRetryTask().run());
        } else if (Gloading.STATUS_EMPTY_DATA == status) {
            xState = XStateController.STATE_EMPTY;
        }
        return xState;
    }
}
