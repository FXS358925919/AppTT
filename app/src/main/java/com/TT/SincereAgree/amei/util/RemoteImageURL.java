package com.TT.SincereAgree.amei.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by Amei on 2017/11/19.
 */

public class RemoteImageURL implements ImageURL{
    private List<String> imgUrls = new ArrayList<>();
    @Override
    public List<String> getURL(final String userPhone) {
     new Thread(new Runnable() {
      @Override
      public void run() {
       try {
        /**
         * 根据url获取服务器的json数据responseData
         */
        String url = "image/getImgsByUser/"+userPhone;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(rootUrl + url).build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        imgUrls = parseJSONWithJSONArray(responseData);
       }catch (IOException e) {
        e.printStackTrace();
       }
      }
     }).start();
     return imgUrls;
    }

 private static List<String> parseJSONWithJSONArray(String responseData) {
  List<String> urlList = new ArrayList<>();
  try {
   JSONObject jSONObject = new JSONObject(responseData);
   if(jSONObject.getInt("code") == 200)
    return null;

   JSONObject jsondata = jSONObject.getJSONObject("data");
   //所有语音动态相关数据
   JSONArray voiceArry = jsondata.getJSONArray("imgurls");
   for (int i = 0; i<voiceArry.length();i++){
    String jsonsigle = voiceArry.getString(i);//单条语音相关动态
    urlList.add(jsonsigle.toString());
   }
  }catch (Exception e) {
   e.printStackTrace();
  }
  return urlList;
 }
}
