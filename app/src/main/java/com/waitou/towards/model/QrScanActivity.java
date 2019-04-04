package com.waitou.towards.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.to.aboomy.statusbar_lib.StatusBarView;
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
        if (RegexUtils.isURL(content)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(content);
            intent.setData(content_url);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            ToastUtils.showShort(content);
        }
    }
}
