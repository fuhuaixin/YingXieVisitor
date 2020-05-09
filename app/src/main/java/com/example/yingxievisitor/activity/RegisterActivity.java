package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.yingxievisitor.MainActivity;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.EventBusVerifyBean;
import com.example.yingxievisitor.bean.GMBean;
import com.example.yingxievisitor.utils.SPUtils;
import com.example.yingxievisitor.utils.ToastUtils;
import com.example.yingxievisitor.view.VerifyDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_user, ll_password, ll_password_again;

    private EditText et_user, et_password, et_password_again;

    private TextView tv_user_null, tv_pass_null, tv_pass_again_null;
    private ImageView image_clean_user;
    private Button btn_register;
    private VerifyDialog verifyDialog;


    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ll_password = (LinearLayout) findViewById(R.id.ll_password);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        ll_password_again = (LinearLayout) findViewById(R.id.ll_password_again);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        tv_user_null = (TextView) findViewById(R.id.tv_user_null);
        tv_pass_null = (TextView) findViewById(R.id.tv_pass_null);
        tv_pass_again_null = (TextView) findViewById(R.id.tv_pass_again_null);
        btn_register = (Button) findViewById(R.id.btn_register);
        image_clean_user = (ImageView) findViewById(R.id.image_clean_user);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        verifyDialog = new VerifyDialog(this, R.style.dialog, "register");

    }

    @Override
    protected void initListen() {
        btn_register.setOnClickListener(this);
        image_clean_user.setOnClickListener(this);

        et_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    image_clean_user.setVisibility(View.VISIBLE);
                } else {
                    image_clean_user.setVisibility(View.GONE);
                }
                if (s.toString().length() ==11) {
                    if (et_user.getText().toString().matches(num)){
                        CheckExist(s.toString());
                    }else {
                        ToastUtils.show("请输入正确的手机号");
                    }
                }
            }
        });
    }

    /**
     * 判是否输入有值
     */
    String num = "[1][356789]\\d{9}";

    private void toRegister() {
        if (et_user.getText().toString().equals("")) {
            tv_user_null.setVisibility(View.VISIBLE);
            ll_user.setBackgroundResource(R.drawable.shape_login_null_bg);
        } else if (!et_user.getText().toString().matches(num)) {
            ToastUtils.show("请输入正确得手机号");
        } else if (et_password.getText().toString().equals("")) {
            tv_pass_null.setVisibility(View.VISIBLE);
            ll_password.setBackgroundResource(R.drawable.shape_login_null_bg);
        } else if (et_password_again.getText().toString().equals("")) {
            tv_pass_again_null.setVisibility(View.VISIBLE);
            ll_password_again.setBackgroundResource(R.drawable.shape_login_null_bg);
        } else {
            tv_user_null.setVisibility(View.GONE);
            tv_pass_null.setVisibility(View.GONE);
            tv_pass_again_null.setVisibility(View.GONE);
            ll_user.setBackgroundResource(R.drawable.shape_login_ll_bg);
            ll_password.setBackgroundResource(R.drawable.shape_login_ll_bg);
            ll_password_again.setBackgroundResource(R.drawable.shape_login_ll_bg);
            if (et_password.getText().toString().equals(et_password_again.getText().toString())) {
                verifyDialog.show();
            } else {
                ToastUtils.show("两次密码不一致");
            }
        }
    }

    /**
     * EventBus 接收参数，判断是否验证成功，再去提交注册
     *
     * @param eventBusVerifyBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVerify(EventBusVerifyBean eventBusVerifyBean) {
        Log.e("fhxx", "获取到" + eventBusVerifyBean.toString());
        if (eventBusVerifyBean.getType().equals("register") && eventBusVerifyBean.getVerify()) {
            Register(et_user.getText().toString(), et_password.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                toRegister();
                break;
            case R.id.image_clean_user:
                et_user.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 检查是否注册
     */
    private void CheckExist(String userName) {
        EasyHttp.get(AppUrl.CheckExist)
                .params("username", userName)
                .syncRequest(false)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.show("网络错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        GMBean gmBean = JSON.parseObject(s, GMBean.class);
                        if (!gmBean.isStatus()) {
                            ToastUtils.show(gmBean.getMessage());
                        } else {
                            ToastUtils.show("该账号可以使用");
                        }
                    }
                });
    }

    /**
     * 注册
     */

    private void Register(String userName, String passWord) {
        EasyHttp.get(AppUrl.Register)
                .params("username", userName)
                .params("password", passWord)
                .syncRequest(false)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.show("网络错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        GMBean gmBean = JSON.parseObject(s, GMBean.class);
                        if (gmBean.isStatus()) {
                            ToastUtils.show("注册成功");
                            SPUtils.putString(RegisterActivity.this, "login_user", et_user.getText().toString());

                            finish();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            ToastUtils.show("注册失败");
                        }



                    }
                });
    }

}
