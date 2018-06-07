package com.TT.SincereAgree.chat;

/**
 * Created by 冯雪松 on 2018-03-28.
 */

public class ChatGift {
    private String url;
    private String name;
    private int id;
    private int inNum;//积分
    private int sinNum;//诚意分
    private int count;

    public ChatGift(String url, String name, int id, int inNum, int sinNum, int count) {
        this.url = url;
        this.name = name;
        this.id = id;
        this.inNum = inNum;
        this.sinNum = sinNum;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInNum() {
        return inNum;
    }

    public void setInNum(int inNum) {
        this.inNum = inNum;
    }

    public int getSinNum() {
        return sinNum;
    }

    public void setSinNum(int sinNum) {
        this.sinNum = sinNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
