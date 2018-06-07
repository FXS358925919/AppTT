package com.TT.SincereAgree.pocket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.chat.ChatGift;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.accountId;
import static com.TT.SincereAgree.Configure.rootUrl;

public class ExchangeActivity extends AppCompatActivity {
    private List<ChatGift> list_exchange = new ArrayList<ChatGift>();
    private PocketGiftAdapter adapter_exchange;
    private boolean isIntegral = true;
    private TextView balanceText;
    private int integral;
    private int sincerity;
    public TextView integralandsincerity;
    private boolean isPause = false;
    //adapter向ExchangeActivity发送消息,然后重新获取积分，并更新UI
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0 :
                    sendRequestWithRefresh();
                    String text = integral+"<small><small><small>积分</small></small></small>/"+sincerity+"<small><small><small>诚意分</small></small></small>";
                    integralandsincerity.setText(Html.fromHtml(text));
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        integral = intent.getIntExtra("integral",0);
        sincerity = intent.getIntExtra("sincerity",0);
        setContentView(R.layout.activity_exchange);
        integralandsincerity = (TextView) findViewById(R.id.exchange_integralandsincerity);
        String text = integral+"<small><small><small>积分</small></small></small>/"+sincerity+"<small><small><small>诚意分</small></small></small>";
        integralandsincerity.setText(Html.fromHtml(text));
        initIntegralGifts();
        RecyclerView recyclerView_exchange = (RecyclerView) findViewById(R.id.pocket_recycler_view_exchange);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView_exchange.setLayoutManager(manager);
        adapter_exchange = new PocketGiftAdapter(list_exchange,handler);
        recyclerView_exchange.setAdapter(adapter_exchange);

    }

    private void initIntegralGifts() {
        list_exchange.clear();
        try{
            sendRequestWithHttpURLConnectionForToken();
        }catch(Exception e){
            e.printStackTrace();
        }

        /*list_exchange.add(new Gift("100",R.drawable.coffee3x));
        list_exchange.add(new Gift("200",R.drawable.rocket3x));
        list_exchange.add(new Gift("300",R.drawable.bear3x));
        list_exchange.add(new Gift("400",R.drawable.rose3x));
        list_exchange.add(new Gift("550",R.drawable.redcar3x));
        list_exchange.add(new Gift("600",R.drawable.cake3x));
        list_exchange.add(new Gift("400",R.drawable.ring3x));
        list_exchange.add(new Gift("560",R.drawable.lollipop2x));*/
    }



    public void sendRequestWithHttpURLConnectionForToken() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl+"pocket/gift/allgifts").build();
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
                list_exchange.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequestWithRefresh() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/pocket/home/" + accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObjectRefresh(responseData);
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

    private void parseJSONWithJSONObjectRefresh(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            //JSONArray jsonArray = new JSONArray(responseData);
            if(jSONObject.getInt("code") == 200)
                return;
            //userInfo = (Map<String, String>) jSONObject.getJSONObject("data");
            JSONObject newjSONObject = jSONObject.getJSONObject("data").getJSONObject("wallet");
            integral = Integer.parseInt(newjSONObject.getString("integral"));
            sincerity = Integer.parseInt(newjSONObject.getString("sincerity"));
            /*String text = integral+"<small><small><small>积分</small></small></small>/"+sincerity+"<small><small><small>诚意分</small></small></small>";
            integralandsincerity.setText(Html.fromHtml(text));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true; //记录页面已经被暂停
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause){ //判断是否暂停
            isPause = false;
            sendRequestWithRefresh();
        }

    }
}
