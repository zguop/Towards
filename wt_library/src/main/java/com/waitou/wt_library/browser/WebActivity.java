package com.waitou.wt_library.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.waitou.wt_library.R;
import com.waitou.wt_library.base.BaseActivity;
import com.waitou.wt_library.base.TitleBar;

/**
 * Created by waitou on 17/3/17.
 * web页面
 */

public class WebActivity extends BaseActivity {

    public static final String PLATFORM_WEB_URL   = "url";
    public static final String PLATFORM_WEB_TITLE = "title";

    private WebView mWeb;

    private String mUrl, mTitleStr;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        TextView webError = findViewById(R.id.error);

        webError.setOnClickListener(v -> reload());
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(PLATFORM_WEB_URL);
        mTitleStr = intent.getStringExtra(PLATFORM_WEB_TITLE);
        TitleBar toolbar = findViewById(R.id.toolbar);

        /*--------------- 初始化标题 ---------------*/
        TextView toolTitle = toolbar.initializeHeader(StringUtils.null2Length0(mTitleStr));
        /*--------------- 设置webView ---------------*/
        mWeb = findViewById(R.id.web);
        ProgressBar progress = findViewById(R.id.progress);
        mWeb.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        mWeb.getSettings().setBuiltInZoomControls(false);//设置使支持缩放
        mWeb.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        mWeb.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新窗口
        mWeb.getSettings().setDefaultTextEncodingName("UTF-8");//编码方式
        mWeb.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //把所有内容放大webview等宽的一列中
        mWeb.getSettings().setSupportZoom(true);////支持缩放，默认为true。
        mWeb.getSettings().setBuiltInZoomControls(true);//设置内置的缩放控件，为false，该webview不可缩放
        mWeb.getSettings().setDisplayZoomControls(false);//隐藏原生的缩放控件
        mWeb.getSettings().setAppCacheEnabled(true); //开启webview缓存功能
        mWeb.getSettings().setAppCachePath(String.valueOf(getExternalCacheDir())); //sd卡下的缓存目录
        mWeb.getSettings().setCacheMode(NetworkUtils.isConnected() ? WebSettings.LOAD_NO_CACHE : WebSettings.LOAD_CACHE_ELSE_NETWORK);//有网就从网络获取最新的数据，否则本地获取
        mWeb.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            //显示网页
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
        mWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progress.setProgress(newProgress);
                progress.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.equals("网页无法打开")) {
                    webError.setVisibility(View.VISIBLE);
                    return;
                }
                toolTitle.setText(title);
            }
        });
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url); //在页面加载结束时调用
                if (!TextUtils.isEmpty(view.getTitle()) && !view.getTitle().equals("网页无法打开")) {
                    webError.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error); //加载失败
                webError.setVisibility(View.VISIBLE);
            }
        });
        /*--------------- 加载页面 ---------------*/
        reload();
    }

    private void reload() {
        loadUrl(TextUtils.isEmpty(mUrl) ? "about:blank" : mUrl);
    }

    @Override
    public void onBackPressed() {
        if (mWeb != null && mWeb.canGoBack()) {
            mWeb.goBack();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 加载URL
     */
    public void loadUrl(String url) {
        if (mWeb != null) {
            mWeb.loadUrl(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWeb != null) {
            mWeb.onResume();
        }
    }

    /*---------------activity销毁WebView,先加载null内容，再移除，接着销毁WebView ---------------*/
    @Override
    protected void onDestroy() {
        if (mWeb != null) {
            mWeb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWeb.clearHistory();
            ((ViewGroup) mWeb.getParent()).removeView(mWeb);
            mWeb.destroy();
        }
        super.onDestroy();
    }
}

