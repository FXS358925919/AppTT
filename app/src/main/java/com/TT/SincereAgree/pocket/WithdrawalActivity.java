package com.TT.SincereAgree.pocket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class WithdrawalActivity extends AppCompatActivity {
    private int integralCount;
    private TextView withdrawText;
    private EditText withdrawEdit;
    private EditText alipayUsername;
    private Button button;
    private boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        Intent intent = getIntent();
        integralCount = intent.getIntExtra("integralCount",0);
        withdrawText = (TextView) findViewById(R.id.withdraw_integercount);
        String text = integralCount+"<small><small><small>积分</small></small></small>";
        withdrawText.setText(Html.fromHtml(text));
        withdrawEdit = (EditText) findViewById(R.id.pocket_edit_withdraw);
        alipayUsername = (EditText) findViewById(R.id.pocket_edit_apipay);
        button = (Button) findViewById(R.id.pocket_button_withdraw);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int withdrawCount = -1;
                try{
                    withdrawCount = Integer.parseInt(withdrawEdit.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(withdrawCount == -1)
                    Toast.makeText(WithdrawalActivity.this,"提现积分请输入数字",Toast.LENGTH_SHORT).show();
                else{
                    String username = alipayUsername.getText().toString();
                    try{
                        sendRequestWithHttpURLConnection(withdrawCount,username);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final String s = success? "提现成功，3到5个工作日即会到账！":"提现失败";
                    if(success){
                        String text = integralCount+"<small><small><small>积分</small></small></small>";
                        withdrawText.setText(Html.fromHtml(text));
                    }
                    final AlertDialog ad = new AlertDialog.Builder(WithdrawalActivity.this).setMessage(s).show();
                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            ad.dismiss();
                        }
                    };
                    hander.postDelayed(runnable, 1 * 1000);
                }

            }
        });
    }

    public void sendRequestWithHttpURLConnection(final int num,final String username) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("num",num+"").add("username",String.valueOf(username)).build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/withdraw").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 100){
                        integralCount -= num;
                        success = true;
                    }else
                        success = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }
}
