package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yingxievisitor.MainActivity;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.EventBusVerifyBean;
import com.example.yingxievisitor.utils.SPUtils;
import com.example.yingxievisitor.view.VerifyDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 登录activity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_login; //登录
    private EditText et_user, et_password; //账号密码
    private TextView tv_user_null, tv_pass_null,tv_register,tv_forget_pass;
    private LinearLayout ll_user, ll_password;
    private VerifyDialog verifyDialog; //验证弹窗


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_user_null = (TextView) findViewById(R.id.tv_user_null);
        tv_pass_null = (TextView) findViewById(R.id.tv_pass_null);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget_pass = (TextView) findViewById(R.id.tv_forget_pass);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        ll_password = (LinearLayout) findViewById(R.id.ll_password);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        verifyDialog = new VerifyDialog(this, R.style.dialog,"login");

    }

    @Override
    protected void initListen() {
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forget_pass.setOnClickListener(this);

    }

    /**
     * 判断账密是否为空
     */
    private void toLogin() {
        if (et_user.getText().toString().equals("")) {
            tv_user_null.setVisibility(View.VISIBLE);
            ll_user.setBackgroundResource(R.drawable.shape_login_null_bg);
        } else if (et_password.getText().toString().equals("")) {
            tv_pass_null.setVisibility(View.VISIBLE);
            ll_password.setBackgroundResource(R.drawable.shape_login_null_bg);
        } else {
            verifyDialog.show();
            tv_user_null.setVisibility(View.GONE);
            tv_pass_null.setVisibility(View.GONE);
            ll_user.setBackgroundResource(R.drawable.shape_login_ll_bg);
            ll_password.setBackgroundResource(R.drawable.shape_login_ll_bg);
        }
    }

    /**
     * EventBus 接收参数，判断是否验证成功，再去提交登录
     * @param busVerifyBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVerify(EventBusVerifyBean busVerifyBean) {
        Log.e("fhxx","获取到"+busVerifyBean.toString());
        if (busVerifyBean.getType().equals("login")&&busVerifyBean.getVerify()){
            if (et_user.getText().toString().equals("123456")&&et_password.getText().toString().equals("1234")){
                SPUtils.putString(this,"login_user",et_user.getText().toString());
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }else {
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                toLogin();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
