package com.example.yingxievisitor.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.utils.ToastUtils;

/**
 * 修改密码
 */
public class ChangePassActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle,tv_old_tip,tv_new_tip;
    private ImageView imageBack;
    private EditText et_old_pass,et_new_pass,et_new_pass_again;
    private Button btn_change;
    private ImageView image_old,image_new,image_new_again;
    private Dialog mDialog;
    @Override
    protected int initLayout() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_old_tip = (TextView) findViewById(R.id.tv_old_tip);
        tv_new_tip = (TextView) findViewById(R.id.tv_new_tip);
        imageBack = (ImageView) findViewById(R.id.image_back);
        et_old_pass = (EditText) findViewById(R.id.et_old_pass);
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_new_pass_again = (EditText) findViewById(R.id.et_new_pass_again);
        btn_change = (Button) findViewById(R.id.btn_change);
        image_old = (ImageView) findViewById(R.id.image_old);
        image_new = (ImageView) findViewById(R.id.image_new);
        image_new_again = (ImageView) findViewById(R.id.image_new_again);
        mDialog =new dialogs(this,R.style.dialog);
    }

    @Override
    protected void initData() {
        tvTitle.setText("修改密码");
    }

    @Override
    protected void initListen() {
        imageBack.setOnClickListener(this);
        btn_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.image_back:
                finish();
                break;
            case R.id.btn_change:
                tv_new_tip.setVisibility(View.GONE);
                tv_old_tip.setVisibility(View.GONE);
                if (et_old_pass.getText().toString().equals("")){
                    tv_old_tip.setVisibility(View.VISIBLE);
                    tv_old_tip.setText("请输入密码");
                    return;
                }
                if (et_new_pass.getText().toString().equals("")){
                    tv_new_tip.setVisibility(View.VISIBLE);
                    tv_new_tip.setText("请输入新密码");
                    return;
                }
                if (et_new_pass_again.getText().toString().equals("")){
                    tv_new_tip.setVisibility(View.VISIBLE);
                    tv_new_tip.setText("请再次输入新密码");
                    return;
                }
                if (!et_new_pass.getText().toString().equals(et_new_pass_again.getText().toString())){
                    tv_new_tip.setVisibility(View.VISIBLE);
                    tv_new_tip.setText("两次输入密码不一致");
                    return;
                }
                mDialog.show();

                break;
        }
    }


    /**
     * 修改成功弹窗
     */
    private class dialogs extends Dialog{

        private TextView tv_to_login;
        public dialogs(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_tip_change);

            tv_to_login =findViewById(R.id.tv_to_login);
            tv_to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    startActivity(new Intent(ChangePassActivity.this,LoginActivity.class));
                    finish();
                }
            });
        }
    }
}
