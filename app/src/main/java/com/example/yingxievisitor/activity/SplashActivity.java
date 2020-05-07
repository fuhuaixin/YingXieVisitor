package com.example.yingxievisitor.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
        GetPermission();
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
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_know_more:

                break;
        }
    }


    /**
     * 获取定位权限
     */
    private void GetPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }, 200);
        } else {
//            mLocationClient.start();// 定位SDK
//            Toast.makeText(LoginActivity.this, "已开启定位权限", Toast.LENGTH_LONG).show();
        }
    }
}
