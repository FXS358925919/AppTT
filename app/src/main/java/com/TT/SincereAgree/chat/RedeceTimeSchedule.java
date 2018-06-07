package com.TT.SincereAgree.chat;

import com.TT.SincereAgree.Configure;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-05-22.
 */

public class RedeceTimeSchedule {
    //发送一条消息多少积分
    public int sendIntegralTime = 2;
    public Timer timer = new Timer();
    public TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(Configure.sendValueCount > 0)
                sendRequestWithHttpURLConnection(Configure.sendValueCount * sendIntegralTime);
            if(!Configure.lackIntegeral)
                Configure.sendValueCount = 0;
        }
    };

    public void start() {
        timer.schedule(timerTask,new Date(),60000);
    }

    private void sendRequestWithHttpURLConnection(final int num) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("num",num+"").build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/reduceChatIntegral").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            //JSONArray jsonArray = new JSONArray(responseData);
            if(jSONObject.getInt("code") == 200)
                Configure.lackIntegeral = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
