package com.example.yingxievisitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.yingxievisitor.MainActivity;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.ChiefPublicActivity;
import com.example.yingxievisitor.activity.WebActivity;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.bean.NewsBean;
import com.example.yingxievisitor.utils.ToastUtils;
import com.sunfusheng.marqueeview.MarqueeView;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private TextView tv_if_wifi,tv_wifi_name;
    private ImageView tv_chief_public,image_traffic,image_3d;
    private Banner home_banner;
    private LinearLayout llOpenWifi,ll_find_job,ll_used_car,ll_find_home,ll_used,ll_service;
    private WifiManager wifiManager; //WifiManager
    private WifiInfo connectionInfo;
    private MarqueeView marqueeView;


    private List<String> bannerList =new ArrayList<>();
    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 获取wifi状态
     */
    @Override
    public void onResume() {
        super.onResume();
       if (wifiManager!=null){
           int wifiState = wifiManager.getWifiState();
           connectionInfo= wifiManager.getConnectionInfo();
           Log.e("fhxx","当前wifi----》"+wifiState+"------"+connectionInfo.getSSID());
           if (wifiState==3){
               tv_if_wifi.setText("已连接WiFi");
               tv_wifi_name.setText(connectionInfo.getSSID());
           }else {
               tv_if_wifi.setText("未连接WiFi");
               tv_wifi_name.setText("未连接");
           }
       }
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tv_if_wifi =view.findViewById(R.id.tv_if_wifi);
        tv_wifi_name =view.findViewById(R.id.tv_wifi_name);
        tv_chief_public =view.findViewById(R.id.tv_chief_public);
        home_banner =view.findViewById(R.id.home_banner);
        llOpenWifi =view.findViewById(R.id.ll_open_wifi);
        ll_find_job =view.findViewById(R.id.ll_find_job);
        ll_used_car =view.findViewById(R.id.ll_used_car);
        ll_find_home =view.findViewById(R.id.ll_find_home);
        ll_used =view.findViewById(R.id.ll_used);
        ll_service =view.findViewById(R.id.ll_service);
        image_traffic =view.findViewById(R.id.image_traffic);
        image_3d =view.findViewById(R.id.image_3d);
        marqueeView =view.findViewById(R.id.marqueeView);
        wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);

        getNews();//获取新闻列表
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        llOpenWifi.setOnClickListener(this);
        tv_chief_public.setOnClickListener(this);
        ll_find_job.setOnClickListener(this);
        ll_used_car.setOnClickListener(this);
        ll_find_home.setOnClickListener(this);
        ll_used.setOnClickListener(this);
        ll_service.setOnClickListener(this);
        image_traffic.setOnClickListener(this);
        image_3d.setOnClickListener(this);
    }


    /**
     * 设置banner 和 文字跑马灯
     */
    private void setBanner(){

        marqueeView.startWithList(newsList);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                toWeb("新闻详情",AppUrl.NewsBase+list.get(position).getNewid());
            }
        });

        final IndicatorView indicatorView = new IndicatorView(mActivity)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT)
                .setIndicatorRatio(1f)
                .setIndicatorRadius(2f)
                .setIndicatorSelectedRatio(3)
                .setIndicatorSelectedRadius(2f)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);

        home_banner.setIndicator(indicatorView)
                .setAutoPlay(true)
                .setAutoTurningTime(2000)
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
                                toWeb("新闻详情",AppUrl.NewsBase+list.get(index).getNewid());
                            }
                        });
                        return iv;
                    }
                })
                .setPageTransformer(true,new ScaleInTransformer())
                .setRoundCorners(10f)
                .setPages(bannerList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_open_wifi:

                int length = connectionInfo.getSSID().length();
                String substring = connectionInfo.getSSID().substring(1, length-1);
                Log.e("fhxx  ssid",substring+ substring.equals("YXLJD") +"");
                if (connectionInfo.getSSID()!=null&&substring.equals("YXLJD")){
//                    toBrowser(""); //去认证界面
                    ToastUtils.show("取认证界面");
                }else {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }

            break;
            case R.id.tv_chief_public:
            startActivity(new Intent(mActivity, ChiefPublicActivity.class));
                break;
            case R.id.ll_find_job:
                toBrowser("https://m.58.com/zz/job.shtml");
                break;
            case R.id.ll_used_car:
                toBrowser("https://m.58.com/zz/car.shtml");

                break;
            case R.id.ll_find_home:
                toBrowser("https://m.58.com/zz/house.shtml");

                break;
            case R.id.ll_used:
                toBrowser("https://m.58.com/zz/sale.shtml");

                break;
            case R.id.ll_service:
                toBrowser("https://m.58.com/zz/huangye.shtml");

                break;
            case R.id.image_traffic:
                ToastUtils.show("点击附近");
                break;
            case R.id.image_3d:
                toWeb("三维实景","http://192.168.10.104:8080/zhjd/earthstreet.html");
                break;
        }
    }

    /**
     * 跳转浏览器
     * @param url
     */
    private void toBrowser(String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 获取banner和滚动新闻
     */
    private List<String> newsList =new ArrayList<>();
    List<NewsBean.DataBean.ListBean> list=new ArrayList<>();
    private void getNews(){
        EasyHttp.get(AppUrl.GetNewsList)
                .params("page","1")
                .params("size","4")
                .params("isHome","1")
                .syncRequest(false)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        NewsBean newsBean = JSON.parseObject(s, NewsBean.class);
                        if (newsBean.isStatus()&&newsBean!=null){
                            list = newsBean.getData().getList();
                            for (int i = 0; i < list.size(); i++) {
                                bannerList.add(AppUrl.ImageBase+list.get(i).getImg());
                                newsList.add(list.get(i).getTitle());
                            }

                            setBanner();
                        }
                    }
                });

    }

    /**
     * 去新闻详情页
     */
    private void toWeb(String title,String url){
        Intent intent = new Intent(mActivity, WebActivity.class);
        intent.putExtra("webUrl",url);
        intent.putExtra("webTitle",title);
        startActivity(intent);
    }

}
