package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    private ImageView image_back;
    private TextView tv_title;
    private LinearLayout ll_agreement, ll_norm;

    @Override
    protected int initLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_agreement = (LinearLayout) findViewById(R.id.ll_agreement);
        ll_norm = (LinearLayout) findViewById(R.id.ll_norm);

    }

    @Override
    protected void initData() {
        tv_title.setText("关于");
    }

    @Override
    protected void initListen() {
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWeb("用户协议", "file:///android_asset/xieyi.html");
            }
        });
        ll_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWeb("社区规范", "file:///android_asset/guifan.html");
            }
        });
    }

    /**
     * 去新闻详情页
     */
    private void toWeb(String title, String url) {
        Intent intent = new Intent(AboutActivity.this, WebActivity.class);
        intent.putExtra("webUrl", url);
        intent.putExtra("webTitle", title);
        startActivity(intent);
    }
}
