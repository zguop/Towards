package com.waitou.photo_library.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.waitou.photo_library.R;
import com.waitou.wt_library.theme.ThemeUtils;

/**
 * Created by waitou on 17/4/27.
 * loading
 */

public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = ProgressDialogFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(ThemeUtils.getThemeAttrColor(getActivity(), R.attr.colorPrimary), PorterDuff.Mode.SRC_IN);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            // Disable Back key and Search key
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                case KeyEvent.KEYCODE_SEARCH:
                    return true;
                default:
                    return false;
            }
        });
        return dialog;
    }
}
