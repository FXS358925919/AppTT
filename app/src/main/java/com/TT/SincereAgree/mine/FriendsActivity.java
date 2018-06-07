package com.TT.SincereAgree.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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

public class FriendsActivity extends AppCompatActivity {
    private List<Friend> list = new ArrayList<Friend>();
    private String accountid;
    private String frienditem;
    private String rootUrl = Configure.rootUrl;
    private String responseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Intent intent = getIntent();
        accountid = intent.getStringExtra("accountid");
        frienditem = intent.getStringExtra("frienditem");
        initfriends();
        /*
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        */

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mine_friends_recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FriendsAdapter friendsAdapter = new FriendsAdapter(list);
        recyclerView.setAdapter(friendsAdapter);

    }

    private void initfriends() {
        try{
            sendRequestWithHttpURLConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void sendRequestWithHttpURLConnection() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mine/friends/" + accountid + "/" + frienditem).build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();

                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        t.join();
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData).getJSONObject("data");
            JSONArray jsonArray = jSONObject.getJSONArray("friends");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Friend friend = new Friend(object.getString("accountId"), object.getString("name"), object.getString("profile"),object.getString("sex").equals("1")?"男":"女");
                list.add(friend);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
