package com.TT.SincereAgree.amei.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amei on 2017/12/4.
 */

public class Dynamic implements Serializable{
    private String dId;
    private String accountId;
    private int dCategory;
    private String dyTime;//动态发布的时间
    private String text;//动态的内容
    private String dyUserImag;//动态相关的图片一张
    private String[] dyUserImags = new String[9];
    private int[] fivenums = new int[5];
    private List<Comment> comment;

    private String dynamicUserName;//动态用户的名字
    private String dyUserHeadimag;//动态用户的头像

    private int dyUserHeadima;
    private int dyUserima;

    private Comment hotcomment;
    //private String userName;
    //private String dReleasetime;
    //private String dyima;
   // private String dContent;
    //private String profile;
    //private List<CommentVO> commentList;
    //会弃用
    public Dynamic(String text,String dynamicUserName,String dytime,int duheadima,int dyima,List<Comment> comment,int[] fivenums,String dyimag,String dyUserHeadimag){
        this.text = text;
        this.dynamicUserName = dynamicUserName;
        this.dyTime = dytime;
        this.dyUserHeadima = duheadima;
        this.comment = comment;
        this.fivenums = fivenums;
        this.dyUserima = dyima;
        this.dyUserImag = dyimag;
        this.dyUserHeadimag = dyUserHeadimag;
    }
    //有可能弃用
    public Dynamic(String text,String dynamicUserName,String dytime,int duheadima,int dyima,Comment hotcomment,int[] fivenums,String dyimag,String dyUserHeadimag){
        this.text = text;
        this.dynamicUserName = dynamicUserName;
        this.dyTime = dytime;
        this.dyUserHeadima = duheadima;
        this.hotcomment = hotcomment;
        this.fivenums = fivenums;
        this.dyUserima = dyima;
        this.dyUserImag = dyimag;
        this.dyUserHeadimag = dyUserHeadimag;
    }
    /**构造函数传参
     * 动态一张图片*/
    public Dynamic(String dId,String accountId,int dCategory,String text,String dytime,int[] fivenums,String dyimag,List<Comment> comment,String dynamicUserName,String dyUserHeadimag){
        this.dId = dId;
        this.accountId = accountId;
        this.dCategory = dCategory;
        this.text = text;
        this.dyTime = dytime;
        this.fivenums = fivenums;
        this.dyUserImag = dyimag;
        this.comment = comment;
        this.dynamicUserName = dynamicUserName;
        this.dyUserHeadimag = dyUserHeadimag;
    }
    /**构造函数传参
     * 图片数组*/
    public Dynamic(String dId,String accountId,int dCategory,String text,String dytime,int[] fivenums,String[] dyimags,List<Comment> comment,String dynamicUserName,String dyUserHeadimag){
        this.dId = dId;
        this.accountId = accountId;
        this.dCategory = dCategory;
        this.text = text;
        this.dyTime = dytime;
        this.fivenums = fivenums;
        this.dyUserImags = dyimags;
        this.comment = comment;
        this.dynamicUserName = dynamicUserName;
        this.dyUserHeadimag = dyUserHeadimag;
    }

    public Dynamic(){

    }

    public String[] getDyUserImags() {
        return dyUserImags;
    }

    public void setDyUserImags(String[] dyUserImags) {
        this.dyUserImags = dyUserImags;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public int getdCategory() {
        return dCategory;
    }

    public void setdCategory(int dCategory) {
        this.dCategory = dCategory;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDyUserHeadimag() {
        return dyUserHeadimag;
    }

    public void setDyUserHeadimag(String dyUserHeadimag) {
        this.dyUserHeadimag = dyUserHeadimag;
    }

    public String getDyUserImag() {
        return dyUserImag;
    }

    public void setDyUserImag(String dyUserImag) {
        this.dyUserImag = dyUserImag;
    }

    public int getDyUserima() {
        return dyUserima;
    }

    public void setDyUserima(int dyUserima) {
        this.dyUserima = dyUserima;
    }

    public int getFivenums(int i) {
        return fivenums[i];
    }

    public int[] getFivenums() {
        return fivenums;
    }

    public void setFivenums(int[] fivenums) {
        this.fivenums = fivenums;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public int getDyUserHeadima() {
        return dyUserHeadima;
    }

    public void setDyUserHeadima(int dyUserHeadima) {
        this.dyUserHeadima = dyUserHeadima;
    }

    public String getDynamicUserName() {
        return dynamicUserName;
    }

    public void setDynamicUserName(String dynamicUserName) {
        this.dynamicUserName = dynamicUserName;
    }

    public String getDyTime() {
        return dyTime;
    }

    public void setDyTime(String dyTime) {
        this.dyTime = dyTime;
    }



    public void setText(String text) {
        this.text = text;
    }

    public String getText() {

        return text;
    }
}
