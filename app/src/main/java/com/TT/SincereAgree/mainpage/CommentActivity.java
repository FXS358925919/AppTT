package com.TT.SincereAgree.mainpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class CommentActivity extends AppCompatActivity {
    private List<InformationComment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initComments();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.information_content_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(list);
        recyclerView.setAdapter(commentAdapter);
    }

    private void initComments() {
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
                    Request request = new Request.Builder().url(rootUrl + "/mainpage/getComments/" + Configure.accountId + "/0/10").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
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
            JSONArray jsonArray = jSONObject.getJSONArray("comments");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject comment = object.getJSONObject("comment");
                JSONObject dynamic = object.getJSONObject("dynamic");
                JSONObject userinfo = object.getJSONObject("userinfo");
                Date commentReleaseDay = new Date(comment.getLong("releasedate"));
                long minutes = (new Date().getTime()-commentReleaseDay.getTime())/(1000*60);
                String commentReleaseTime = minutes+"分钟前";
                if(minutes > 60){
                    minutes = minutes/60;
                    commentReleaseTime = minutes+"小时前";
                }
                if(minutes > 24){
                    minutes = minutes/24;
                    commentReleaseTime = minutes+"天前";
                }
                InformationComment newComment = new InformationComment(userinfo.getString("profile"),userinfo.getString("name"),
                        commentReleaseTime,comment.getString("content"),dynamic.getString("dyima"),dynamic.getString("dContent"));
                list.add(newComment);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
