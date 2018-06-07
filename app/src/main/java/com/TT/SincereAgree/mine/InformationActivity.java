package com.TT.SincereAgree.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.mainpage.CommentActivity;

public class InformationActivity extends AppCompatActivity {

    private LinearLayout all;
    private LinearLayout review;
    private LinearLayout fans;
    private LinearLayout chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //all = (LinearLayout) findViewById(R.id.information_all);
        review = (LinearLayout) findViewById(R.id.information_review);
        fans = (LinearLayout) findViewById(R.id.information_fans);
        chat = (LinearLayout) findViewById(R.id.information_fans);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });
    }
}
