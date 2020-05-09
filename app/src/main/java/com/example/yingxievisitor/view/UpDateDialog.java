package com.example.yingxievisitor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.utils.ToastUtils;

/**
 * 更新弹窗
 */
public class UpDateDialog extends Dialog {

    private ImageView image_close;
    private TextView tv_update;
    private Context mContext;
    public UpDateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext =context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        image_close =findViewById(R.id.image_close);
        tv_update =findViewById(R.id.tv_update);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }


}
