package com.TT.SincereAgree.chat;

import android.content.Context;
import android.view.View;

import com.TT.SincereAgree.Configure;

import org.json.JSONObject;

import java.io.IOException;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.accountId;
import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-05-19.
 */

public class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
    private String sex;

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        Configure.chatToId = s;
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        //每次开启聊天窗口就初始化sendCount和receiveCount
        Configure.sendCount = 0;
        Configure.receiveCount = 0;
        Configure.chatToId = s;
        sendRequestWithHttpURLConnection();
        if(Configure.sex.equals("女")&&sex.equals("男"))
            Configure.reduceIntegral = false;
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    //拦截最近聊天列表的点击事件
    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        Configure.sendCount = 0;
        Configure.receiveCount = 0;
        Configure.chatToId = uiConversation.getConversationTargetId();
        sendRequestWithHttpURLConnection();
        if(Configure.sex.equals("女")&&sex.equals("男"))
            Configure.reduceIntegral = false;
        //RongIM.getInstance().startPrivateChat(context,Configure.chatToId,"聊天中");
        return false;
    }

    private void sendRequestWithHttpURLConnection() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mine/home/" + accountId).build();
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
                return;
            //userInfo = (Map<String, String>) jSONObject.getJSONObject("data");
            JSONObject newjSONObject = jSONObject.getJSONObject("data").getJSONObject("userinfo");
            sex = newjSONObject.getString("sex").equals("1")?"男":"女";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
