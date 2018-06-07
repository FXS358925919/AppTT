package com.TT.SincereAgree.pocket;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.TT.SincereAgree.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.accountId;
import static com.TT.SincereAgree.Configure.rootUrl;

public class BillActivity extends AppCompatActivity {
    List<BillItem> vals = new ArrayList<BillItem>();
    private List<Bill> bills = new ArrayList<Bill>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initVals();
        getBills();
        if(bills.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("您没有账单数据");
            //点击对话框以外的区域是否让对话框消失
            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pocket_recycler_view_bill);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BillsAdapter friendsAdapter = new BillsAdapter(bills);
        recyclerView.setAdapter(friendsAdapter);
    }

    private void initVals(){
        vals.add(new BillItem(R.drawable.recharge3x,"充值获得"));
        vals.add(new BillItem(R.drawable.giftreward3x,"兑换礼物"));
        vals.add(new BillItem(R.drawable.recharge3x,"红包获得"));
        vals.add(new BillItem(R.drawable.giftreward3x,"兑换礼物"));
        vals.add(new BillItem(R.drawable.giftreward3x,"发送红包"));
        vals.add(new BillItem(R.drawable.giftreward3x,"发送红包"));
    }

    private void getBills() {
        bills.clear();
        try{
            sendRequestWithHttpURLConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*bills.add(new Bill("签到",R.drawable.sign3x,+10,new Date()));
        bills.add(new Bill("聊天获得",R.drawable.chatget3x,+10,new Date()));
        bills.add(new Bill("礼物打赏",R.drawable.giftreward3x,+100,new Date()));
        bills.add(new Bill("分享",R.drawable.share3x,+10,new Date()));
        bills.add(new Bill("充值",R.drawable.recharge3x,+10,new Date()));*/
    }

    private void sendRequestWithHttpURLConnection() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/pocket/bill/" + accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        t.join();
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            //JSONArray jsonArray = new JSONArray(responseData);
            if(jSONObject.getInt("code") == 200)
                return;
            //userInfo = (Map<String, String>) jSONObject.getJSONObject("data");
            JSONArray jsonArray  = jSONObject.getJSONObject("data").getJSONArray("bills");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int category = object.getInt("category");
                Long d = object.getLong("date");
                Date date = new Date(d);
                BillItem item = vals.get(category);
                Bill bill = new Bill(item.getName(),item.getImageId(),object.getInt("income"),date);
                bills.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
