package com.example.yingxievisitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


import com.bumptech.glide.Glide;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.activity.ChiefPublicActivity;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.utils.ToastUtils;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvHome,tv_chief_public;
    private Banner home_banner;
    private LinearLayout llOpenWifi;
    private WifiManager wifiManager; //WifiManager


    private static final String[] URLS = {
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3210855908,3095539181&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2080505558,2205047574&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2894881224,342594760&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2894881224,342594760&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3162346827,2000964752&fm=26&gp=0.jpg"
    };
    private List<String> bannerList =new ArrayList<>();
    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
       if (wifiManager!=null){
           int wifiState = wifiManager.getWifiState();
           WifiInfo connectionInfo = wifiManager.getConnectionInfo();
//           connectionInfo.getPasspointProviderFriendlyName()
           Log.e("fhxx","当前wifi----》"+wifiState+"------"+connectionInfo.getSSID());
       }
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        tvHome =view.findViewById(R.id.tvHome);
        tv_chief_public =view.findViewById(R.id.tv_chief_public);
        home_banner =view.findViewById(R.id.home_banner);
        llOpenWifi =view.findViewById(R.id.ll_open_wifi);
        wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);

        tvHome.setText("这是HomeFragment");

        for (int i = 0; i < URLS.length; i++) {
            bannerList.add(URLS[i]);
        }
        setBanner();

    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        llOpenWifi.setOnClickListener(this);
        tv_chief_public.setOnClickListener(this);
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

        home_banner.setIndicator(indicatorView)
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
                .setRoundCorners(10f)
                .setPages(bannerList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_open_wifi:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            break;
            case R.id.tv_chief_public:
            startActivity(new Intent(mActivity, ChiefPublicActivity.class));
                break;
        }
    }
}
