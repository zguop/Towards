package com.waitou.towards.model;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.to.aboomy.statusbar_lib.StatusBarUtil;
import com.to.aboomy.statusbar_lib.StatusBarView;
import com.to.aboomy.utils_lib.USize;
import com.waitou.towards.R;

import cn.bertsir.zbar.ScanActivity;

/**
 * auth aboom
 * date 2018/7/4
 */
public class QrScanActivity extends ScanActivity {

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        ViewGroup viewGroup = findViewById(R.id.title_parent);
        viewGroup.addView(new StatusBarView(this), 0);
    }

    @Override
    public void onScanResult(String content) {
        super.onScanResult(content);
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}
