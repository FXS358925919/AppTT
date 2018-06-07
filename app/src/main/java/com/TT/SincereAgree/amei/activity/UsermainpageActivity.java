package com.TT.SincereAgree.amei.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.GridPicsAdapter;
import com.TT.SincereAgree.amei.adapter.PersonLabelAdapter;
import com.TT.SincereAgree.amei.dao.UserIfoDao;
import com.TT.SincereAgree.amei.view.SimpleItemLayout;
import com.TT.SincereAgree.chat.GiftActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * 主页面1-用户
 */
public class UsermainpageActivity extends AppCompatActivity {
    private UserIfoDao userIfoDao = new UserIfoDao();
    private SimpleItemLayout userIamge;
    private SimpleItemLayout userIfo;
    private SimpleItemLayout userMood;
    private TextView userPresent;
    private TextView userFollow;
    private TextView userFan;
    private TextView userFriend;
    private TextView seeOtherUserInf;

    private Button sendGift;
    private Button startChat;
    private Button follow;
    //性别
    private String sex;
    //名字
    private String name;

    //是否已经关注,0是未关注
    long hasfollow = 0;

    //是否成功关注
    boolean followed = false;

    private View popView;
    private GridView popgrid;
    private ImageButton exitpop;
    private GridView lifePicGrid;
    private GridPicsAdapter lifePicGridAdapter;
    private List<String>  lifePicData = new ArrayList<>();
    private GridView personLableGrid1;
    private PersonLabelAdapter personLableGrid1Adapter;
    private List<String>  personaLable1Data = new ArrayList<>();
    private GridView personLableGrid2;
    private List<String>  personaLable2Data = new ArrayList<>();
    private PersonLabelAdapter personLableGrid2Adapter;
    private ImageView backgroundImageView;
    private ImageView circleImageView;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mainpage);
        inflater = getLayoutInflater();
        backgroundImageView = (ImageView) findViewById(R.id.iv_head_backgroud);
        circleImageView = (ImageView) findViewById(R.id.iv_head_circle);
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(backgroundImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(circleImageView);
        findview();
        init();

    }

    private void findview(){
        userIamge = (SimpleItemLayout) findViewById(
                R.id.item_title_life);
        userIfo = (SimpleItemLayout)findViewById(
                R.id.item_title_detail);
        userMood = (SimpleItemLayout)findViewById(
                R.id.item_title_mood);
        /*userPresent = (TextView)findViewById(
                R.id.user_mainpage_editText6);*/
        userFollow = (TextView)findViewById(
                R.id.user_mainpage_editText7);
        userFan = (TextView)findViewById(
                R.id.user_mainpage_editText8);
        userFriend = (TextView) findViewById(R.id.user_mainpage_friend);
        sendGift = (Button) findViewById(R.id.bottom_sendgift);
        startChat = (Button)findViewById(R.id.bottom_chat);
        follow = (Button) findViewById(R.id.bottom_follow);
        lifePicGrid = (GridView) findViewById(R.id.life_grid_pics);
        personLableGrid1 = (GridView) findViewById(R.id.detailinfo_grid_lable_1);
        personLableGrid2 = (GridView) findViewById(R.id.detailinfo_grid_lable_2);
    }

    private void setPersonLabes(){
        personaLable1Data.add("24岁");
        personaLable1Data.add("158m");
        personaLable1Data.add("暂无");
        personaLable2Data.add("现居城市：上海");
        personaLable2Data.add("期望三年内结婚");
        personLableGrid1Adapter = new PersonLabelAdapter(this,R.layout.user_mainpage,personaLable1Data);
        personLableGrid2Adapter = new PersonLabelAdapter(this,R.layout.user_mainpage,personaLable2Data);
        personLableGrid1.setAdapter(personLableGrid1Adapter);
        personLableGrid2.setAdapter(personLableGrid2Adapter);
    }

    private void setLifePicGrid(){
        lifePicData.add("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=ce18bf50dbca7bcb627bc12f8e086b3f/a2cc7cd98d1001e98517929cb40e7bec54e7977e.jpg");
        lifePicData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528306845913&di=c237fa4a82f018a048e7bccbd1e621ec&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F908fa0ec08fa513d88fa2e4c316d55fbb3fbd9d4.jpg");
        lifePicData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528306845913&di=762e32fa5bc34b43fa68aebdf24d5a01&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F71cf3bc79f3df8dc6f0d4203c111728b47102874.jpg");
        lifePicData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528306845912&di=15c6636895702249b03931c15e477d7c&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F71cf3bc79f3df8dcdfa01202c111728b46102887.jpg");
        lifePicData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528306845911&di=7c4e5428303563c24244336d879ae731&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8718367adab44aed314c6f73bf1c8701a18bfb23.jpg");
        lifePicGridAdapter = new GridPicsAdapter(this, R.layout.user_mainpage,lifePicData);
        lifePicGrid.setAdapter(lifePicGridAdapter);
    }
    private void init(){
        setLifePicGrid();
        setPersonLabes();
        Intent intent = getIntent();
        final String accountID = intent.getStringExtra("ACCOUNTID");
        sendRequestForUserinfo(accountID);

        /*userFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_userFan = new Intent(UsermainpageActivity.this,TabActivity.class);
                startActivity(intent_userFan);

            }
        });*/
        /*userPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_present = new Intent(UsermainpageActivity.this,PresentActivity.class);
                startActivity(intent_present);
            }
        });*/

        /**
         * 详细资料
         */
        userIfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_usermood = new Intent(UsermainpageActivity.this,UserDyInfActivity.class);
                intent_usermood.putExtra("ACCOUNTID",accountID);
                intent_usermood.putExtra("SECTIONNUM",1);
                startActivity(intent_usermood);
            }
        });

        /***
         * 生活照片
         */
        userIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_userima = new Intent(UsermainpageActivity.this,BlurImageActivity.class);
                intent_userima.putExtra("ACCOUNTID",accountID);
                startActivity(intent_userima);
            }
        });

        /**
         * 心情状态
         */
        userMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), accountID , Toast.LENGTH_LONG).show();
                Intent Dynamic_intent = new Intent(UsermainpageActivity.this,UserDyInfActivity.class);
                Dynamic_intent.putExtra("ACCOUNTID",accountID);
                Dynamic_intent.putExtra("SECTIONNUM",0);
                startActivity(Dynamic_intent);
            }
        });

        /**
         * 送礼物
         */
        sendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsermainpageActivity.this, GiftActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("receive",accountID);
                startActivity(intent);
            }
        });

        /**
         * 开始聊天
         */
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    //每次开启聊天窗口就初始化sendCount和receiveCount
                    Configure.sendCount = 0;
                    Configure.receiveCount = 0;
                    //Configure.sendValueCount = 0;
                    //把聊天对象的Id保存到静态变量中
                    Configure.chatToId = accountID;
                    if(Configure.sex.equals("女")  && sex.equals("男"))
                        Configure.reduceIntegral = false;
                    RongIM.getInstance().startPrivateChat(UsermainpageActivity.this,accountID,"与"+name+"聊天中");
                }
            }
        });

        /**
         * 关注
         */
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasfollow == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UsermainpageActivity.this);
                    builder.setMessage("确认关注?");
                    builder.setCancelable(true);
                    //设置正面按钮
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendRequestForFollow(accountID);
                            dialog.dismiss();
                            if(followed){
                                Toast.makeText(UsermainpageActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                follow.setText("已关注");
                                hasfollow = 1;
                            }

                            else
                                Toast.makeText(UsermainpageActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //设置反面按钮
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

    }

    private void sendRequestForUserinfo(final String accountid) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mine/home/friend/" + Configure.accountId + "/" + accountid).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            if(jSONObject.getInt("code") == 200)
                return;
            showResponse(jSONObject.getJSONObject("data"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final JSONObject jSONObject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                try {
                    JSONObject userinfo = jSONObject.getJSONObject("userinfo");
                    userFriend.setText("好友\n"+jSONObject.getString("friendcount"));
                    userFollow.setText("关注\n"+jSONObject.getString("follwcount"));
                    userFan.setText("粉丝\n"+jSONObject.getString("fanscount"));
                    sex = userinfo.getString("sex").equals("1")? "男":"女";
                    name = userinfo.getString("name");
                    hasfollow = Long.parseLong(jSONObject.getString("hasfollow"));
                    follow.setText(hasfollow == 0? "关注":"已关注");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendRequestForFollow(final String accountid) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mine/follow/" + Configure.accountId + "/" + accountid).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = new JSONObject(responseData);
                        if(jSONObject.getInt("code") == 200)
                            followed = false;
                        else
                            followed = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
