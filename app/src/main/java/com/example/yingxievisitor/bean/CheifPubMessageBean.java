package com.example.yingxievisitor.bean;

public class CheifPubMessageBean {
    String message="";
    Boolean isChoose =false;

    public CheifPubMessageBean(String message, Boolean isChoose) {
        this.message = message;
        this.isChoose = isChoose;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String title) {
        this.message = title;
    }

    public Boolean getChoose() {
        return isChoose;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }
}
