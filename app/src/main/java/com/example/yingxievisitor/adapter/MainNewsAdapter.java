package com.example.yingxievisitor.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.MainNewsBean;

import java.util.List;

public class MainNewsAdapter extends BaseQuickAdapter<MainNewsBean, BaseViewHolder> {

    public MainNewsAdapter(int layoutResId, @Nullable List<MainNewsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainNewsBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_time,item.getTime())
                .setText(R.id.tv_message,item.getMessage());

        helper.addOnClickListener(R.id.ll_item);
    }
}
