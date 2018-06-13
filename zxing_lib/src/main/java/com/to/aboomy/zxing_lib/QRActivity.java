package com.to.aboomy.zxing_lib;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * auth aboom
 * date 2018/5/23
 */
public class QRActivity extends AppCompatActivity implements ScanCallback {

    private CameraPreView mCameraPreView;
    private boolean       isOpen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_qr);
        mCameraPreView = findViewById(R.id.camera_pre);
        checkCameraPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOpen && mCameraPreView != null) {
            mCameraPreView.setScanCallback(this);
            mCameraPreView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraPreView != null) {
            mCameraPreView.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 相机权限监测
     */
    private void checkCameraPermission() {
        int selfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (selfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        } else {
            isOpen = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isOpen = true;
            } else {
                Toast.makeText(this, "摄像头权限被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onScanResult(String content) {

    }
}
