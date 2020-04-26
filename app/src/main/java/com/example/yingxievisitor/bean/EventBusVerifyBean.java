package com.example.yingxievisitor.bean;

public class EventBusVerifyBean {

    private Boolean isVerify =false;
    private String type ="";

    public EventBusVerifyBean(Boolean isVerify, String type) {
        this.isVerify = isVerify;
        this.type = type;
    }

    public Boolean getVerify() {
        return isVerify;
    }

    public void setVerify(Boolean verify) {
        isVerify = verify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
