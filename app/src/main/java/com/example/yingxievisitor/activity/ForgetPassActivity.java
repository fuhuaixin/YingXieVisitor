package com.example.yingxievisitor.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.EventBusVerifyBean;
import com.example.yingxievisitor.utils.SPUtils;
import com.example.yingxievisitor.utils.ToastUtils;
import com.example.yingxievisitor.view.VerifyDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * 忘记密码
 */
public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageBack, image_clean_user;
    private TextView tvTitle, tv_user;
    private EditText et_password, et_password_again;
    private Button btn_commit;
    private VerifyDialog verifyDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_forget_pass;
    }

    @Override
    protected void initView() {
        imageBack = (ImageView) findViewById(R.id.image_back);
        image_clean_user = (ImageView) findViewById(R.id.image_clean_user);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_user = (TextView) findViewById(R.id.tv_user);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        btn_commit = (Button) findViewById(R.id.btn_commit);

    }

    @Override
    protected void initData() {
        tvTitle.setText("忘记密码");
        EventBus.getDefault().register(this);
        verifyDialog = new VerifyDialog(this, R.style.dialog, "forget");


        tv_user.setText( SPUtils.getString(this,"login_user"));

    }

    @Override
    protected void initListen() {
        imageBack.setOnClickListener(this);
        image_clean_user.setOnClickListener(this);
        btn_commit.setOnClickListener(this);

      /*  et_user.addTextChangedListener(new TextWatcher() {
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
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_clean_user:

                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {
         if (et_password.getText().toString().equals("")) {
            ToastUtils.show("请输入密码");
        } else if (et_password_again.getText().toString().equals("")) {
            ToastUtils.show("请确认密码");
        } else {
            if (et_password.getText().toString().equals(et_password_again.getText().toString())) {
                verifyDialog.show();
            } else {
                ToastUtils.show("两次输入密码不一致");
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commitPass(EventBusVerifyBean busVerifyBean) {
        if (busVerifyBean.getType().equals("forget") && busVerifyBean.getVerify()) {
            ToastUtils.show("修改成功，请登录");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
