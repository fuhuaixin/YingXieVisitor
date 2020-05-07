package com.example.yingxievisitor.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.NearRimBean;

import java.util.List;

public class NearRimAdapter extends BaseQuickAdapter<NearRimBean,BaseViewHolder>{

    private Context context;

    public NearRimAdapter(int layoutResId, @Nullable List<NearRimBean> data, Context context) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NearRimBean item) {
        helper.setText(R.id.tvMessage,item.getMsg());
        TextView view = helper.getView(R.id.tvMessage);
        if (item.getIsTrue()==1){
            view.setTextColor(context.getResources().getColor(R.color.tvBlue));
            view.setBackgroundResource(R.drawable.shape_near_sel);
        }else {
            view.setTextColor(context.getResources().getColor(R.color.tv66));
            view.setBackgroundResource(R.drawable.shape_near_unsel);
        }
        helper.addOnClickListener(R.id.tvMessage);
    }
}
