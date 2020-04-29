package com.example.yingxievisitor.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.adapter.ChiefPubliceMessageAdapter;
import com.example.yingxievisitor.adapter.ChiefPubliceTitleAdapter;
import com.example.yingxievisitor.base.BaseActivity;
import com.example.yingxievisitor.bean.CheifPubMessageBean;
import com.example.yingxievisitor.bean.CheifPubTitleBean;
import com.example.yingxievisitor.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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

        setTitle();
        setMessage(titleList.get(0).getTitle());
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

                        setMessage(titleList.get(position).getTitle());
                        break;
                }
            }
        });


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

    private void setTitle() {
        titleList.add(new CheifPubTitleBean("全部", false));
        titleList.add(new CheifPubTitleBean("生育收养", false));
        titleList.add(new CheifPubTitleBean("就业创业", false));
        titleList.add(new CheifPubTitleBean("准营准办", false));
        titleList.add(new CheifPubTitleBean("婚姻登记", false));
        titleList.add(new CheifPubTitleBean("优待抚恤", false));
        titleList.add(new CheifPubTitleBean("住房保障", false));
        titleList.add(new CheifPubTitleBean("社会保障", false));
        titleList.add(new CheifPubTitleBean("证件办理", false));
        titleList.add(new CheifPubTitleBean("司法公证", false));
        titleList.add(new CheifPubTitleBean("医疗卫生", false));
    }


    private void setMessage(String title) {
        mesList.clear();
        switch (title) {
            case "全部":
                for (int i = 0; i < 15; i++) {
                    mesList.add(new CheifPubMessageBean("全部" + i, false));
                }
                break;

            case "生育收养":
                for (int i = 0; i < 15; i++) {
                    mesList.add(new CheifPubMessageBean("生育收养" + i, false));
                }
                break;
            case "就业创业":
                for (int i = 0; i < 15; i++) {
                    mesList.add(new CheifPubMessageBean("就业创业" + i, false));
                }
                break;
            case "司法公证":
                for (int i = 0; i < 15; i++) {
                    mesList.add(new CheifPubMessageBean("司法公证" + i, false));
                }
                break;
        }

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
                        ToastUtils.show("tv_guide");
                        break;
                    case R.id.tv_online:
                        ToastUtils.show("tv_online");
                        break;
                }
            }
        });

        chiefPubliceMessageAdapter.notifyDataSetChanged();
    }

}
