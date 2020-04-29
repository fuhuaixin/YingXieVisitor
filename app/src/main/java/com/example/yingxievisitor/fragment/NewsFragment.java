package com.example.yingxievisitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.adapter.MainNewsAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.WebActivity;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.bean.MainNewsBean;
import com.example.yingxievisitor.utils.ToastUtils;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻fragment
 */
public class NewsFragment extends BaseFragment {
    private ImageView imageBack;
    private TextView tvTitle,tv_new_news,tv_near_news;

    private Banner news_banner;
    private RecyclerView new_recycle;
    private MainNewsAdapter mainNewsAdapter;

    private static final String[] URLS = {
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2860421298,3956393162&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=163638141,898531478&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1028426622,4209712325&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462142898,440466184&fm=26&gp=0.jpg"
    };
    private List<String> bannerList =new ArrayList<>();
    private List<MainNewsBean> newsBeanList =new ArrayList<>();
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
        news_banner =view.findViewById(R.id.news_banner);
        new_recycle =view.findViewById(R.id.new_recycle);

    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("新闻");
        imageBack.setVisibility(View.GONE);
        getNewsData(1);

        for (int i = 0; i < URLS.length; i++) {
            bannerList.add(URLS[i]);
        }
        setBanner();


    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        tv_new_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_new_news.setTextColor(mActivity.getResources().getColor(R.color.cr21));
                tv_new_news.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_near_news.setTextColor(mActivity.getResources().getColor(R.color.cr33));
                tv_near_news.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                getNewsData(1);

            }
        });
        tv_near_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_near_news.setTextColor(mActivity.getResources().getColor(R.color.cr21));
                tv_near_news.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_new_news.setTextColor(mActivity.getResources().getColor(R.color.cr33));
                tv_new_news.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                getNewsData(2);
            }
        });
    }


    private void setBanner(){
        final IndicatorView indicatorView = new IndicatorView(mActivity)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT)
                .setIndicatorRatio(1f)
                .setIndicatorRadius(2f)
                .setIndicatorSelectedRatio(3)
                .setIndicatorSelectedRadius(2f)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);

        news_banner.setIndicator(indicatorView)
                .setAutoPlay(true)
//                .setPageMargin(SizeUtils.dp2px(20), SizeUtils.dp2px(10))
//                .setRoundCorners(SizeUtils.dp2px(20))
                .setHolderCreator(new HolderCreator() {
                    @Override
                    public View createView(Context context, final int index, Object o) {
                        ImageView iv = new ImageView(context);
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(context).load(o).into(iv);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.show(index + "");
                            }
                        });
                        return iv;
                    }
                })
                .setPageTransformer(true,new ScaleInTransformer())
                .setPages(bannerList);
    }


    private void getNewsData(int type){
        newsBeanList.clear();
        if (type==1){
            for (int i = 0; i < 10; i++) {
                newsBeanList.add(new MainNewsBean("郑州市信用体系总规划的批复"+i,"2020-04-2"+i,
                        "郑州市我国重要的信用示范基地，华北地区的重要中心城市。实施要深入贯彻党额度十八大和十八届三中、五中、六中全会及中央城镇化工作会议、中央城市," +
                                "乌拉乌拉乌拉"+i));
            }

        }else if (type==2){
            for (int i = 0; i < 5; i++) {
                newsBeanList.add(new MainNewsBean("新乡市信用体系总规划的批复"+i,"2021-02-1"+i,
                        "新乡市我国重要的信用示范基地，华北地区的重要中心城市。实施要深入贯彻党额度十八大和十八届三中、五中、六中全会及中央城镇化工作会议、中央城市," +
                                "乌拉乌拉乌拉"+i));
            }
        }

        new_recycle.setLayoutManager(new LinearLayoutManager(mActivity));
        mainNewsAdapter =new MainNewsAdapter(R.layout.item_main_news,newsBeanList);
        new_recycle.setAdapter(mainNewsAdapter);
        mainNewsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ll_item:
//                        ToastUtils.show("点击了"+position);
                        Intent intent = new Intent(mActivity, WebActivity.class);
                        intent.putExtra("webUrl","https://news.sina.com.cn/w/2020-04-27/doc-iircuyvh9997305.shtml");
                        startActivity(intent);
                        break;
                }
            }
        });

        mainNewsAdapter.notifyDataSetChanged();
    }

}
