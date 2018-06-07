package com.TT.SincereAgree.amei.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amei on 2017/11/6.
 */

public class SigninActivity extends Activity{
    private Button signinButton;
    private EditText signinPhone;
    private EditText signinPassword;
    private TextView toRegister;
    private String USERID;
    private String PWD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        findView();
        init();
    }

    private void findView(){
        signinButton = (Button)findViewById(R.id.signinbutton);
        signinPhone = (EditText)findViewById(R.id.signinphone);
        signinPassword = (EditText)findViewById(R.id.signinpassword);
        toRegister = (TextView) findViewById(R.id.toregister);

    }

    private void init(){
        signinButton.setOnClickListener(signinOnClickListener);
        toRegister.setOnClickListener(toregisterOnClickLister);

    }

    private View.OnClickListener signinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            USERID = signinPhone.getText().toString();
            PWD = signinPassword.getText().toString();
            Log.e("--------------------",USERID);
            if (USERID.equals("") || PWD.equals("")||USERID == null||PWD== null) {
                Toast.makeText(SigninActivity.this, "用户名或密码不能为空",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                login();
                //t.start();
            }
        }
    };

    private View.OnClickListener toregisterOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_toregister = new Intent(SigninActivity.this,RegisterActivity.class);
            startActivity(intent_toregister);
        }
    };

    private void login(){
        if (USERID.equals("amei") && PWD.equals("123456")) {
            Intent intent_sginin = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent_sginin);}
        else {
            handler.sendEmptyMessage(2);
        }
    }

    /*
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            if (USERID.equals("amei") && PWD.equals("123456")) {
                    Intent intent_sginin = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent_sginin);}
            else {
                handler.sendEmptyMessage(2);
            }

        }
    });*/

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //LoginTask task = new LoginTask(SigninActivity.this);
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("screenName", USERID);
                parameters.put("password",PWD);
                //task.execute(parameters);
            }
            if (msg.what == 2) {
                Toast.makeText(SigninActivity.this, "登陆失败", Toast.LENGTH_SHORT)
                        .show();
            } else if (msg.what == 3) {
                Toast.makeText(SigninActivity.this, "用户名或密码不能为空",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };


}
