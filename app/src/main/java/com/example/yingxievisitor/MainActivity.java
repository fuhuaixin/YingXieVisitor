package com.example.yingxievisitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.fragment.HomeFragment;
import com.example.yingxievisitor.fragment.MineFragment;
import com.example.yingxievisitor.fragment.NearFragment;
import com.example.yingxievisitor.fragment.NewsFragment;
import com.example.yingxievisitor.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private FrameLayout frameLayout;
    private RadioGroup rgMain;

    //装fragment的实例集合
    private ArrayList<Fragment> fragments;

    private int position = 0;

    //缓存Fragment或上次显示的Fragment
    private Fragment tempFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static MainActivity newInstance() {
        BMapManager.init();
        return new MainActivity();
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        dialog();
        //设置不可侧滑退出
        setSwipeBackEnable(false);
        zLoadingDialog.show();

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        rgMain = (RadioGroup) findViewById(R.id.rg_main);

    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void initListen() {

        rgMain.check(R.id.rb_home);
        switchFragment(tempFragment,new HomeFragment());
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home: //首页
                        position = 0;
                        break;
                    case R.id.rb_news: //新闻
                        position = 1;
                        break;
                    case R.id.rb_near: //附近
                        position = 2;
                        break;
                    case R.id.rb_mine: //我的
                        position = 3;
                        break;
                }
                //根据位置得到相应的Fragment
                Fragment baseFragment = getFragment(position);
                /**
                 * 第一个参数: 上次显示的Fragment
                 * 第二个参数: 当前正要显示的Fragment
                 */
                switchFragment(tempFragment,baseFragment);
            }
        });

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    zLoadingDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 添加的时候按照顺序
     */
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NearFragment());
        fragments.add(new MineFragment());
    }


    /**
     * 根据位置得到对应的 Fragment
     * @param position
     * @return
     */
    public Fragment getFragment(int position){
        if(fragments != null && fragments.size()>0){
            Fragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }


    /**
     * 切换Fragment
     * @param fragment
     * @param nextFragment
     */
    public void switchFragment(Fragment fragment, Fragment nextFragment){
        if (tempFragment != nextFragment){
            tempFragment = nextFragment;
            if (nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加成功
                if (!nextFragment.isAdded()){
                    //隐藏当前的Fragment
                    if (fragment != null){
                        transaction.hide(fragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else {
                    //隐藏当前Fragment
                    if (fragment != null){
                        transaction.hide(fragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }


    /**
     * 两次点击退出
     */
    private static final int TIME_EXIT = 2000;
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "再点击一次返回退出程序", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }
}
