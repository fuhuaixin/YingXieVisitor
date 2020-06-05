package com.example.yingxievisitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.adapter.MainNewsAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.WebActivity;
import com.example.yingxievisitor.adapter.MainNewsAllAdapter;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.bean.MainNewsBean;
import com.example.yingxievisitor.bean.NewsBean;
import com.example.yingxievisitor.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻fragment  新闻类型（pianqu\zhengce）
 */
public class NewsFragment extends BaseFragment {
    private ImageView imageBack;
    private TextView tvTitle,tv_new_news,tv_near_news;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView new_recycle;
//    private MainNewsAdapter mainNewsAdapter;
    private MainNewsAllAdapter mainNewsAdapter;

//    private List<MainNewsBean> newsBeanList =new ArrayList<>();
    private List<NewsBean.DataBean.ListBean> newsBeanList =new ArrayList<>();
    private int chooseType =1; //1是政策 2 是片区
    private int page =1; //第几页
    @Override
    public int setLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tvTitle =view.findViewById(R.id.tv_title);
        tv_new_news =view.findViewById(R.id.tv_new_news);
        tv_near_news =view.findViewById(R.id.tv_near_news);
        imageBack =view.findViewById(R.id.image_back);
        new_recycle =view.findViewById(R.id.new_recycle);
        refreshLayout =view.findViewById(R.id.refreshLayout);

    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("新闻");
        imageBack.setVisibility(View.GONE);

        new_recycle.setLayoutManager(new LinearLayoutManager(mActivity));
        mainNewsAdapter =new MainNewsAllAdapter(mActivity);
        mainNewsAdapter.setData(newsBeanList);

        new_recycle.setAdapter(mainNewsAdapter);

//        refreshLayout.setEnableRefresh(false);
        getNews("zhengce",page);

    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        tv_new_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsBeanList.clear();
                chooseType=1;
                page=1;
                tv_new_news.setTextColor(mActivity.getResources().getColor(R.color.cr21));
                tv_new_news.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_near_news.setTextColor(mActivity.getResources().getColor(R.color.cr33));
                tv_near_news.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                getNews("zhengce",1);
            }
        });
        tv_near_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsBeanList.clear();
                chooseType=2;
                page=1;
                tv_near_news.setTextColor(mActivity.getResources().getColor(R.color.cr21));
                tv_near_news.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_new_news.setTextColor(mActivity.getResources().getColor(R.color.cr33));
                tv_new_news.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                getNews("pianqu",1);

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newsBeanList.clear();
                page=1;
                if (chooseType==1){
                    getNews("zhengce",page) ;
                }else if (chooseType==2){
                    getNews("pianqu",page) ;
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page ++ ;
                if (chooseType==1){
                    getNews("zhengce",page) ;
                }else if (chooseType==2){
                    getNews("pianqu",page) ;
                }
            }
        });
    }




    /**
     * 获取新闻
     */
    private void getNews(String type,int page){
        EasyHttp.get(AppUrl.GetNewsList)
                .params("type",type)
                .params("page", String.valueOf(page))
                .params("size","10")
                .syncRequest(false)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        NewsBean newsBean = JSON.parseObject(s, NewsBean.class);
                        if (newsBean.isStatus()&&newsBean!=null){
                            List<NewsBean.DataBean.ListBean> list = newsBean.getData().getList();
                            newsBeanList.addAll(list);

                            mainNewsAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

}
