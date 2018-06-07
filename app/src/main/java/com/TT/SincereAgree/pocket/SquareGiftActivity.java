package com.TT.SincereAgree.pocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.chat.ChatGift;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class SquareGiftActivity extends AppCompatActivity {
    private List<ChatGift> list_hot = new ArrayList<ChatGift>();
    private SquareGiftAdapter adapter_hot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_gift);
        setHotGifts();
        RecyclerView recyclerView_hot = (RecyclerView) findViewById(R.id.square_gift_list);
        GridLayoutManager manager_hot = new GridLayoutManager(this, 4);
        recyclerView_hot.setLayoutManager(manager_hot);
        Intent intent = getIntent();
        String toid = intent.getStringExtra("toid");
        String dyId = intent.getStringExtra("dyId");
        String newGiftNum = intent.getStringExtra("newGiftNum");
        String type = intent.getStringExtra("type");
        adapter_hot = new SquareGiftAdapter(list_hot,toid,dyId,newGiftNum,type);
        recyclerView_hot.setAdapter(adapter_hot);
    }

    public void setHotGifts(){
        list_hot.clear();
        try{
            sendRequestWithHttpURLConnectionForToken();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void sendRequestWithHttpURLConnectionForToken() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl+"mine/gift/"+ Configure.accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

    private void parseJSONWithJSONObject(String responseData) {
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
}
