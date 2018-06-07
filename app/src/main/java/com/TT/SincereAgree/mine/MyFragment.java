package com.TT.SincereAgree.mine;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.activity.UserDyInfActivity;
import com.TT.SincereAgree.mine.setting.SettingActivity;
import com.TT.SincereAgree.pocket.MyGiftActivity;
import com.TT.SincereAgree.util.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.TT.SincereAgree.Configure.accountId;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private String maintoken = "";
    private String name;
    private String profileurl;
    private String[] authority = new String[]{"权限1", "权限2", "权限3"};
    private ButtonOnClick buttonOnClick = new ButtonOnClick(1);
    private Toolbar toolbar = null;
    AppCompatActivity pMainActivity;
    private String rootUrl = Configure.rootUrl;
    private String userInfoStr;
    //我的名字,好友，关注，粉丝，礼物
    private TextView myName;
    private TextView myFriendsCount;
    private TextView myAttenCount;
    private TextView myFollwersCount;
    private TextView myGiftCount;
    private ImageView profile;
    private Toolbar myToolbar = null;
    public MyFragment() {
        // Required empty public constructor
    }


    private class ButtonOnClick implements DialogInterface.OnClickListener {

        private int index; // 表示选项的索引

        public ButtonOnClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // which表示单击的按钮索引，所有的选项索引都是大于0，按钮索引都是小于0的。
            if (which >= 0) {
                //如果单击的是列表项，将当前列表项的索引保存在index中。
                //如果想单击列表项后关闭对话框，可在此处调用dialog.cancel()
                //或是用dialog.dismiss()方法。
                index = which;
            } else {
                //用户单击的是【确定】按钮
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    //显示用户选择的是第几个列表项。
                    final AlertDialog ad = new AlertDialog.Builder(
                            getActivity()).setMessage(
                            "你选择的权限是:" + authority[index]).show();
                    //五秒钟后自动关闭。
                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            ad.dismiss();
                        }
                    };
                    hander.postDelayed(runnable, 5 * 1000);
                }
                //用户单击的是【取消】按钮
                else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Toast.makeText(getActivity(), "你没有选择任何东西",
                            Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.my_toolBar);
        /*toolbar.setTitle("我的");*/
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.setting15x);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //setHasOptionsMenu(true);
        myName = (TextView) view.findViewById(R.id.my_name);
        myFriendsCount = (TextView) view.findViewById(R.id.my_friends_count);
        myAttenCount = (TextView) view.findViewById(R.id.my_atten_count);
        myFollwersCount = (TextView) view.findViewById(R.id.my_follower_count);
        myGiftCount = (TextView) view.findViewById(R.id.my_gift_count);
        profile = (ImageView) view.findViewById(R.id.my_profile);
        TextView album_text = (TextView) view.findViewById(R.id.my_album);
        album_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击设置阅读权限", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择权限");
                builder.setSingleChoiceItems(authority, 0, buttonOnClick);
                builder.setPositiveButton("确定", buttonOnClick);
                builder.setNegativeButton("取消", buttonOnClick);
                builder.show();
            }
        });
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.my_function_data);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My_BasicInf.class);
                intent.putExtra("userinfo", userInfoStr);
                startActivity(intent);
            }
        });
        myGiftCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "显示礼物页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MyGiftActivity.class);
                startActivity(intent);
            }
        });
        TextView myaffairTextView = (TextView) view.findViewById(R.id.my_my_affair);
        myaffairTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Dynamic_intent = new Intent(getActivity(),UserDyInfActivity.class);
                Dynamic_intent.putExtra("ACCOUNTID", Configure.accountId);
                startActivity(Dynamic_intent);
            }
        });

        LinearLayout linearLayout_pocket = (LinearLayout) view.findViewById(R.id.myfrgment_pocket);


        myFriendsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("accountid",accountId);
                intent.putExtra("frienditem", "0");
                startActivity(intent);
            }
        });
        myAttenCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("accountid",accountId);
                intent.putExtra("frienditem", "1");
                startActivity(intent);
            }
        });
        myFollwersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("accountid",accountId);
                intent.putExtra("frienditem", "2");
                startActivity(intent);
            }
        });
        /*
        Button test = (Button) view.findViewById(R.id.button_test);
        textView = (TextView) view.findViewById(R.id.text_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithHttpURLConnection();


            }
        });*/
        LinearLayout recentChat = (LinearLayout) view.findViewById(R.id.myfrgment_recentChat);
        recentChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecentActivityChat.class);
                startActivity(intent);
                //RongIM.getInstance().startConversationList(getContext());
            }
        });
        LinearLayout setting = (LinearLayout) view.findViewById(R.id.myfrgment_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                intent.putExtra("profileurl",profileurl);
                startActivity(intent);
            }
        });
        sendRequestWithHttpURLConnection();
        onFocus();
        /*RongIM.setUserInfoProvider(this, true);
        //没连接再连接
        if(RongIM.getInstance().getRongIMClient().getCurrentConnectionStatus()==RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED){
            connectRongServer(maintoken);
        }*/

        return view;
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mine/home/" + accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    userInfoStr = responseData;
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
            //JSONArray jsonArray = new JSONArray(responseData);
            if(jSONObject.getInt("code") == 200)
                return;
            //userInfo = (Map<String, String>) jSONObject.getJSONObject("data");
            showResponse(jSONObject.getJSONObject("data"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final JSONObject jSONObject) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                try {
                    JSONObject userinfo = jSONObject.getJSONObject("userinfo");
                    name = userinfo.getString("name");
                    profileurl = rootUrl+"/mine/home/profile?url="+userinfo.getString("profile");
                    //maintoken = jSONObject.getString("token");
                    myName.setText(name);
                    myFriendsCount.setText("好友\n"+jSONObject.getString("friendcount"));
                    myAttenCount.setText("关注\n"+jSONObject.getString("follwcount"));
                    myFollwersCount.setText("粉丝\n"+jSONObject.getString("fanscount"));
                    myGiftCount.setText("礼物\n"+jSONObject.getString("giftcount"));

                    new DownloadImageTask(profile).execute(profileurl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mytoolbar, menu);

    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message:
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }*/
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    Snackbar.make(toolbar, "Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_mainpage_message:
                    //Snackbar.make(toolbar, "消息按钮被点击 in mine", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), InformationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar, "Click More", Snackbar.LENGTH_SHORT).show();
                    break;
            }

            return true;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
        inflater.inflate(R.menu.menu_mainfragment_user, menu);
    }

    public void onFocus() {
        setHasOptionsMenu(true);
        pMainActivity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) pMainActivity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        /*toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView) pMainActivity.findViewById(R.id.mainpage_toolbar_title);
        tv_title.setText("我的");
        pMainActivity.setSupportActionBar(toolbar);
        //设置筛选Icon，必须在setSupportActionBar(toolbar)之后设置
        toolbar.setNavigationIcon(R.mipmap.ic_menu_filter_24dp);
        //添加筛选菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar, "我的界面消息被获取", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);*/
    }

    /*private void connectRongServer(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("onError", "on onError : ");
                sendRequestWithHttpURLConnectionForToken();
                Log.e("onError", maintoken);
            }

            @Override
            public void onSuccess(String s) {
                String userid = s;
                Log.e("onSucess", "on Sucess userid: " + s);
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(s, name, Uri.parse(profileurl)));
                }
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                Toast.makeText(getActivity(), "connect server sucess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void sendRequestWithHttpURLConnectionForToken() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    int val = new Random().nextInt(10);
                    long i = new Date().getTime();
                    String data = Configure.appSecret + val + i;

                    String siqn = DigestUtils.shaHex(data);
                    RequestBody requestBody = new FormBody.Builder().add("userId", accountId).build();
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().url(Configure.getTokenUrl).addHeader("App-Key", Configure.appKey).addHeader("Nonce", val + "")
                            .addHeader("Timestamp", i + "").addHeader("Signature", siqn)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    if (jSONObject.getInt("code") == 200) {
                        maintoken = jSONObject.getString("token");
                        connectRongServer(maintoken);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }*/
}
