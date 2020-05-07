package com.example.yingxievisitor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.NearRimBean;

import java.util.List;

public class NearBusMessAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    private Context context;

    public NearBusMessAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvBus,item);
        helper.addOnClickListener(R.id.ll_item);
        View view = helper.getView(R.id.line);
        if (helper.getPosition()==0){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
