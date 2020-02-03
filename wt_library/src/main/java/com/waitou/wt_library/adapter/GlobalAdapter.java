package com.waitou.wt_library.adapter;

import android.view.View;

import com.billy.android.loading.Gloading;
import com.waitou.widget_lib.StateController;
import com.waitou.wt_library.R;

/**
 * auth aboom
 * date 2019/4/6
 */
public class GlobalAdapter implements Gloading.Adapter {
    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        StateController stateController = null;
        if (convertView instanceof StateController) {
            stateController = (StateController) convertView;
        }
        if (stateController == null) {
            stateController = (StateController) holder.getWrapper().getChildAt(0);
        }
        switch (status) {
            case Gloading.STATUS_LOADING:
                stateController.showLoading();
                break;
            case Gloading.STATUS_EMPTY_DATA:
                stateController.showEmpty();
                break;
            case Gloading.STATUS_LOAD_FAILED:
                stateController.showError();
                stateController.getErrorView().findViewById(R.id.error_retry_view).setOnClickListener(v -> {
                    Runnable retryTask = holder.getRetryTask();
                    if (retryTask != null) {
                        retryTask.run();
                    }
                });
                break;
            case Gloading.STATUS_LOAD_SUCCESS:
                stateController.showContent();
                break;
            default:
                break;
        }
        return stateController;
    }
}
