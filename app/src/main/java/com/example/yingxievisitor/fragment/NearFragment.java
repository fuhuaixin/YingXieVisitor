package com.example.yingxievisitor.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseFragment;

public class NearFragment extends BaseFragment {


    private ImageView imageBack;
    private TextView tvTitle;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        imageBack =view.findViewById(R.id.image_back);
        tvTitle = view.findViewById(R.id.tv_title);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("附近");
        imageBack.setVisibility(View.GONE);
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);

    }
}
