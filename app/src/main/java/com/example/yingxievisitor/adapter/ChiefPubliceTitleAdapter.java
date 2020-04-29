package com.example.yingxievisitor.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.CheifPubTitleBean;
import com.example.yingxievisitor.bean.MainNewsBean;

import java.util.List;

public class ChiefPubliceTitleAdapter extends BaseQuickAdapter<CheifPubTitleBean, BaseViewHolder> {

    public ChiefPubliceTitleAdapter(int layoutResId, @Nullable List<CheifPubTitleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheifPubTitleBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        LinearLayout ll_item =helper.getView(R.id.ll_item);
        View line =helper.getView(R.id.view_line);

        if (item.getChoose()){
            ll_item.setBackgroundResource(R.color.white);
            line.setVisibility(View.VISIBLE);
        }else {
            ll_item.setBackgroundResource(R.color.crF7);
            line.setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.ll_item);
    }
}
