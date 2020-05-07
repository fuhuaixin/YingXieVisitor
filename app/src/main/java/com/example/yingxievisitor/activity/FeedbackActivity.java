package com.example.yingxievisitor.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.utils.ToastUtils;

/**
 * 意见反馈界面
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle,tv_commit,tv_length;
    private ImageView imageBack;
    private EditText et_message;


    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_length = (TextView) findViewById(R.id.tv_length);
        imageBack = (ImageView) findViewById(R.id.image_back);
        et_message = (EditText) findViewById(R.id.et_message);
    }

    @Override
    protected void initData() {
        tvTitle.setText("意见反馈");
        tv_commit.setClickable(false);
    }

    @Override
    protected void initListen() {
        imageBack.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_length.setText(s.toString().length()+"/200");
                if (s.toString().length()>0){
                    tv_commit.setClickable(true);
                    tv_commit.setBackgroundResource(R.drawable.shape_commit_sel);
                }else {
                    tv_commit.setClickable(false);
                    tv_commit.setBackgroundResource(R.drawable.shape_commit_unsel);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_commit:
                ToastUtils.show("提交成功");
                break;
        }
    }
}
