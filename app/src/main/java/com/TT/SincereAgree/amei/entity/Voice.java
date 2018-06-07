package com.TT.SincereAgree.amei.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amei on 2017/12/20.
 */

public class Voice implements Serializable{
    private String vId;
    private String accountId;
    private int dCategory;
    /**语音发布的时间*/
    private String dyTime;
    /**语音时长*/
    private int voitime;
    /**可听的比例*/
    private int audper;
    /**语音发布的用户名*/
    private String voiceUserName;
    private String voUserHeadimag;//语音动态用户的头像
    private int[] fivenums = new int[5];
    /**用户评级满5星*/
    private double raratingStart;
    /**语音存放的地址*/
    private String voiceUrl;
    /**评论*/
    private List<Comment> comment;


    private String voicetime;
    private String auditableper;
    private int dyUserHeadima;
    private int dyUserima;//会弃用

    public Voice(){}

    /**
     * 构造函数
     * @param dyTime
     * @param voicetime
     * @param percent
     * @param voiceUserName
     * @param voUserHeadimag
     * @param start
     * @param voiceUrl
     * @param comment
     * @param fivenums
     */
    public Voice(String vId,String accountId,int dCategory,String dyTime,int voicetime,int percent,String voiceUserName,
                 String voUserHeadimag,double start,String voiceUrl,List<Comment> comment,
                 int[] fivenums){
        this.vId = vId;
        this.accountId = accountId;
        this.dCategory = dCategory;
        this.dyTime = dyTime;
        this.voitime = voicetime;
        this.audper = percent;
        this.voiceUserName = voiceUserName;
        this.voUserHeadimag = voUserHeadimag;
        this.raratingStart = start;
        this.voiceUrl = voiceUrl;
        this.comment = comment;
        this.fivenums = fivenums;
    }


    public Voice(String voicetime,String auditableper,String voiceUserName,String dytime,int duheadima,int dyima,List<Comment> comment,int[] fivenums,double start){
        this.voicetime = voicetime;
        this.auditableper = auditableper;

        this.voiceUserName = voiceUserName;
        this.dyTime = dytime;
        this.dyUserHeadima = duheadima;
        this.comment = comment;
        this.fivenums = fivenums;
        this.dyUserima = dyima;
        this.raratingStart = start;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public int getAudper() {
        return audper;
    }

    public void setAudper(int audper) {
        this.audper = audper;
    }

    public int getVoitime() {
        return voitime;
    }

    public void setVoitime(int voitime) {
        this.voitime = voitime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getdCategory() {
        return dCategory;
    }

    public void setdCategory(int dCategory) {
        this.dCategory = dCategory;
    }

    public String getVoiceUserName() {
        return voiceUserName;
    }

    public void setVoiceUserName(String voiceUserName) {
        this.voiceUserName = voiceUserName;
    }

    public String getVoUserHeadimag() {
        return voUserHeadimag;
    }

    public void setVoUserHeadimag(String voUserHeadimag) {
        this.voUserHeadimag = voUserHeadimag;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public double getRaratingStart() {
        return raratingStart;
    }

    public void setRaratingStart(double raratingStart) {
        this.raratingStart = raratingStart;
    }

    public String getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(String voicetime) {
        this.voicetime = voicetime;
    }

    public String getAuditableper() {
        return auditableper;
    }

    public void setAuditableper(String auditableper) {
        this.auditableper = auditableper;
    }

    public String getDynamicUserName() {
        return voiceUserName;
    }

    public void setDynamicUserName(String dynamicUserName) {
        this.voiceUserName = dynamicUserName;
    }

    public String getDyTime() {
        return dyTime;
    }

    public void setDyTime(String dyTime) {
        this.dyTime = dyTime;
    }

    public int getDyUserHeadima() {
        return dyUserHeadima;
    }

    public void setDyUserHeadima(int dyUserHeadima) {
        this.dyUserHeadima = dyUserHeadima;
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
}
