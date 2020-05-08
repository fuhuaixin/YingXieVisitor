package com.example.yingxievisitor.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;

public class WebActivity extends BaseActivity {

    private ImageView imageBack;
    private TextView tvTitle;
    private WebView webView;
    private String url,title;

    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        dialog();
        zLoadingDialog.show();
        webView = (WebView) findViewById(R.id.webView);
        imageBack = (ImageView) findViewById(R.id.image_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
//        tvTitle.setText("新闻");
        tvTitle.setText(getIntent().getStringExtra("webTitle"));

        url = getIntent().getStringExtra("webUrl");

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(url);

        stopDialog();
    }

    @Override
    protected void initListen() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void stopDialog(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    zLoadingDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
