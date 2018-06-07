package com.TT.SincereAgree.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.MainActivity;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.mine.setting.UploadUtil;

import org.json.JSONObject;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;


public class RelationActivity extends AppCompatActivity {

    private ImageView relation1;
    private ImageView relation2;
    private ImageView relation3;
    private String select = "";
    private boolean succeed = false;
    private String accountid;
    private String sex;
    private String picUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        relation1 = (ImageView) findViewById(R.id.relastion_1);
        relation2 = (ImageView) findViewById(R.id.relastion_2);
        relation3 = (ImageView) findViewById(R.id.relastion_3);
        relation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "朋友";
                register();
            }
        });
        relation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "知己";
                register();
            }
        });
        relation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "亲密关系";
                register();
            }
        });
    }

    public void register(){
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String imagepath = intent.getStringExtra("imagepath");
        sex = intent.getBooleanExtra("sex",false) == true? "男":"女";
        String bsex = intent.getBooleanExtra("sex",false) == true? "1":"2";
        String nickname = intent.getStringExtra("nickname");
        String age = intent.getStringExtra("age");
        try{
            sendRequestWithHttpURLConnectionForToken(username,password,imagepath,bsex,nickname,age,select);
            if(succeed){
                Toast.makeText(RelationActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RelationActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
            }
            Thread.sleep(1000);
            Intent intent1 = null;
            if(succeed){
                intent1 = new Intent(RelationActivity.this, MainActivity.class);
                Configure.accountId = accountid;
                Configure.sex = sex;
                Configure.name = nickname;
                Configure.picUrl = picUrl;
            }

            else
                intent1 = new Intent(RelationActivity.this, LoginActivity.class);
            startActivity(intent1);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public void sendRequestWithHttpURLConnectionForToken(final String username,final String password,final String imagepath,
                                                         final String sex, final String nickname, final String age,final String relation) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username",username).add("password",password).
                            add("sex",sex).add("nickname",nickname).add("age",age).add("relation",relation).build();
                    Request request = new Request.Builder().url(rootUrl+"login/regiser").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 200){
                        succeed = false;
                    }else{
                        accountid = jSONObject.getJSONObject("data").getString("accountid");
                        picUrl = rootUrl+"/mine/home/profile?url="+jSONObject.getJSONObject("data").getString("picUrl");
                        Thread thread2 = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                String uploadurl = rootUrl+"/mine/setting/changeprofile/"+accountid;
                                try {
                                    File file = new File(imagepath);
                                    UploadUtil.uploadImage(file, uploadurl);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        thread2.start();
                        thread2.join();
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
