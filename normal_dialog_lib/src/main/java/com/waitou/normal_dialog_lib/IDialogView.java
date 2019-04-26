package com.waitou.normal_dialog_lib;

import android.content.Context;
import android.view.View;

/**
 * auth aboom
 * date 2019-04-25
 */
public interface IDialogView {
    default View getContentView(Context activity) {
        return new View(activity);
    }
}
