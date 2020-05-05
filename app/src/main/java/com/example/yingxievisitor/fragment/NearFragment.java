package com.example.yingxievisitor.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseFragment;

public class NearFragment extends BaseFragment implements View.OnClickListener{


    private ImageView imageBack;
    private TextView tvTitle;
    private LinearLayout ll_into;
    private MapView mapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LinearLayout ll_find,ll_traffic;
    private DrawerLayout draw_layout;
    private boolean isFirstLoc = true; //第一次定位

    @Override
    public int setLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        imageBack =view.findViewById(R.id.image_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ll_into = view.findViewById(R.id.ll_into);
        mapView =view.findViewById(R.id.mapView);
        ll_find =view.findViewById(R.id.ll_find);
        ll_traffic =view.findViewById(R.id.ll_traffic);
        draw_layout =view.findViewById(R.id.draw_layout);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("附近");
        imageBack.setVisibility(View.GONE);
        initMap(); //定位
        //设置drawLayout的宽度
        WindowManager wm = mActivity.getWindowManager();//获取屏幕宽高
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = ll_into.getLayoutParams();
        layoutParams.width = width * 1 / 2;
        ll_into.setLayoutParams(layoutParams);


    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        ll_find.setOnClickListener(this);
        ll_traffic.setOnClickListener(this);
    }

    private void initMap() {
        mapView.removeViewAt(1);
        mBaiduMap = mapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        //定位初始化
        mLocationClient = new LocationClient(getContext());


        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);


        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        mLocationClient.registerLocationListener(mListener);
        //开启地图定位图层
        mLocationClient.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(19.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_find:
                draw_layout.openDrawer(GravityCompat.END);
                break;
            case R.id.ll_traffic:

                break;
        }
    }
}
