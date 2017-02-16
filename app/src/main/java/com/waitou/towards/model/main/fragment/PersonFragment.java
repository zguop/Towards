package com.waitou.towards.model.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by waitou on 16/12/23.
 */

public class PersonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView  = new TextView(getActivity());
        textView.setTextColor(Color.BLACK);
        textView.setText(getClass().getSimpleName());
        return textView;
    }
}
