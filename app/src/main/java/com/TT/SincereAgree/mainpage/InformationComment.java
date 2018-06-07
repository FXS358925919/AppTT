package com.TT.SincereAgree.mainpage;

/**
 * Created by 冯雪松 on 2018-06-04.
 */

public class InformationComment {
    String userProfile;
    String userName;
    String dynamicReleaseTime;
    String commentText;
    String dynamicPicUrl;
    String dynaminText;

    public InformationComment(String userProfile, String userName, String dynamicReleaseTime, String commentText, String dynamicPicUrl, String dynaminText) {
        this.userProfile = userProfile;
        this.userName = userName;
        this.dynamicReleaseTime = dynamicReleaseTime;
        this.commentText = commentText;
        this.dynamicPicUrl = dynamicPicUrl;
        this.dynaminText = dynaminText;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getDynamicPicUrl() {
        return dynamicPicUrl;
    }

    public void setDynamicPicUrl(String dynamicPicUrl) {
        this.dynamicPicUrl = dynamicPicUrl;
    }

    public String getDynaminText() {
        return dynaminText;
    }

    public void setDynaminText(String dynaminText) {
        this.dynaminText = dynaminText;
    }
}
