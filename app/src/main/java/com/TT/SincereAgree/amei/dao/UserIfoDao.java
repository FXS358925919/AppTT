package com.TT.SincereAgree.amei.dao;

import com.TT.SincereAgree.amei.entity.User;

/**
 * Created by Amei on 2017/11/15.
 */

public class UserIfoDao {

    //根据登录账号获取登录用户的信息
    public User userIfo(String account){
       User user = new User();
        user.setPhone("123456");
        user.setNickname("陈东阳");
        user.setBirthday("2017-03-15");
        user.setCity("中国");
        user.setInfPerfect(false);
       return user;
    }
}
