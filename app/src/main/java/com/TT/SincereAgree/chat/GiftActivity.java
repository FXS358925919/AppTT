package com.TT.SincereAgree.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class GiftActivity extends AppCompatActivity {
    private List<ChatGift> list_my = new ArrayList<ChatGift>();
    private List<ChatGift> list_hot = new ArrayList<ChatGift>();
    private GiftAdapter adapter_my;
    private GiftHotAdapter adapter_hot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gift);
        //我的礼物
        setMyGifts();
        RecyclerView recyclerView_my = (RecyclerView) findViewById(R.id.chat_gift_recycler_view_my);
        GridLayoutManager manager_my = new GridLayoutManager(this, 4);
        recyclerView_my.setLayoutManager(manager_my);
        adapter_my = new GiftAdapter(list_my);
        recyclerView_my.setAdapter(adapter_my);

        //热门礼物
        setHotGifts();
        RecyclerView recyclerView_hot = (RecyclerView) findViewById(R.id.chat_gift_recycler_view_hot);
        GridLayoutManager manager_hot = new GridLayoutManager(this, 4);
        recyclerView_hot.setLayoutManager(manager_hot);
        adapter_hot = new GiftHotAdapter(list_hot);
        recyclerView_hot.setAdapter(adapter_hot);
    }

    public void setMyGifts(){
        list_my.clear();
        try{
            sendRequestWithHttpURLConnectionForMine();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setHotGifts(){
        list_hot.clear();
        try{
            sendRequestWithHttpURLConnectionForHot();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendRequestWithHttpURLConnectionForHot() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl+"pocket/gift/allgifts").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObjectForHot(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

    private void parseJSONWithJSONObjectForHot(String responseData) {
        try {JSONObject jSONObject = new JSONObject(responseData).getJSONObject("data");
            JSONArray jsonArray = jSONObject.getJSONArray("gifts");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ChatGift gift = new ChatGift(object.getString("pictureurl"),object.getString("gName"),
                        Integer.parseInt(object.getString("gId")),Integer.parseInt(object.getString("integral")),
                        Integer.parseInt(object.getString("sincererity")),1);
                list_hot.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequestWithHttpURLConnectionForMine() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl+"mine/gift/"+ Configure.accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObjectForMine(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

    private void parseJSONWithJSONObjectForMine(String responseData) {
        try {JSONObject jSONObject = new JSONObject(responseData).getJSONObject("data");
            JSONArray jsonArray = jSONObject.getJSONArray("gifts");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ChatGift gift = new ChatGift(object.getString("pictureurl"),object.getString("gName"),
                        Integer.parseInt(object.getString("gId")),Integer.parseInt(object.getString("integral")),
                        Integer.parseInt(object.getString("sincererity")),Integer.parseInt(object.getString("count")));
                list_my.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
