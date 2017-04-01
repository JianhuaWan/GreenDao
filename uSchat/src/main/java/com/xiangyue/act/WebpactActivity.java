package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.util.SysApplication;

/**
 * 用户使用协议
 *
 * @author Administrator
 */
public class WebpactActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.pact_web);
        webView = (WebView) findViewById(R.id.webbyusers);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);// 设置使支持缩放
        webView.loadUrl("file:///android_asset/userRead.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;// true表示此事件在此处被处理，不需要再广播
            }

            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                dismissLoadDialog();
                super.onPageFinished(view, url);
            }
        });
    }

    public void back(View view) {
        finish();
    }

}
