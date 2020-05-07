package com.example.yingxievisitor.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.ChangePassActivity;
import com.example.yingxievisitor.activity.FeedbackActivity;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.view.UpDateDialog;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView imageBack;
    private LinearLayout ll_change_pass,ll_feedback,ll_check;

    private UpDateDialog upDateDialog;
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
        imageBack.setVisibility(View.GONE);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("我的");
        upDateDialog =new UpDateDialog(mActivity,R.style.dialog);
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        ll_change_pass.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_check.setOnClickListener(this);
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
                upDateDialog.show();
                break;

        }
    }
}
