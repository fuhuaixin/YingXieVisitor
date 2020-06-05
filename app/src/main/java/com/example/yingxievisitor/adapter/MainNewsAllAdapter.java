package com.example.yingxievisitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.WebActivity;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.bean.NewsBean;

import java.util.List;

public class MainNewsAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int NO_IMAGE =1;
    private final int IMAGE =2;

    private Context mContext;
    private List<NewsBean.DataBean.ListBean> mList;


    public MainNewsAllAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<NewsBean.DataBean.ListBean> mList){
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getImg().equals("")){
            return NO_IMAGE;
        }else {
            return IMAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==NO_IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_no_image,parent,false);
            return new NOImageViewHolder(view);
        }else if (viewType==IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_image,parent,false);
            return new ImageViewHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NOImageViewHolder){
            NOImageViewHolder noImgHolder = (NOImageViewHolder) holder;
            noImgHolder.tv_time.setText(mList.get(position).getUpdatetime());
            noImgHolder.tv_title.setText(mList.get(position).getTitle());
            noImgHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toWeb(mList.get(position).getNewid());
                }
            });
        }else if (holder instanceof ImageViewHolder){
            ImageViewHolder imgHolder = (ImageViewHolder) holder;
            imgHolder.tv_time.setText(mList.get(position).getUpdatetime());
            imgHolder.tv_title.setText(mList.get(position).getTitle());
            Glide.with(mContext).load(AppUrl.ImageBase+mList.get(position).getImg()).into(imgHolder.image_content);
            imgHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toWeb(mList.get(position).getNewid());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NOImageViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_time;
        LinearLayout ll_item;
        public NOImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title =itemView.findViewById(R.id.tv_title);
            tv_time =itemView.findViewById(R.id.tv_time);
            ll_item =itemView.findViewById(R.id.ll_item);

        }
    }
    class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_time;
        ImageView image_content;
        LinearLayout ll_item;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title =itemView.findViewById(R.id.tv_title);
            tv_time =itemView.findViewById(R.id.tv_time);
            image_content =itemView.findViewById(R.id.image_content);
            ll_item =itemView.findViewById(R.id.ll_item);
        }
    }

    private void toWeb(String url){
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("webUrl",AppUrl.NewsBase+url);
        intent.putExtra("webTitle","新闻");
        mContext.startActivity(intent);
    }
}
