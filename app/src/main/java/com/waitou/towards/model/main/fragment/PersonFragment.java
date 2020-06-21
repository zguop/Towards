package com.waitou.towards.model.main.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.waitou.wt_library.base.LazyFragment;

/**
 * Created by waitou on 16/12/23.
 */

public class PersonFragment extends LazyFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        TextView textView  = new TextView(getActivity());
        textView.setTextColor(Color.BLACK);
        textView.setText(getClass().getSimpleName());
        return textView;
    }
}
