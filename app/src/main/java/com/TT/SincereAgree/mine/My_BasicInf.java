package com.TT.SincereAgree.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import org.json.JSONObject;

public class My_BasicInf extends AppCompatActivity {
    private TextView name;
    private TextView city;
    private TextView sex;
    private TextView height;
    private TextView weight;
    private TextView email;
    private TextView phonenum;
    private TextView likesports;
    private TextView sexinfo;
    private TextView merriage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__basic_inf);
        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userinfo");
        JSONObject userInfoJson = null;
        name = (TextView) findViewById(R.id.mine_basic_inf_name);
        city = (TextView) findViewById(R.id.mine_basic_inf_city);
        sex = (TextView) findViewById(R.id.mine_basic_inf_sex);
        height = (TextView) findViewById(R.id.mine_basic_inf_height);
        weight = (TextView) findViewById(R.id.mine_basic_inf_weight);
        email = (TextView) findViewById(R.id.mine_basic_inf_email);
        phonenum = (TextView) findViewById(R.id.mine_basic_inf_phonenum);
        likesports = (TextView) findViewById(R.id.mine_basic_inf_likesports);
        sexinfo = (TextView) findViewById(R.id.mine_basic_inf_sexinfo);
        merriage = (TextView) findViewById(R.id.mine_basic_inf_merriage);
        try{
            userInfoJson = new JSONObject(userInfo).getJSONObject("data").getJSONObject("userinfo");
            name.setText(userInfoJson.getString("name"));
            city.setText(userInfoJson.getString("city"));
            sex.setText(userInfoJson.getString("sex")=="0"?"男":"女");
            height.setText(userInfoJson.getString("hight"));
            weight.setText(userInfoJson.getString("weight"));
            email.setText(userInfoJson.getString("qq"));
            phonenum.setText(userInfoJson.getString("phoneNum"));
            likesports.setText(userInfoJson.getString("likeSports"));
            sexinfo.setText(userInfoJson.getString("sexInfo"));
            merriage.setText(userInfoJson.getString("merriage")=="0"? "未婚":"已婚");
        }catch(Exception e) {
            e.printStackTrace();
        }



    }


}
