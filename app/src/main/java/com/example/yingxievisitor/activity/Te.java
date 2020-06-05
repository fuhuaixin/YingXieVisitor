package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.bikenavi.params.BikeRouteNodeInfo;
import com.baidu.mapapi.model.LatLng;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Te extends BaseActivity {

    Button btn_start;
    private LatLng startPt;
    private LatLng endPt;
    private String TAG ="fhxx";
    private SeekBar seekbar;
    private BikeNaviLaunchParam bikeParam;
    TextView tv_start;
    private Timer timer;

    private int seekTime=0;
    Boolean ifStop =false;
    @Override
    protected int initLayout() {
        return R.layout.activity_te;
    }

    @Override
    protected void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        tv_start = (TextView) findViewById(R.id.tv_start);
    }

    @Override
    protected void initData() {

        startPt = new LatLng(40.057038,116.307899);
        endPt = new LatLng(35.1558, 113.562605);

        /*构造导航起终点参数对象*/
        BikeRouteNodeInfo bikeStartNode = new BikeRouteNodeInfo();
        bikeStartNode.setLocation(startPt);
        BikeRouteNodeInfo bikeEndNode = new BikeRouteNodeInfo();
        bikeEndNode.setLocation(endPt);
        bikeParam = new BikeNaviLaunchParam().startNodeInfo(bikeStartNode).endNodeInfo(bikeEndNode);


        timerStart();
    }

    @Override
    protected void initListen() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BikeNavigateHelper.getInstance().initNaviEngine(Te.this, new IBEngineInitListener() {
                    @Override
                    public void engineInitSuccess() {
                        routePlanWithBikeParam();
                    }

                    @Override
                    public void engineInitFail() {
                        BikeNavigateHelper.getInstance().unInitNaviEngine();
                    }
                });
            }
        });

        seekbar.setOnSeekBarChangeListener(seekBarChangeListener);

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifStop){
                    tv_start.setText("停止");
                    ifStop=false;
                    timerStart();

                }else {
                    timer.cancel();
                    task.cancel();
                    tv_start.setText("开始");
                    ifStop=true;
                }
            }
        });

    }

    /**
     * 发起骑行导航算路
     */
    private void routePlanWithBikeParam() {
        BikeNavigateHelper.getInstance().routePlanWithRouteNode(bikeParam, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "BikeNavi onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "BikeNavi onRoutePlanSuccess");
                Intent intent = new Intent();
                intent.setClass(Te.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "BikeNavi onRoutePlanFail");
            }

        });
    }




    private SeekBar.OnSeekBarChangeListener seekBarChangeListener =new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.e("fhxx","当前进度：" + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.e("fhxx","当前进度 2 ：" + seekBar.getProgress());
//            timer.cancel();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.e("fhxx","结束：" + seekBar.getProgress());
            seekTime=seekBar.getProgress();
            seekbar.setProgress(seekTime);
//            timerStart();
        }
    };

    TimerTask task;
    private void timerStart(){

        timer = new Timer();

        task= new TimerTask() {
            @Override
            public void run() {
                seekTime++;
                seekbar.setProgress(seekTime);
            }
        };
        timer.schedule(task, 0, 1000);
    }

}
