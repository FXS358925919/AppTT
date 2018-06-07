package com.TT.SincereAgree.amei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.TT.SincereAgree.R;

public class NavigateActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        findView();
        init();
    }

    private void findView(){
        loginButton = (Button) findViewById(R.id.signinbutton);
        registerButton = (Button) findViewById(R.id.registerbutton);
    }

    private void init(){
        loginButton.setOnClickListener(loginOnClickListener);
        registerButton.setOnClickListener(registerOnClickLister);

    }

    private OnClickListener loginOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_tosignin = new Intent(NavigateActivity.this,SigninActivity.class);
            startActivity(intent_tosignin);

        }
    };

    private OnClickListener registerOnClickLister = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_toregister = new Intent(NavigateActivity.this,RegisterActivity.class);
            startActivity(intent_toregister);
        }
    };

}
