package com.TT.SincereAgree.amei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TT.SincereAgree.R;

public class RegisterActivity extends AppCompatActivity {
    private Button nextrebutton;
    private EditText inputphone;
    private EditText inputpassword;
    private String userphone;
    private String userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        findview();
        init();
    }
    private void findview(){
        nextrebutton = (Button)findViewById(R.id.nextrebutton);
        inputphone = (EditText) findViewById(R.id.inputphone);
        inputpassword = (EditText)findViewById(R.id.inputpassword);
    }
    private void init(){
        nextrebutton.setOnClickListener(nextreOnClickLister);

    }
    private View.OnClickListener nextreOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userphone = inputphone.getText().toString();
            userpassword = inputpassword.getText().toString();
            Log.e("------",userphone);
            //处理注册用户信息进程

            if (userpassword.equals("")||userphone.equals("")||userphone == null||userpassword == null)
            {
                Toast.makeText(RegisterActivity.this,"手机号或密码不能为空",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                register();
            }

        }
    };

    //注册数据处理
    private void register(){
        Intent intent_next = new Intent(RegisterActivity.this,RegisterTwoActivity.class);
        startActivity(intent_next);
    }
}
