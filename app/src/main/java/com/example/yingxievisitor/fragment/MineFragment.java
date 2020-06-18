package com.example.yingxievisitor.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.AboutActivity;
import com.example.yingxievisitor.activity.ChangePassActivity;
import com.example.yingxievisitor.activity.FeedbackActivity;
import com.example.yingxievisitor.activity.LoginActivity;
import com.example.yingxievisitor.activity.WebActivity;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.bean.GMBean;
import com.example.yingxievisitor.utils.PackageUtils;
import com.example.yingxievisitor.utils.SPUtils;
import com.example.yingxievisitor.utils.ToastUtils;
import com.example.yingxievisitor.view.UpDateDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import constant.UiType;
import listener.OnInitUiListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvTitle, tv_userName;
    private ImageView imageBack;
    private LinearLayout ll_change_pass, ll_feedback, ll_check, ll_logout, ll_tologin,ll_about,ll_help;

    private UpDateDialog upDateDialog;

    private String userName = "";

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tvTitle = view.findViewById(R.id.tv_title);
        imageBack = view.findViewById(R.id.image_back);
        ll_change_pass = view.findViewById(R.id.ll_change_pass);
        ll_feedback = view.findViewById(R.id.ll_feedback);
        ll_check = view.findViewById(R.id.ll_check);
        ll_logout = view.findViewById(R.id.ll_logout);
        tv_userName = view.findViewById(R.id.tv_userName);
        ll_tologin = view.findViewById(R.id.ll_tologin);
        ll_about = view.findViewById(R.id.ll_about);
        ll_help = view.findViewById(R.id.ll_help);
        imageBack.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        userName = SPUtils.getString(mActivity, "login_user");
        if (SPUtils.getBoolean(mActivity, "loginStatus")) {
            ll_logout.setVisibility(View.VISIBLE);
            ll_feedback.setVisibility(View.VISIBLE);
            ll_change_pass.setVisibility(View.VISIBLE);
            tv_userName.setText(userName);
        } else {
            ll_logout.setVisibility(View.GONE);
            ll_feedback.setVisibility(View.GONE);
            ll_change_pass.setVisibility(View.GONE);
            tv_userName.setText("登录/注册");
        }
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);

        tvTitle.setText("我的");
        upDateDialog = new UpDateDialog(mActivity, R.style.dialog);

    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        ll_change_pass.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_check.setOnClickListener(this);
        ll_tologin.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_change_pass:
                startActivity(new Intent(mActivity, ChangePassActivity.class));
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(mActivity, FeedbackActivity.class));
                break;
            case R.id.ll_check:
                GetLastVersion();
                break;
            case R.id.ll_tologin:
                if (tv_userName.getText().equals("登录/注册")) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                } else {
                    ToastUtils.show("您已登录");
                }
                break;
            case R.id.ll_logout:
                logout(tv_userName.getText().toString());
                break;
            case R.id.ll_about:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.ll_help:
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("webUrl", "file:///android_asset/help.html");
                intent.putExtra("webTitle", "帮助中心");
                startActivity(intent);
                break;

        }
    }

    /**
     * 退出登录
     */
    private void logout(String userName) {
        EasyHttp.get(AppUrl.Logout)
                .params("username", userName)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.show("网络错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        SPUtils.putBoolean(mActivity, "loginStatus", false);
                        tv_userName.setText("登录/注册");
                        ll_logout.setVisibility(View.GONE);
                        ll_change_pass.setVisibility(View.GONE);
                        ll_feedback.setVisibility(View.GONE);
                        ToastUtils.show("退出成功");
                    }
                });
    }

    /**
     * 检查更新
     */

    private void GetLastVersion() {
        EasyHttp.post(AppUrl.GetLastVersion)
                .params("system", "client")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.show("网络错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        GMBean gmBean = JSON.parseObject(s, GMBean.class);
                        if (gmBean.isStatus()) {
                            double VersionName = Double.valueOf(PackageUtils.getVersionName(mActivity));
                            double code = Double.valueOf(gmBean.getData().toString());
                            if (VersionName < code) {
                                upDate(code);
                            }else {
                                ToastUtils.show("已经是最新版本");
                            }

                        }
                    }
                });
    }

    /**
     * 去更新
     */

    private void upDate(final Double code) {
        String apkUrl = AppUrl.BaseURLTest2+AppUrl.DownloadApk;
//        String apkUrl = "http://118.24.148.250:8080/yk/update_signed.apk";
        String updateTitle = "发现新版本";
        String updateContent = "发现新版本升级后体验更顺畅";
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.isShowNotification();
        updateConfig.setAlwaysShowDownLoadDialog(true);
        updateConfig.setNotifyImgRes(R.mipmap.ic_launcher);

        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.CUSTOM);
        uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);

        UpdateAppUtils.getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)
                .setOnInitUiListener(new OnInitUiListener() {
                    @Override
                    public void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig) {
                        TextView tv_code =view.findViewById(R.id.tv_code);
                        tv_code.setText("V"+code);
                    }
                })
                .update();

    }

}
