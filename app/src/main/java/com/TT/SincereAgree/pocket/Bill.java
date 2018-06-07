package com.TT.SincereAgree.pocket;

import java.util.Date;

/**
 * Created by 冯雪松 on 2017-09-11.
 */

public class Bill {
    private String name;
    private int imageId;
    private int income;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bill(String name, int imageId, int income, Date date) {
        this.name = name;
        this.imageId = imageId;
        this.income = income;
        this.date = date;
    }
}
