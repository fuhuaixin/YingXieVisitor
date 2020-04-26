package com.example.yingxievisitor.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseFragment;

public class MineFragment extends BaseFragment {

    private TextView tvHome;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tvHome =view.findViewById(R.id.tvHome);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);

        tvHome.setText("这是MineFragment");
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);

    }
}
