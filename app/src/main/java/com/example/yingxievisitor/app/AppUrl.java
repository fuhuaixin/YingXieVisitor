package com.example.yingxievisitor.app;

public class AppUrl {


    public static final String BaseURLTest = "http://192.168.10.151:8080/"; //测试(测试环境)
    public static final String BaseURLTest2 = "http://192.168.10.104:8080/"; //测试(测试环境)2

    //图片前缀
    public static final String ImageBase = "http://192.168.10.104:8055/Datas/img/";
    //新闻前缀
    public static final String NewsBase = "http://192.168.10.104:8080/zhjd/news.html?id=";
    //办事指南前缀
    public static final String OnlineWorkBase = "http://192.168.10.104:8080/zhjd/onlinework.html?id=";

    public static final String basePath ="zhjd/server/";

    //获取新闻列表
    public static final String GetNewsList =basePath+"app/getNewsList";
    //获取在线办事列表
    public static final String GovinfoList =basePath+"govinfo/list";


}
