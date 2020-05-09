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


    //实时环境信息接口
    public static final String RealEnvironment = basePath+"env/realEnvironment";
    //获取新闻列表
    public static final String GetNewsList =basePath+"app/getNewsList";
    //获取在线办事列表
    public static final String GovinfoList =basePath+"govinfo/list";


    //用户注册检查
    public static final String CheckExist =basePath+"appUser/checkExist";
    //用户注册
    public static final String Register =basePath+"appUser/register";
    //用户登录
    public static final String Login =basePath+"appUser/login";
    //用户退出
    public static final String Logout =basePath+"appUser/logout";
    //用户重置密码
    public static final String ResetPassword =basePath+"appUser/resetPassword";
    //用户提交反馈
    public static final String FeedbackSubmit =basePath+"appFeedback/submit";
    //检查app版本更新
    public static final String GetLastVersion =basePath+"app/getLastVersion";
    //app下载
    public static final String DownloadApk ="zhjd/apk/yxlzhjd-client.apk";



}
