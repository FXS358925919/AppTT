package com.TT.SincereAgree.chat;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-04-04.
 */

public class ChatUtil {
    public static void sendRequestWithHttpURLConnectionForToken(final String userid,final String nums, final boolean isSincerity) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", userid)
                            .add("nums",nums).add("isSincerity",String.valueOf(isSincerity)).build();
                    Request request = new Request.Builder().url(rootUrl+"chat/updateReadPacket").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void addGiftForUser(final String toid, final int giftid, final String dyid,final String fromid){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("toid", toid)
                            .add("giftid",String.valueOf(giftid)).add("dyid",dyid).add("fromid",fromid).build();
                    Request request = new Request.Builder().url(rootUrl+"chat/gift/addGift").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void deleteGiftForUser(final String toid, final int giftid, final String dyid,final String fromid){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("toid", toid)
                            .add("giftid",String.valueOf(giftid)).add("dyid",dyid).add("fromid",fromid).build();
                    Request request = new Request.Builder().url(rootUrl+"chat/gift/removeGift").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
