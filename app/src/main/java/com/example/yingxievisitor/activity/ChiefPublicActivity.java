package com.example.yingxievisitor.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.adapter.ChiefPubliceMessageAdapter;
import com.example.yingxievisitor.adapter.ChiefPubliceTitleAdapter;
import com.example.yingxievisitor.app.AppUrl;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.CheifPubMessageBean;
import com.example.yingxievisitor.bean.CheifPubTitleBean;
import com.example.yingxievisitor.bean.GovinfoListBean;
import com.example.yingxievisitor.utils.ToastUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 在线办事
 */
public class ChiefPublicActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageBack;
    private TextView tvTitle;
    private RecyclerView recycle_title, recycle_item;
    private List<CheifPubTitleBean> titleList = new ArrayList<>();
    private ChiefPubliceTitleAdapter chiefPubliceTitleAdapter;
    private List<CheifPubMessageBean> mesList = new ArrayList<>();
    private ChiefPubliceMessageAdapter chiefPubliceMessageAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_chief_public;
    }

    @Override
    protected void initView() {
        imageBack = (ImageView) findViewById(R.id.image_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recycle_title = (RecyclerView) findViewById(R.id.recycle_title);
        recycle_item = (RecyclerView) findViewById(R.id.recycle_item);
    }

    @Override
    protected void initData() {
        tvTitle.setText("在线办事");

        getMessage("", "all");

    }

    @Override
    protected void initListen() {
        imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;
        }
    }


    /**
     * 设置二级列表数据recycle
     */
    private void setMessage() {

        recycle_item.setLayoutManager(new LinearLayoutManager(this));
        chiefPubliceMessageAdapter = new ChiefPubliceMessageAdapter(R.layout.item_chief_public_message, mesList);
        recycle_item.setAdapter(chiefPubliceMessageAdapter);
        chiefPubliceMessageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.ll_item:
                        for (int i = 0; i < mesList.size(); i++) {
                            mesList.get(i).setChoose(false);
                        }
                        mesList.get(position).setChoose(true);
                        chiefPubliceMessageAdapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_guide:
                        toWeb("办事指南", AppUrl.OnlineWorkBase + mesList.get(position).getId());
//                        ToastUtils.show("tv_guide");
                        break;
                    case R.id.tv_online:
                        toBrowser("https://puser.hnzwfw.gov.cn:8081/login?loginType=nationalUser");
                        break;
                }
            }
        });

        chiefPubliceMessageAdapter.notifyDataSetChanged();
    }

    /**
     * 跳转浏览器
     *
     * @param url
     */
    private void toBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 获取所有数据
     * type = all 时 加载所有数据  type = 其他是  去查询category对应的数据
     * category 为空时 加载所有数据  有数据时 去加载对应的数据
     */
    List<String> categoryList = new ArrayList<>();
    List<String> getCategoryList = new ArrayList<>();

    private void getMessage(String category, final String type) {
        EasyHttp.post(AppUrl.GovinfoList)
                .params("category", category)
                .syncRequest(false)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.show("网络请求错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        GovinfoListBean govinfoListBean = JSON.parseObject(s, GovinfoListBean.class);
                        if (govinfoListBean.isStatus() && govinfoListBean.getData().size() > 0) {
                            List<GovinfoListBean.DataBean> data = govinfoListBean.getData();
                            if (type.equals("all")) {
                                for (int i = 0; i < data.size(); i++) {
                                    categoryList.add(data.get(i).getCategory());
                                }

                                Log.e("fhxx", categoryList.toString());
                                getCategoryList = removeDuplicate(categoryList);
                                for (int i = 0; i < getCategoryList.size(); i++) {
                                    if (getCategoryList.get(i) != null) {
                                        titleList.add(new CheifPubTitleBean(getCategoryList.get(i), false));
                                    }
                                }
                                setTitle();
                                getMessage(getCategoryList.get(0), "category");

                            } else {
                                mesList.clear();
                                for (int i = 0; i < data.size(); i++) {
                                    mesList.add(new CheifPubMessageBean(data.get(i).getTitle() + "(" + data.get(i).getSubject() + ")", false, data.get(i).getId()));
                                }
                                setMessage();
                            }

                        } else {
                            ToastUtils.show("暂无数据");
                        }
                    }
                });
    }

    /**
     * 去list中重复
     *
     * @param list
     * @return
     */
    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }


    /**
     * 设置第一级列表
     */
    private void setTitle() {
        titleList.get(0).setChoose(true);
        recycle_title.setLayoutManager(new LinearLayoutManager(this));
        chiefPubliceTitleAdapter = new ChiefPubliceTitleAdapter(R.layout.item_chief_public, titleList);
        recycle_title.setAdapter(chiefPubliceTitleAdapter);
        chiefPubliceTitleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_item:
                        for (int i = 0; i < titleList.size(); i++) {
                            titleList.get(i).setChoose(false);
                        }
                        titleList.get(position).setChoose(true);
                        chiefPubliceTitleAdapter.notifyDataSetChanged();
                        getMessage(titleList.get(position).getTitle(), "category");
                        break;
                }
            }
        });
    }


    /**
     * 去新闻详情页
     */
    private void toWeb(String title, String url) {
        Intent intent = new Intent(ChiefPublicActivity.this, WebActivity.class);
        intent.putExtra("webUrl", url);
        intent.putExtra("webTitle", title);
        startActivity(intent);
    }
}
