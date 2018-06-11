package com.to.aboomy.zxing_lib;

import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * auth aboom
 * date 2018/5/23
 */
public class CameraPreView extends FrameLayout implements SurfaceHolder.Callback {

    private CameraManager      mCameraManager;
    private SurfaceView        mSurfaceView;
    private CameraScanAnalysis mPreviewCallback;

    public CameraPreView(@NonNull Context context) {
        this(context, null);
    }

    public CameraPreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCameraManager = new CameraManager(context);
        mPreviewCallback = new CameraScanAnalysis();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }
        mCameraManager.stopPreview();
        startCameraPreview(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean start() {
        try {
            mCameraManager.openDriver();
        } catch (Exception e) {
            Log.e("aa", "打开摄像头权限被拒绝，具体异常信息：" + e.getMessage());
            return false;
        }
        if (mSurfaceView == null) {
            mSurfaceView = new SurfaceView(getContext());
            addView(mSurfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            SurfaceHolder holder = mSurfaceView.getHolder();
            holder.addCallback(this);
        }
        startCameraPreview(mSurfaceView.getHolder());
        return true;
    }

    private void startCameraPreview(SurfaceHolder holder) {
        try {
            mCameraManager.startPreview(holder, null);
            mCameraManager.autoFocus(mFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Camera.AutoFocusCallback mFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            postDelayed(mAutoFocusTask, 1000);
        }
    };

    private Runnable mAutoFocusTask = new Runnable() {
        public void run() {
            mCameraManager.autoFocus(mFocusCallback);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    /**
     * Camera stop preview.
     */
    public void stop() {
        removeCallbacks(mAutoFocusTask);
        mPreviewCallback.onStop();
        mCameraManager.stopPreview();
        mCameraManager.closeDriver();
    }
}
