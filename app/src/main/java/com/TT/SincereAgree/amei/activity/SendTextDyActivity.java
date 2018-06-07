package com.TT.SincereAgree.amei.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * 发布文字
 */
public class SendTextDyActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private EditText sendtext;
    /**发布的内容*/
    private String textContent;
    /**发布的时间*/
    private String releasetime;
    /**发布的账号ID*/
    private int state;
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_text_dy);
        app = (App) this.getApplicationContext();
        initViews();
    }
    @Override
    protected void onStart() {
        super.onStart();
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.showLeftImageAndRightStr("发布", "发布", "取消");

        titleBar.clickright(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textContent = sendtext.getText().toString().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                releasetime = dateFormat.format(new Date());
                final HashMap<String,String> map = new HashMap<String, String>();
                map.put("accountId",app.getUserID());
                map.put("dContent",textContent);
                map.put("dReleasetime",releasetime);

                if (!textContent.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postDynamic");
                            if (state == 1)
                                handler.sendEmptyMessage(0);
                            //else
                                //Toast.makeText(SendTextDyActivity.this, "发布失败", Toast.LENGTH_LONG).show();
                        }
                    }).start();
                } else {
                    Toast.makeText(SendTextDyActivity.this, "内容不能为空!", Toast.LENGTH_LONG).show();
                }
            }
        });
        titleBar.clickleft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initViews() {
        sendtext = (EditText)findViewById(R.id.sendtext);

    }
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(SendTextDyActivity.this, "发布成功", Toast.LENGTH_LONG).show();
            finish();
        }

    };


}
