package com.TT.SincereAgree.pocket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.chat.ChatGift;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class MyGiftActivity extends AppCompatActivity {
    private List<ChatGift> list_hot = new ArrayList<ChatGift>();
    private MyGiftAdapter adapter_hot;
    private Button exchangeButton;
    private String[] exchanges = new String[]{"积分", "诚意分"};
    private MyGiftActivity.ButtonOnClick buttonOnClick = new MyGiftActivity.ButtonOnClick(0);
    private class ButtonOnClick implements DialogInterface.OnClickListener {
        private boolean b = false;
        private int index; // 表示选项的索引
        public ButtonOnClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which >= 0) {
                //如果单击的是列表项，将当前列表项的索引保存在index中。
                //如果想单击列表项后关闭对话框，可在此处调用dialog.cancel()
                //或是用dialog.dismiss()方法。
                index = which;
            } else {
                //用户单击的是【确定】按钮
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    boolean indexB = index == 0 ? true:false;
                    try{
                        sendRequestWithHttpURLConnectionForToken(-1,-1,indexB);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final String s = b? "兑换成功！":"兑换失败";
                    final AlertDialog ad = new AlertDialog.Builder(MyGiftActivity.this).setMessage(s).show();
                    //显示用户选择的是第几个列表项。
                    //3秒钟后自动关闭。
                    index = 0;
                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            ad.dismiss();

                        }
                    };

                    hander.postDelayed(runnable, 1 * 1000);
                }
                if(buttonOnClick.b){
                    list_hot.clear();
                    adapter_hot.notifyDataSetChanged();
                }
                //用户单击的是【取消】按钮
                else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                }
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket_gift);
        setHotGifts();
        RecyclerView recyclerView_hot = (RecyclerView) findViewById(R.id.pocket_gift_recycler_view_hot);
        GridLayoutManager manager_hot = new GridLayoutManager(this, 4);
        recyclerView_hot.setLayoutManager(manager_hot);
        adapter_hot = new MyGiftAdapter(list_hot,MyGiftActivity.this.getBaseContext());
        recyclerView_hot.setAdapter(adapter_hot);
        exchangeButton = (Button) findViewById(R.id.mygift_exchange_all);
        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_hot.size() == 0){
                    Toast.makeText(MyGiftActivity.this,"您没有礼物可以兑换",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyGiftActivity.this);
                    builder.setTitle("请选择兑换方式");
                    builder.setSingleChoiceItems(exchanges, 0, buttonOnClick);
                    builder.setPositiveButton("确定", buttonOnClick);
                    builder.setNegativeButton("取消", buttonOnClick);
                    builder.show();

                }

            }
        });
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
                        Integer.parseInt(object.getString("sincererity")),Integer.parseInt(object.getString("count")));
                list_hot.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequestWithHttpURLConnectionForToken(final int giftid,final int num,final boolean isintegeral) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("giftid",giftid+"").add("num",num+"").add("isintegeral",String.valueOf(isintegeral)).build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/gift/exchangeforintegral").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 100)
                        buttonOnClick.b = true;
                    else
                        buttonOnClick.b = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }
}
