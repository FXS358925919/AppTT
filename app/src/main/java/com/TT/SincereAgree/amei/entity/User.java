package com.TT.SincereAgree.amei.entity;


import java.util.Date;

/**
 * Created by Amei on 2017/11/8.
 */

public class User {
    private String userID;
    private String nickname;
    private String realname;
    private String sex;
    private String birthday;
    private String phone;
    private String weixinnumber;
    private String qqnumber;
    private String threenumber;//三围
    private String height;
    private String job;
    private String province;
    private String email;
    private String city;
    private String address;
    private String profileImageUrl;
    private String description;
    private Date registerDate;
    // 找回密码时用到的信息
    private String validataCode;
    private Long expierTime;
    private Integer point;
    private String type;
    /**
     * 用户资料是否完善标志位，默认为不完善false
     * 完善个人资料后为true*/

    private boolean InfPerfect;

    public void setInfPerfect(boolean infPerfect) {
        InfPerfect = infPerfect;
    }

    public boolean isInfPerfect() {
        return InfPerfect;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {

        return height;
    }


    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {

        return job;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {

        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRealname() {
        return realname;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeixinnumber() {
        return weixinnumber;
    }

    public String getQqnumber() {
        return qqnumber;
    }

    public String getThreenumber() {
        return threenumber;
    }

    public String getProvince() {
        return province;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public String getValidataCode() {
        return validataCode;
    }

    public Long getExpierTime() {
        return expierTime;
    }

    public Integer getPoint() {
        return point;
    }

    public String getType() {
        return type;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeixinnumber(String weixinnumber) {
        this.weixinnumber = weixinnumber;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }

    public void setThreenumber(String threenumber) {
        this.threenumber = threenumber;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setValidataCode(String validataCode) {
        this.validataCode = validataCode;
    }

    public void setExpierTime(Long expierTime) {
        this.expierTime = expierTime;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser(User source) {
        if (source != null) {
            if (source.getRealname() != null)
                this.setProfileImageUrl(source.getRealname());
            if (source.getSex() != null)
                this.setSex(source.getSex());
            if (source.getBirthday() != null)
                this.setBirthday(source.getBirthday());
            if (source.getPhone() != null)
                this.setPhone(source.getPhone());
            if (source.getEmail() != null)
                this.setEmail(source.getEmail());
            if (source.getProvince() != null)
                this.setProvince(source.getProvince());
            if (source.getCity() != null)
                this.setCity(source.getCity());
            if (source.getAddress() != null)
                this.setAddress(source.getAddress());
            if (source.getProfileImageUrl() != null)
                this.setProfileImageUrl(source.getProfileImageUrl());
            if (source.getDescription() != null)
                this.setDescription(source.getDescription());
            if (source.getRegisterDate() != null)
                this.setRegisterDate(source.getRegisterDate());
            if (source.getValidataCode() != null)

                this.setValidataCode(source.getValidataCode());
            if (source.getExpierTime() != null)
                this.setExpierTime(source.getExpierTime());
        }
    }
}
