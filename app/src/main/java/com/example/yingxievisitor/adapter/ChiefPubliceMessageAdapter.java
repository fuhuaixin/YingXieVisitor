package com.example.yingxievisitor.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.CheifPubMessageBean;
import com.example.yingxievisitor.bean.CheifPubTitleBean;

import java.util.List;

public class ChiefPubliceMessageAdapter extends BaseQuickAdapter<CheifPubMessageBean, BaseViewHolder> {

    public ChiefPubliceMessageAdapter(int layoutResId, @Nullable List<CheifPubMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheifPubMessageBean item) {
        helper.setText(R.id.tv_title,item.getMessage());
        LinearLayout tv_gone =helper.getView(R.id.ll_gone);
        if (item.getChoose()){
            tv_gone.setVisibility(View.VISIBLE);
        }else {
            tv_gone.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.ll_item,R.id.tv_guide,R.id.tv_online);
    }
}
