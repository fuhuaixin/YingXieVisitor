package com.example.yingxievisitor.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseFragment;

public class MineFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvTitle;
    private ImageView imageBack;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tvTitle =view.findViewById(R.id.tv_title);
        imageBack =view.findViewById(R.id.image_back);
        imageBack.setVisibility(View.GONE);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("我的");
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){



        }
    }
}
