package com.example.yingxievisitor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yingxievisitor.R;
import com.example.yingxievisitor.adapter.NearBusMessAdapter;
import com.example.yingxievisitor.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近信息弹窗
 */
public class NearMessageDialog extends Dialog {

    private TextView tv_title,tv_address,tv_phone;
    private  String title,address,number;
    private Boolean isBus =false;
    private LinearLayout ll_no_bus,ll_bus;
    private RecyclerView recycle_bus;
    private Context mContext;
    private NearBusMessAdapter nearBusMessAdapter;
    private List<String > busList = new ArrayList<>();
    public NearMessageDialog(@NonNull Context context,String title,String address,String number,Boolean isBus) {
        super(context, R.style.nearDialog);
        this.mContext =context;
        this.title =title;
        this.address =address;
        this.number =number;
        this.isBus =isBus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_near_message);
        initViews();
        initData();
    }
    private void initViews() {
        tv_title =findViewById(R.id.tv_title);
        tv_address =findViewById(R.id.tv_address);
        tv_phone =findViewById(R.id.tv_phone);
        ll_no_bus =findViewById(R.id.ll_no_bus);
        ll_bus =findViewById(R.id.ll_bus);
        recycle_bus =findViewById(R.id.recycle_bus);
    }
    private void initData() {

        tv_address.setText(address);
        if (number!=null && !number.equals("")){
            tv_phone.setText("联系方式："+number);
        }else {
            tv_phone.setText("联系方式："+"未知");
        }

        if (isBus){
            tv_title.setText(title+"公交站");
            ll_bus.setVisibility(View.VISIBLE);
            ll_no_bus.setVisibility(View.GONE);
            String[] strs = address.split(";");

            for (int i = 0; i < strs.length; i++) {
                busList.add(strs[i]);
            }
        }else {
            tv_title.setText(title);
            ll_bus.setVisibility(View.GONE);
            ll_no_bus.setVisibility(View.VISIBLE);
        }

       /* recycle_bus.setLayoutManager(new LinearLayoutManager(mContext));
        nearBusMessAdapter =new NearBusMessAdapter(R.layout.item_near_bus_message,busList,mContext);
        recycle_bus.setAdapter(nearBusMessAdapter);

        nearBusMessAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ll_item:
                        ToastUtils.show(busList.get(position));
                        break;
                }
            }
        });*/
         //
    }


}
