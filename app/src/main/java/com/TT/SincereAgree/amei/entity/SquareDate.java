package com.TT.SincereAgree.amei.entity;

import java.util.Date;

/**
 * Created by 冯雪松 on 2017-12-13.
 */

public class SquareDate {
    String userProfile;
    //int vipLevel;
    //boolean certifacated;
    String userName;
    String dynamicReleaseTime;
    String releasePicture;
    String sex;
    String age;
    //String constellation;
    String userheight;
    String userweight;
    String mainText;
    String classification0;
    String classification1;
    String classification2;
    String classification3;
    String location;
    Date dynamicReleaseDay;

    public SquareDate(String userProfile, String userName, String dynamicReleaseTime,
                      String releasePicture, String sex, String age, String userheight,
                      String userweight, String mainText, String classification0,
                      String classification1, String classification2, String classification3, String location, Date dynamicReleaseDay) {
        this.userProfile = userProfile;
        this.userName = userName;
        this.dynamicReleaseTime = dynamicReleaseTime;
        this.releasePicture = releasePicture;
        this.sex = sex;
        this.age = age;
        //this.constellation = constellation;
        this.userheight = userheight;
        this.userweight = userweight;
        this.mainText = mainText;
        this.classification0 = classification0;
        this.classification1 = classification1;
        this.classification2 = classification2;
        this.classification3 = classification3;
        this.location = location;
        this.dynamicReleaseDay = dynamicReleaseDay;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDynamicReleaseTime() {
        return dynamicReleaseTime;
    }

    public void setDynamicReleaseTime(String dynamicReleaseTime) {
        this.dynamicReleaseTime = dynamicReleaseTime;
    }

    public String getReleasePicture() {
        return releasePicture;
    }

    public void setReleasePicture(String releasePicture) {
        this.releasePicture = releasePicture;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getUserheight() {
        return userheight;
    }

    public void setUserheight(String userheight) {
        this.userheight = userheight;
    }

    public String getUserweight() {
        return userweight;
    }

    public void setUserweight(String userweight) {
        this.userweight = userweight;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getClassification0() {
        return classification0;
    }

    public void setClassification0(String classification0) {
        this.classification0 = classification0;
    }

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    public String getClassification2() {
        return classification2;
    }

    public void setClassification2(String classification2) {
        this.classification2 = classification2;
    }

    public String getClassification3() {
        return classification3;
    }

    public void setClassification3(String classification3) {
        this.classification3 = classification3;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDynamicReleaseDay() {
        return dynamicReleaseDay;
    }

    public void setDynamicReleaseDay(Date dynamicReleaseDay) {
        this.dynamicReleaseDay = dynamicReleaseDay;
    }
}
