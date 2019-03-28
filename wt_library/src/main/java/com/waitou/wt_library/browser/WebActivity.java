package com.waitou.wt_library.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.waitou.wt_library.R;
import com.waitou.wt_library.base.BaseActivity;
import com.waitou.wt_library.databinding.ActivityWebBinding;
import com.waitou.wt_library.kit.UNetWork;
import com.to.aboomy.utils_lib.UString;

/**
 * Created by waitou on 17/3/17.
 * web页面
 */

public class WebActivity extends BaseActivity {

    public static final String PLATFORM_WEB_URL   = "url";
    public static final String PLATFORM_WEB_TITLE = "title";

    private ActivityWebBinding mWebBinding;

    private String mUrl, mTitleStr;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mWebBinding.error.setOnClickListener(v -> reload());
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(PLATFORM_WEB_URL);
        mTitleStr = intent.getStringExtra(PLATFORM_WEB_TITLE);

        /*--------------- 初始化标题 ---------------*/
        mWebBinding.toolbar.initializeHeader(UString.checkNotNull(mTitleStr, ""));
        /*--------------- 设置webView ---------------*/
        mWebBinding.web.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        mWebBinding.web.getSettings().setBuiltInZoomControls(false);//设置使支持缩放
        mWebBinding.web.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        mWebBinding.web.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebBinding.web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新窗口
        mWebBinding.web.getSettings().setDefaultTextEncodingName("UTF-8");//编码方式
        mWebBinding.web.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebBinding.web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //把所有内容放大webview等宽的一列中
        mWebBinding.web.getSettings().setSupportZoom(true);////支持缩放，默认为true。
        mWebBinding.web.getSettings().setBuiltInZoomControls(true);//设置内置的缩放控件，为false，该webview不可缩放
        mWebBinding.web.getSettings().setDisplayZoomControls(false);//隐藏原生的缩放控件
        mWebBinding.web.getSettings().setAppCacheEnabled(true); //开启webview缓存功能
        mWebBinding.web.getSettings().setAppCachePath(String.valueOf(getExternalCacheDir())); //sd卡下的缓存目录
        mWebBinding.web.getSettings().setCacheMode(UNetWork.isAvailable() ? WebSettings.LOAD_NO_CACHE : WebSettings.LOAD_CACHE_ELSE_NETWORK);//有网就从网络获取最新的数据，否则本地获取
        mWebBinding.web.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            //显示网页
            startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
        mWebBinding.web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mWebBinding.progress.setProgress(newProgress);
                mWebBinding.progress.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.equals("网页无法打开")) {
                    mWebBinding.error.setVisibility(View.VISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(mWebBinding.toolbar.getTitle())) {
                    mWebBinding.toolbar.setTitle(title);
                }
            }
        });
        mWebBinding.web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url); //在页面加载结束时调用
                if (!TextUtils.isEmpty(view.getTitle()) && !view.getTitle().equals("网页无法打开")) {
                    mWebBinding.error.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().getPath());
                return true; //在点击请求的是链接是才会调用
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error); //加载失败
                mWebBinding.error.setVisibility(View.VISIBLE);
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
        if (mWebBinding.web != null && mWebBinding.web.canGoBack()) {
            mWebBinding.web.goBack();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 加载URL
     */
    public void loadUrl(String url) {
        if (mWebBinding.web != null) {
            mWebBinding.web.loadUrl(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebBinding.web != null) {
            mWebBinding.web.onResume();
        }
    }

    /*---------------activity销毁WebView,先加载null内容，再移除，接着销毁WebView ---------------*/
    @Override
    protected void onDestroy() {
        if (mWebBinding.web != null) {
            mWebBinding.web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebBinding.web.clearHistory();
            ((ViewGroup) mWebBinding.web.getParent()).removeView(mWebBinding.web);
            mWebBinding.web.destroy();
        }
        super.onDestroy();
    }
}

