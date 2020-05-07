package com.example.yingxievisitor.bean;

public class NearRimBean {
    private String msg;
    private int isTrue;

    public NearRimBean(String msg, int isTrue) {
        this.msg = msg;
        this.isTrue = isTrue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }
}
