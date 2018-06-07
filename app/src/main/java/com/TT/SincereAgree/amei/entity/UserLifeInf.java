package com.TT.SincereAgree.amei.entity;

import lombok.Data;

/**
 * Created by Amei on 2017/11/15.
 */
@Data
public class UserLifeInf {
    private String activity;
    private String food;
    private String goods;
    private String leifure;
    private String Genderconcept;//
    private String marry;//已婚、未婚

    public void setUserLifeInf(UserLifeInf source){
        /*if (source != null)
        {
            if (source.getActivity() != null)
                this.setActivity(source.getActivity());
            if (source.getFood() != null)
                this.setFood(source.getFood());
            if (source.getGoods() != null)
                this.setGoods(source.getGoods());
            if (source.getGenderconcept() != null)
                this.setGenderconcept(source.getGenderconcept());
            if (source.getMarry() != null)
                this.setMarry(source.getMarry());
            if (source.getLeifure() != null)
                this.setLeifure(source.getLeifure());

        }*/

    }
}
