package com.TT.SincereAgree.pocket;

/**
 * Created by 冯雪松 on 2018-04-24.
 */

public class BillItem {
    private int imageId;
    private String name;

    public BillItem(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
