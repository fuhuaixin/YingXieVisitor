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

import com.example.yingxievisitor.MainActivity;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.EventBusVerifyBean;
import com.example.yingxievisitor.utils.ToastUtils;
import com.example.yingxievisitor.view.VerifyDialog;

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
            }
        });
    }

    /**
     * 判是否输入有值
     */
    private void toRegister() {
        if (et_user.getText().toString().equals("")) {
            tv_user_null.setVisibility(View.VISIBLE);
            ll_user.setBackgroundResource(R.drawable.shape_login_null_bg);
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
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
}
