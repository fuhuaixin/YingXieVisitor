package com.example.yingxievisitor.bean;

public class MainNewsBean {

    String title ="";
    String time ="";
    String message ="";
    String newsId ="";

    public MainNewsBean(String title, String time, String message,String newsId) {
        this.title = title;
        this.time = time;
        this.message = message;
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
