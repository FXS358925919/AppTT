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
import com.TT.SincereAgree.MainActivity;
import com.TT.SincereAgree.R;

import org.json.JSONObject;

import java.security.MessageDigest;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

public class LoginActivity extends AppCompatActivity {
    private TextView usernameText;
    private TextView passwordText;
    private Button loginButton;
    private TextView regText;
    private TextView forgetText;
    private boolean succeed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (TextView) findViewById(R.id.login_username);
        passwordText = (TextView) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_loginbutton);
        regText = (TextView) findViewById(R.id.login_register);
        forgetText = (TextView) findViewById(R.id.login_forgetpass);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sendRequestWithHttpURLConnectionForToken(usernameText.getText().toString(),passwordText.getText().toString());
                    if(succeed){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else
                        Toast.makeText(LoginActivity.this,"账号密码错误", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    public void sendRequestWithHttpURLConnectionForToken(final String username, final String password) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username",username).add("password",password).build();
                    Request request = new Request.Builder().url(rootUrl+"login/identify").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("text",responseData);
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 200){
                        Log.e("sa","账号密码错误");
                        succeed = false;
                    }
                    else{
                        Log.e("sa","正确");
                        succeed = true;
                        Configure.accountId = jSONObject.getJSONObject("data").getString("accountid");
                        Configure.sex = jSONObject.getJSONObject("data").getString("sex");
                        Configure.name = jSONObject.getJSONObject("data").getString("name");
                        Configure.picUrl = rootUrl+"/mine/home/profile?url="+jSONObject.getJSONObject("data").getString("picurl");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
