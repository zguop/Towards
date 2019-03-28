package cn.bertsir.zbar;

import android.os.Bundle;
import android.widget.TextView;

import cn.bertsir.zbar.view.ScanView;

/**
 * auth aboom
 * date 2018/6/13
 */
public class ScanActivity extends QRActivity {

    private ScanView mScanView;
    private TextView mTitleView;

    @Override
    protected int getContentId() {
        return R.layout.qr_activity_scan;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        findViewById(R.id.qr_back).setOnClickListener(v -> finish());
        mScanView = findViewById(R.id.scan_view);
        mTitleView = findViewById(R.id.qr_title);
        mScanView.startScan();
    }

    protected void setTitle(String title) {
        mTitleView.setText(title);
    }

    protected void setScanTitle(String scanTitle) {
        mScanView.setScanTitle(scanTitle);
    }

    protected void setScanTips(String scanTips) {
        mScanView.setScanTips(scanTips);
    }

    protected void setScanViewType(int scanViewType, boolean isInvalidate) {
        mScanView.setScanType(scanViewType, isInvalidate);
    }
}
