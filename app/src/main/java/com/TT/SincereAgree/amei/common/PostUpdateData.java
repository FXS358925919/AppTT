package com.TT.SincereAgree.amei.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Amei on 2018/5/8.
 */

public class PostUpdateData {
    /**更新动态的状态*/
    private static int state = -1;

    public static int postUpdate(HashMap<String,String> map,String posturl){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder params = new FormBody.Builder();
            for(Map.Entry<String, String> entry : map.entrySet()){
                params.add(entry.getKey(),entry.getValue());
            }
            Request request = new Request.Builder().url(posturl).post(params.build()).build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    /**响应失败*/
                    System.out.println("-------------更新网络响应失败！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    /**响应成功*/
                    state = 1;
                    System.out.println("-------更新网络响应成功--------"+response.body().toString());
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
