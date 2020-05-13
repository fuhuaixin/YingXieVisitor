package com.example.yingxievisitor.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.bean.MainNewsBean;

import java.util.List;

public class MainNewsAdapter extends BaseQuickAdapter<MainNewsBean, BaseViewHolder> {

    public MainNewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainNewsBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_time,item.getTime());
        ImageView image_info = helper.getView(R.id.image_info);
        TextView tv_message = helper.getView(R.id.tv_message);
        LinearLayout ll_item = helper.getView(R.id.ll_item);

        if (item.getMessage().equals("")){
            tv_message.setVisibility(View.GONE);
        }else {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText(item.getMessage());
        }
        if (item.getImg().equals("")){
            image_info.setVisibility(View.GONE);
        }else {
            image_info.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(AppUrl.ImageBase+item.getImg()).into(image_info);
        }
        helper.addOnClickListener(R.id.ll_item);
    }
}
