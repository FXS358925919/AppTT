package com.TT.SincereAgree.amei.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amei on 2017/11/15.
 */

public class UserGift {
    private  List gift = new ArrayList();

    public void setGift(List gift) {
        this.gift = gift;
    }

    public List getGift() {

        return gift;
    }
}
