package com.TT.SincereAgree.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.TT.SincereAgree.R;

public class SettingActivity extends AppCompatActivity {
    LinearLayout changeprofile;
    LinearLayout logout;
    LinearLayout quit;
    String profileurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        changeprofile = (LinearLayout) findViewById(R.id.setting_profile);
        logout = (LinearLayout) findViewById(R.id.setting_logout);
        quit = (LinearLayout) findViewById(R.id.setting_quit);
        profileurl = getIntent().getStringExtra("profileurl");
        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ChangeProfileActivity.class);
                intent.putExtra("profileurl",profileurl);
                startActivity(intent);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, QuitActivity.class);
                intent.putExtra("isExist",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                SettingActivity.this.startActivity(intent);
                System.exit(0);
                /*Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//别忘了这行，否则退出不起作用

                intent.setClass(getApplicationContext(), QuitActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
