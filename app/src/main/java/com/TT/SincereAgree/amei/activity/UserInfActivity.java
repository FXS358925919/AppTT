package com.TT.SincereAgree.amei.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.dao.UserIfoDao;
import com.TT.SincereAgree.amei.entity.User;

public class UserInfActivity extends AppCompatActivity {
    public UserIfoDao userIfoDao;
    private TextView userName;
    private TextView userHeight;
    private TextView userJob;
    private TextView userBirth;
    private TextView userCity;
    private TextView userSanwei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_inf);
        findview();
        init();
    }

    private void findview(){
        userName = (TextView)findViewById(R.id.uinfm_username);
        userHeight = (TextView)findViewById(R.id.uinfm_textView6);
        userJob = (TextView)findViewById(R.id.uinfm_textView12);
        userBirth = (TextView)findViewById(R.id.uinfm_textView8);
        userCity = (TextView)findViewById(R.id.uinfm_textView14);
        userSanwei = (TextView)findViewById(R.id.uinfm_textView10);
    }

    private void init(){
        userIfoDao = new UserIfoDao();
        User user = new User();
        user = userIfoDao.userIfo("");

        if (user.getNickname() == null||user.getNickname().equals(""))
        {
            userName.setText("未填");
        }else
        {
            userName.setText(user.getNickname());
        }
        if (user.getCity() == null||user.getCity().equals(""))
        {
            userCity.setText("未填");
        }else
        {
            userCity.setText(user.getCity());
        }
        if (user.getHeight() == null||user.getHeight().equals(""))
        {
            userHeight.setText("未填");
        }else
        {
            userHeight.setText(user.getHeight());
        }

        if (user.getJob() == null||user.getJob().equals(""))
        {
            userJob.setText("未填");
        }else
        {
            userJob.setText(user.getJob());
        }
        if (user.getBirthday() == null||user.getBirthday().equals(""))
        {
            userBirth.setText("未填");
        }else
        {
            userBirth.setText(user.getBirthday());
        }
        if (user.getThreenumber() == null||user.getThreenumber().equals(""))
        {
            userSanwei.setText("未填");
        }else
        {
            userSanwei.setText(user.getThreenumber());
        }

    }
}
