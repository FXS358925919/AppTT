package com.TT.SincereAgree.amei.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.GiftPictureAdapter;

public class PresentActivity extends AppCompatActivity {
    private GridView gridView;
    private String[] titles = new String[]{
            "棒棒糖", "蛋糕", "小熊", "玫瑰", "咖啡",
            "钻戒", "红色跑车", "火箭"
    };
    private String[] price = new String[]{
            "9积分", "9积分", "9积分", "9积分", "9积分"
            , "9积分", "9积分", "9积分"
    };
    private Integer[] images = {R.drawable.lolly, R.drawable.cake,
            R.drawable.bear, R.drawable.rose, R.drawable.coffee,
            R.drawable.ring, R.drawable.redcar, R.drawable.rocket};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpresent);
        findview();
        init();
    }

    private void findview() {
        gridView = (GridView) findViewById(R.id.userpresent_giftgrid);
    }

    private void init() {
        GiftPictureAdapter pictureAdapter = new GiftPictureAdapter(titles, price, images, this);
        gridView.setAdapter(pictureAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String giftname = titles[position];
                new AlertDialog.Builder(PresentActivity.this).setTitle("提示")
                        .setMessage("你确定要送" + giftname + "这个礼物吗？你需要支付9积分").
                        setPositiveButton("确定",payGiftListener).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
            DialogInterface.OnClickListener payGiftListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PresentActivity.this, "预做礼物支付积分处理", Toast.LENGTH_SHORT).show();
                }
            };
        });




    }
}
