package cn.bertsir.zbar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import cn.bertsir.zbar.Qr.Symbol;
import cn.bertsir.zbar.camera.CameraPreview;
import cn.bertsir.zbar.camera.ScanCallback;

public abstract class QRActivity extends AppCompatActivity implements ScanCallback {

    protected boolean isImmersiveStatusBar;

    private boolean isPlaySound = true;

    private boolean       isOpen;
    private CameraPreview mCameraPreView;
    private SoundPool     mSoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            isImmersiveStatusBar = true;
        }
        setContentView(R.layout.qr_activity_qr);
        mCameraPreView = findViewById(R.id.camera_pre);
        if (getContentId() > 0) {
            LayoutInflater.from(this).inflate(getContentId(), findViewById(R.id.content_layout));
        }
        checkCameraPermission();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_SYSTEM);
        SoundPool.Builder soundPoolBuild = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attrBuilder.build());
        mSoundPool = soundPoolBuild.build();
        mSoundPool.load(this, R.raw.qrcode, 1);

        afterCreate(savedInstanceState);
    }

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract int getContentId();


    /**
     * 指定QrConfig常量 默认是二维码
     * 在页面初始化调用
     *
     * @param scanType 1二维码 2UPCA条形码 3全部类型 4用户指定类型
     */
    protected void setScanType(int scanType) {
        Symbol.scanType = scanType;
    }

    /**
     * 是否要开启提示音
     */
    protected void setPlaySound(boolean isPlaySound) {
        this.isPlaySound = isPlaySound;
    }

    /**
     * 是否开启手电筒
     */
    protected void setFlash(boolean isOpen) {
        mCameraPreView.setFlash(isOpen);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraPreView != null) {
            mCameraPreView.setFlash(false);
            mCameraPreView.stop();
        }
        if (mSoundPool != null) {
            mSoundPool.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraPreView != null) {
            mCameraPreView.stop();
        }
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
        if (isPlaySound) {
            mSoundPool.play(1, 1, 1, 0, 0, 1);
        }
        if (mCameraPreView != null) {
            mCameraPreView.setFlash(false);
        }
        if (BuildConfig.DEBUG) {
            Log.e("aa", "onScanResult = " + content);
        }
    }
}
