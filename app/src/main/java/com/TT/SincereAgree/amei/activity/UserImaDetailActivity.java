package com.TT.SincereAgree.amei.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.util.MyScrollView;

import java.util.ArrayList;
import java.util.List;

public class UserImaDetailActivity extends AppCompatActivity {
    private List<String> url = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.showpicture,null);
        MyScrollView scrollView = (MyScrollView) view.getChildAt(0);
        scrollView.setUrl(url);
        setContentView(view);
    }

    private void init() {
        // 运行时获取存储权限，android6.0之后不仅需要在Mainfest里面添加权限，还需要添加运行时权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        Intent intent = getIntent();

        url = (List<String>) intent.getSerializableExtra("URL");
    }
}
