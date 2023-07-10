package com.example.netease.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netease.R;
import com.example.netease.splash.bean.Action;

public class WebViewActivity extends AppCompatActivity {

    public static final String ACTION_URL = "action_url";
    WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebview = findViewById(R.id.webview);

        Action action = (Action) getIntent().getSerializableExtra(ACTION_URL);
        String url = action.getUrl();
        Log.e("it520com", "webview_url=" + url);
        mWebview.loadUrl(url);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mWebview.loadUrl(url);
                return true;
            }
        });

        mWebview.getSettings().getJavaScriptEnabled();

    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
            return;
        }
        gotoMain();
        finish();
    }

    public void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
