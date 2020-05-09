package com.example.yingxievisitor.utils;

import com.example.yingxievisitor.base.ChangeFragment;
import com.example.yingxievisitor.fragment.HomeFragment;
import com.example.yingxievisitor.fragment.MineFragment;
import com.example.yingxievisitor.fragment.NearFragment;
import com.example.yingxievisitor.fragment.NewsFragment;

public class GlobalParms {
    private static HomeFragment homeFragment; //主页fragemnt
    private static NewsFragment newsFragment; //走势图fragemnt
    private static NearFragment nearFragment; //资讯fragemnt
    private static MineFragment mineFragment; //其它fragemnt
    public static ChangeFragment sChangeFragment;  //改变选中Frangment的接口

    /**
     * 获取主页Fragment
     *
     * @return
     */
    public static HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    /**
     * 走势图fragemnt
     *
     * @return
     */
    public static NewsFragment getChartsFragment() {
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }
        return newsFragment;
    }

    /**
     * 资讯fragemnt
     *
     * @return
     */
    public static NearFragment getZiXunFragment() {
        if (nearFragment == null) {
            nearFragment = new NearFragment();
        }
        return nearFragment;
    }

    /**
     * //其它fragemnt
     *
     * @return
     */
    public static MineFragment getOtherFragment() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

    /**
     * 设置被选中的Fragment
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        sChangeFragment = changeFragment;

    }


}
