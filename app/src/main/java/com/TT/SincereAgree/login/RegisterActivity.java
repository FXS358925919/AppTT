package com.TT.SincereAgree.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RegisterActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button next;
    private boolean succeed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (TextView) findViewById(R.id.register_username_text);
        password = (TextView) findViewById(R.id.register_password_text);
        next = (Button) findViewById(R.id.register_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sendRequestWithHttpURLConnectionForToken(username.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(succeed){
                    String u  = username.getText().toString();
                    String p = password.getText().toString();
                    Intent intent = new Intent(RegisterActivity.this,RegisterActivityNext.class);
                    intent.putExtra("username",u);
                    intent.putExtra("password",p);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this,"账号已存在", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendRequestWithHttpURLConnectionForToken(final String username) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(Configure.rootUrl+"login/regtest/"+username).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("text",responseData);
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 200){
                        Log.e("sa","账号已存在");
                        succeed = false;
                    }else{
                        Log.e("sa","正确");
                        succeed = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }
}
