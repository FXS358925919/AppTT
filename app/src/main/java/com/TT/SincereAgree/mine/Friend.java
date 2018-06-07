package com.TT.SincereAgree.mine;

/**
 * Created by 冯雪松 on 2017-09-11.
 */

public class Friend {
    private String accountid;
    private String name;
    private String imageUrl;
    private String sex;
    public Friend(String accountid, String name, String imageUrl, String sex) {
        this.accountid = accountid;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sex = sex;
    }

    public void setAccountid(String accountid) {

        this.accountid = accountid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccountid() {

        return accountid;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
