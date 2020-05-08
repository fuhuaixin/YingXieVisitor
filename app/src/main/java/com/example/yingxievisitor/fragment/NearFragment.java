package com.example.yingxievisitor.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.adapter.NearBusMessAdapter;
import com.example.yingxievisitor.adapter.NearRimAdapter;
import com.example.yingxievisitor.base.BaseFragment;
import com.example.yingxievisitor.bean.NearRimBean;
import com.example.yingxievisitor.utils.BusLineOverlay;
import com.example.yingxievisitor.utils.ToastUtils;
import com.example.yingxievisitor.view.NearMessageDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近地图fragment
 */
public class NearFragment extends BaseFragment implements View.OnClickListener {


    private ImageView imageBack, image_search, image_add_zoom, image_lose_zoom;
    private TextView tvTitle;
    private LinearLayout ll_into;
    private MapView mapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LinearLayout ll_find, ll_traffic, ll_main_location;
    private DrawerLayout draw_layout;
    private RecyclerView recycle_near_rim;
    private NearRimAdapter nearRimAdapter;
    private boolean isFirstLoc = true; //第一次定位
    private PoiSearch poiSearch;
    private EditText et_search;
    private MapStatus mapStatus;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        imageBack = view.findViewById(R.id.image_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ll_into = view.findViewById(R.id.ll_into);
        mapView = view.findViewById(R.id.mapView);
        ll_find = view.findViewById(R.id.ll_find);
        ll_traffic = view.findViewById(R.id.ll_traffic);
        draw_layout = view.findViewById(R.id.draw_layout);
        recycle_near_rim = view.findViewById(R.id.recycle_near_rim);
        et_search = view.findViewById(R.id.et_search);
        image_search = view.findViewById(R.id.image_search);
        ll_main_location = view.findViewById(R.id.ll_main_location);
        image_add_zoom = view.findViewById(R.id.image_add_zoom);
        image_lose_zoom = view.findViewById(R.id.image_lose_zoom);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        tvTitle.setText("附近");
        imageBack.setVisibility(View.GONE);
        poiSearch = PoiSearch.newInstance();
        initMap(); //定位
        //设置drawLayout的宽度
        WindowManager wm = mActivity.getWindowManager();//获取屏幕宽高
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = ll_into.getLayoutParams();
        layoutParams.width = width * 1 / 2;
        ll_into.setLayoutParams(layoutParams);
        RimRecycle();//设置周边 recycle

    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        ll_find.setOnClickListener(this);
        ll_traffic.setOnClickListener(this);
        image_search.setOnClickListener(this);
        ll_main_location.setOnClickListener(this);
        image_add_zoom.setOnClickListener(this);
        image_lose_zoom.setOnClickListener(this);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    isBusMarker = false;
                    searchNear(et_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_find:
                isBusMarker = false;
                draw_layout.openDrawer(GravityCompat.END);
                break;
            case R.id.ll_traffic:
                isBusMarker = false;
                searchNear("公交站");
                break;
            case R.id.image_search:
                isBusMarker = false;
                searchNear(et_search.getText().toString());
                break;
            case R.id.ll_main_location:
                mBaiduMap.clear();
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(myLatlng)
                        .zoom(19)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                break;
            case R.id.image_add_zoom:
                mapStatus = mBaiduMap.getMapStatus();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mapStatus.zoom + 1).build()));
                break;
            case R.id.image_lose_zoom:
                mapStatus = mBaiduMap.getMapStatus();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mapStatus.zoom - 1).build()));
                break;
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mapView.removeViewAt(1);
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        //定位初始化
        mLocationClient = new LocationClient(getContext());

        mBusLineOverlay = new BusLineOverlay(mBaiduMap);
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

    /**
     * 设置recycle
     */
    private List<NearRimBean> rimBeanList = new ArrayList<>();
    private String[] arrRim = {"美食", "酒店", "电影院", "停车场", "银行", "商场", "厕所", "药店"};

    private void RimRecycle() {
        for (int i = 0; i < arrRim.length; i++) {
            rimBeanList.add(new NearRimBean(arrRim[i], 2));
        }

        nearRimAdapter = new NearRimAdapter(R.layout.item_near_rim, rimBeanList, mActivity);
        recycle_near_rim.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recycle_near_rim.setAdapter(nearRimAdapter);
        nearRimAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvMessage:
                        for (int i = 0; i < rimBeanList.size(); i++) {
                            rimBeanList.get(i).setIsTrue(2);
                        }
                        rimBeanList.get(position).setIsTrue(1);
                        searchNear(arrRim[position]);
                        nearRimAdapter.notifyDataSetChanged();
                        draw_layout.closeDrawer(GravityCompat.END);
                        break;
                }
            }
        });
    }

    /**
     * 区域检索
     */
    private String isBus = "";

    private void searchNear(String str) {

        poiSearch.setOnGetPoiSearchResultListener(poiResltListener);
        isBus = str;
        /**
         * 设置矩形检索区域
         */
        LatLngBounds searchBounds = new LatLngBounds.Builder()
                .include(new LatLng(34.768242, 113.721181))
                .include(new LatLng(34.758004, 113.728637))
                .build();

        poiSearch.searchInBound(new PoiBoundSearchOption()
                .bound(searchBounds)
                .keyword(str)
                .pageNum(0)
                .pageCapacity(30));

    }


    /**
     * POI检索监听器
     */

    private List<OverlayOptions> optionsList = new ArrayList<>();
    private String busLineId;
    private List<String> busIdList = new ArrayList<>();
    private OnGetPoiSearchResultListener poiResltListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            Log.e("fhxx  poi top", poiResult.error + "  ----  " + (poiResult.error == SearchResult.ERRORNO.NO_ERROR));

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                busIdList.clear();
                //先定位到英协路
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(new LatLng(34.763375, 113.724974))
                        .zoom(17)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                //清除marker点 清除marker点击事件  清除list存的marker点
                mBaiduMap.clear();
                mBaiduMap.removeMarkerClickListener(onMarkerClickListener);
                optionsList.clear();
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                Log.e("fhxx  poi bot", allPoi.toString());

                BitmapDescriptor bitmap;

                if (isBus.equals("公交站") || isBus.equals("公交")) {
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.icon_near_bus_location);
                } else {
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.icon_near_location_red_little);
                }
                //遍历所有POI，找到类型为公交线路的POI
                for (PoiInfo poi : poiResult.getAllPoi()) {
                    if (poi.getPoiDetailInfo() != null && poi.getPoiDetailInfo().getTag().equals("公交线路")) {
                        Log.e("fhxx tag", poi.getPoiDetailInfo().getTag() + poi.uid);
                        //获取该条公交路线POI的UID
                        busIdList.add(poi.uid);
                        busLineId = poi.uid;
                        getBus(poi.uid);
                        break;
                    }
                }
                Log.e("fhxx", busIdList.toString());
                for (int i = 0; i < allPoi.size(); i++) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("title", allPoi.get(i).getName());
                    mBundle.putDouble("lat", allPoi.get(i).getLocation().latitude);
                    mBundle.putDouble("lng", allPoi.get(i).getLocation().longitude);
                    mBundle.putString("address", allPoi.get(i).getAddress());
                    mBundle.putString("number", allPoi.get(i).getPhoneNum());

                    OverlayOptions option = new MarkerOptions()
                            .extraInfo(mBundle)
                            .position(allPoi.get(i).getLocation())
                            .icon(bitmap);
                    optionsList.add(option);
                }
                mBaiduMap.addOverlays(optionsList);
                mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
            } else {
                ToastUtils.show("附近暂无");
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };

    /**
     * marker点监听
     */
    private NearBusMessAdapter nearBusMessAdapter;
    private List<String> busList = new ArrayList<>();

    private BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (isBusMarker) {
                return false;
            }
            Boolean isbus = false;

            Bundle extraInfo = marker.getExtraInfo();
            String title = extraInfo.getString("title");
            String address = extraInfo.getString("address");
            String number = extraInfo.getString("number");
            double lat = extraInfo.getDouble("lat");
            double lng = extraInfo.getDouble("lng");
            if (isBus.equals("公交站")) {
                isbus = true;
                String[] strs = address.split(";");
                Log.e("fhxx near dialog ", strs.length + " --- ");
                for (int i = 0; i < strs.length; i++) {
                    busList.add(strs[i]);
                }
            } else {
                isbus = false;
            }

            NearMessageDialog nearMessageDialog = new NearMessageDialog(mActivity, title, address, number, isbus);
            nearMessageDialog.show();
            Window window = nearMessageDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            nearMessageDialog.getWindow().setAttributes(lp);
            RecyclerView recycle_bus = nearMessageDialog.findViewById(R.id.recycle_bus);
            recycle_bus.setLayoutManager(new LinearLayoutManager(mActivity));
            nearBusMessAdapter = new NearBusMessAdapter(R.layout.item_near_bus_message, busList, mActivity);
            recycle_bus.setAdapter(nearBusMessAdapter);

            nearBusMessAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.ll_item:
                            getBusMessage(busList.get(position));
                            break;
                    }
                }
            });
            return false;
        }
    };
    /**
     * 定位监听
     */
    private LatLng myLatlng;
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }

            myLatlng = new LatLng(location.getLatitude(),
                    location.getLongitude());
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

    /**
     * 公交站查询
     */
    private void getBusMessage(String bus) {
        poiSearch.setOnGetPoiSearchResultListener(poiResltListener);
        poiSearch.searchInCity(new PoiCitySearchOption()
                .city("郑州")
                .keyword(bus)
                .scope(2));
    }

    /**
     * 根据buslinid查询公交线路
     *
     * @param busLineId
     */
    private void getBus(String busLineId) {
        BusLineSearch busLineSearch = BusLineSearch.newInstance();
        busLineSearch.setOnGetBusLineSearchResultListener(onGetBusLineSearchResultListener);
        busLineSearch.searchBusLine(new BusLineSearchOption()
                .city("郑州")
                .uid(busLineId));

    }

    /**
     * 公交检索监听
     */
    private BusLineResult mBusLineResult = null; // 保存驾车/步行路线数据的变量，供浏览节点时使用
    private BusLineOverlay mBusLineOverlay; // 公交路线绘制对象
    private Boolean isBusMarker = false;
    private OnGetBusLineSearchResultListener onGetBusLineSearchResultListener = new OnGetBusLineSearchResultListener() {
        @Override
        public void onGetBusLineResult(BusLineResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

                ToastUtils.show("抱歉，未找到结果");
                return;
            }
            mBaiduMap.clear();
            mBusLineResult = result;
            mBusLineOverlay.removeFromMap();
            mBusLineOverlay.setData(result);
            mBusLineOverlay.addToMap();
            mBusLineOverlay.zoomToSpan();
            ToastUtils.show(result.getBusLineName());
            isBusMarker = true;
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
    }


}
