package com.example.yingxievisitor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yingxievisitor.R;
import com.example.yingxievisitor.bean.EventBusVerifyBean;
import com.luozm.captcha.Captcha;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

/**
 * 验证码dialog
 */
public class VerifyDialog extends Dialog {

    private Context mContext;
    private Captcha captcha;
    ImageView imageRefresh; //验证码中的刷新图片按钮

    private Boolean isVerify =false;
    private String type;
    //图形资源
    private int[] imageArray = {R.drawable.icon_one, R.drawable.icon_two, R.drawable.icon_three,
            R.drawable.icon_four, R.drawable.icon_five, R.drawable.icon_six, R.drawable.icon_seven,
            R.drawable.icon_eight, R.drawable.icon_nine, R.drawable.icon_ten};

    public VerifyDialog(@NonNull Context context, int themeResId,String type) {
        super(context, themeResId);
        this.mContext =context;
        this.type =type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_verify);
        initView();
        initData();
    }

    @Override
    public void show() {
        super.show();
        captcha.reset(true);
        captcha.setBitmap(imageArray[nextInt()]);
    }

    private void initView() {
        captcha =findViewById(R.id.captcha);
        imageRefresh=captcha.findViewById(R.id.refresh);
    }


    private void initData() {
        imageRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captcha.reset(true);
                captcha.setBitmap(imageArray[nextInt()]);
            }
        });

        captcha.setBitmap(imageArray[nextInt()]);

        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                isVerify =true;
//                Toast.makeText(mContext,"验证成功",Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new EventBusVerifyBean(isVerify,type));
                dismiss();
                return "验证通过,耗时"+time+"毫秒";
            }

            @Override
            public String onFailed(int failCount) {
                isVerify =false;
                captcha.reset(true);
                captcha.setBitmap(imageArray[nextInt()]);
                EventBus.getDefault().post(new EventBusVerifyBean(isVerify,type));
                Toast.makeText(mContext,"验证失败",Toast.LENGTH_SHORT).show();
                return "验证失败,已失败"+failCount+"次";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(mContext,"验证超过次数，你的帐号被封锁",Toast.LENGTH_SHORT).show();
                return "验证失败,帐号已封锁";
            }
        });

    }

    /**
     *随机生成数字
     * @return
     */
    public int nextInt() {
        return new Random().nextInt(10);
    }



}
