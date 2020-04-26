package com.example.yingxievisitor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseFragment;

public class HomeFragment extends BaseFragment {

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

        tvHome.setText("这是HomeFragment");
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);

    }
}
