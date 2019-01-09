package com.tea.teatool.webshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tea.teatool.R;

public class WebShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_shop);
        WebView mWebView = findViewById(R.id.wv);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
        //设置不显示缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //去掉滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
        //webview推荐使用的窗口
        mWebView.getSettings().setUseWideViewPort(true);
        //webview加载的页面的模式
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        //设置响应js 的Alert()函数
        mWebView.setWebChromeClient(new WebChromeClient() {

        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            //点击页面中链接，继续在当前相应
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            //页面加载完成，进度消失
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            //页面开始加载，进度显示
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //忽略SSL证书错误，继续加载页面
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受证书
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }
        });

        mWebView.loadUrl("file:///android_asset/shopweb/index.html");
    }
}
