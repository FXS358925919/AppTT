package com.TT.SincereAgree.amei.entity;

import java.io.Serializable;

/**
 * Created by Amei on 2017/11/28.
 */

public class Comment implements Serializable{

    private String parentId;//评论的哪个人的动态
    private String messageId;//评论的那个人的哪条动态
    private String userId;//评论人
    private String commentUserName;//评论用户的名字
    private String commentId;//评论编号
    private String text;
    private int isgoodcomment;

    public Comment(String parentId, String messageId, String userId, String commentId, String text){
        this.parentId = parentId;
        this.commentId = commentId;
        this.messageId = messageId;
        this.text = text;
        this.userId = userId;
    }

    public Comment(String username,String text,int isgoodcomment){
        this.text = text;
        this.commentUserName = username;
        this.isgoodcomment = isgoodcomment;

    }

    public Comment(String username,String text){
        this.commentUserName = username;
        this.text = text;
    }

    public Comment(){}


    public int getIsgoodcomment() {
        return isgoodcomment;
    }

    public void setIsgoodcomment(int isgoodcomment) {

        this.isgoodcomment = isgoodcomment;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParentId() {
        return parentId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getText() {
        return text;
    }


}
