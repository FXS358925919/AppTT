package com.TT.SincereAgree;

/**
 * Created by 冯雪松 on 2018-01-16.
 */

public class Configure {
    public static final String serverIP= "192.168.191.1";
    public static final String serverPort= "8080";
    public static final String rootUrl = "http://"+serverIP+":"+serverPort+"/TT/";
    public static final String userUrl = rootUrl+"mine/home/profile?url=";
    public static final String dynamicpic = rootUrl+"mine/home/dynamic?url=";
    public static final String albumImgUrl=rootUrl+"image/albumimg?url=";
    public static String accountId = "00000001";
    public static String sex;
    public static String name;
    public static String picUrl;
    public static String getTokenUrl = "http://api.cn.ronghub.com/user/getToken.json";
    public static final String appSecret = "1A7ZV9tt1ifK";
    public static final String appKey = "25wehl3u29hvw";
    public static String chatToId;
    public static int sendCount = 0;
    public static int receiveCount = 0;
    public static int sendValueCount = 0;
    //只有女性角色对男性角色发消息才是false，其他都是true
    public static boolean reduceIntegral = true;
    //积分是否不足的标志
    public static boolean lackIntegeral = false;


}
