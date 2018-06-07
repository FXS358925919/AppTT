package com.TT.SincereAgree.util;

import com.TT.SincereAgree.amei.entity.SquareDate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-04-28.
 */

public class SquareDateThreadHelper {
    public List<SquareDate> list = new ArrayList<SquareDate>();
    public void sendRequestForSquareDate() {
        try{
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(rootUrl + "/mine/publishdate").build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        parseJSONForSquareDate(responseData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
            t.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void parseJSONForSquareDate(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData).getJSONObject("data");
            JSONArray jsonArray = jSONObject.getJSONArray("squaredates");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String[] strs = object.getString("theme").split(",");
                int length = strs.length;
                if(length < 4)
                    strs = new String[4];
                Date dynamicReleaseDay = new Date(object.getLong("time"));
                long minutes = (new Date().getTime()-dynamicReleaseDay.getTime())/(1000*60);
                String dynamicReleaseTime = minutes+"分钟前";
                if(minutes > 60){
                    minutes = minutes/60;
                    dynamicReleaseTime = minutes+"小时前";
                }
                if(minutes > 24){
                    minutes = minutes/24;
                    dynamicReleaseTime = minutes+"天前";
                }
                String sex = object.getString("sex") == "1" ? "男":"女";
                SquareDate date = new SquareDate(object.getString("profile"),object.getString("name"),dynamicReleaseTime, object.getString("mainpicture"),
                        sex,object.getString("age"),object.getString("hight")+"cm",object.getString("weight")+"kg",object.getString("maintext"),strs[0],strs[1],strs[2],strs[3],
                        object.getString("location"),dynamicReleaseDay);
                list.add(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
