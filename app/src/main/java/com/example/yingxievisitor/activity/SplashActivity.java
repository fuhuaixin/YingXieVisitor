package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.yingxievisitor.MainActivity;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;

/**
 * 闪屏页
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_jump;
    private TextView btn_login;
    private TextView tv_know_more;

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        tv_jump = (TextView) findViewById(R.id.tv_jump);
        btn_login = (TextView) findViewById(R.id.btn_login);
        tv_know_more = (TextView) findViewById(R.id.tv_know_more);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListen() {
        tv_jump.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_know_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jump:
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.btn_login:
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                break;
            case R.id.tv_know_more:

                break;
        }
    }
}
