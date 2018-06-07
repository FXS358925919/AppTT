package com.TT.SincereAgree.mainpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.mine.InformationActivity;
import com.TT.SincereAgree.pocket.RechargeViewPageActivity;

import org.apaches.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.TT.SincereAgree.Configure.accountId;
import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-05-15.
 */

public class MainPageFragment extends Fragment implements RongIM.UserInfoProvider{
    View view;
    private Toolbar toolbar=null;
    AppCompatActivity pActivity;
    private List<NineBean> list = new ArrayList<NineBean>();
    private String maintoken = "";
    NineAdapter nineAdapter;
    public MainPageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RongIM.setUserInfoProvider(this, true);
        //没连接再连接
        if(RongIM.getInstance().getRongIMClient().getCurrentConnectionStatus()== RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED){
            connectRongServer(maintoken);
        }
        view = inflater.inflate(R.layout.fragment_mainpage_user, null);
        initList();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mainpage_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageFragment.this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        nineAdapter = new NineAdapter(list);
        recyclerView.setAdapter(nineAdapter);
        Button shai = (Button) view.findViewById(R.id.shaicuan);
        shai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        Button sign = (Button) view.findViewById(R.id.signButton);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeViewPageActivity.class);
                startActivity(intent);
            }
        });
        onFocus();
        return view;
    }

    public void initList(){
        sendRequestWithHttpURLConnection();
        /*NineBean nineBean = new NineBean();
        nineBean.setImageUrl1("/00/00/00/01/profile.jpg");
        nineBean.setName1("haha");
        nineBean.setCity1("河北");
        nineBean.setImageUrl2("/00/00/00/02/profile.jpg");
        nineBean.setName2("haha11");
        nineBean.setCity2("河北11");
        nineBean.setImageUrl3("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl4("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl5("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl6("/00/00/00/02/profile.jpg");

        NineBean nineBean1 = new NineBean();
        nineBean1.setImageUrl1("/00/00/00/03/profile.jpg");
        nineBean.setImageUrl2("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl3("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl4("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl5("/00/00/00/02/profile.jpg");
        nineBean.setImageUrl6("/00/00/00/02/profile.jpg");
        nineBean1.setName1("haha");
        nineBean1.setCity1("河北");
        list.add(nineBean);
        list.add(nineBean1);*/
    }

    private void sendRequestWithHttpURLConnection() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/mainpage/getuser").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
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

    private void sendRequestWithHttpURLConnectionHaveParam(final String relation,final String city,final String age) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("relation",relation).add("city",city).
                            add("age",age).build();
                    Request request = new Request.Builder().url(rootUrl + "/mainpage/getuser").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
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

    public void parseJSONWithJSONObject(String responseData){
        try {
            list.clear();
            JSONObject jSONObject = new JSONObject(responseData).getJSONObject("data");
            JSONArray jsonArray = jSONObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray curArray = jsonArray.getJSONArray(i);
                if(curArray.length() == 6){
                    NineBean ninebean = new NineBean();
                    ninebean.setAccountid6(curArray.getJSONObject(0).getString("accountId"));
                    ninebean.setCity6(curArray.getJSONObject(0).getString("city"));
                    ninebean.setImageUrl6(curArray.getJSONObject(0).getString("profile"));
                    ninebean.setRelation6(curArray.getJSONObject(0).getString("relation"));

                    ninebean.setAccountid1(curArray.getJSONObject(1).getString("accountId"));
                    ninebean.setName1(curArray.getJSONObject(1).getString("name"));
                    ninebean.setCity1(curArray.getJSONObject(1).getString("city"));
                    ninebean.setImageUrl1(curArray.getJSONObject(1).getString("profile"));
                    ninebean.setRelation1(curArray.getJSONObject(1).getString("relation"));

                    ninebean.setAccountid2(curArray.getJSONObject(2).getString("accountId"));
                    ninebean.setName2(curArray.getJSONObject(2).getString("name"));
                    ninebean.setCity2(curArray.getJSONObject(2).getString("city"));
                    ninebean.setImageUrl2(curArray.getJSONObject(2).getString("profile"));
                    ninebean.setRelation2(curArray.getJSONObject(2).getString("relation"));

                    ninebean.setAccountid3(curArray.getJSONObject(3).getString("accountId"));
                    ninebean.setName3(curArray.getJSONObject(3).getString("name"));
                    ninebean.setCity3(curArray.getJSONObject(3).getString("city"));
                    ninebean.setImageUrl3(curArray.getJSONObject(3).getString("profile"));
                    ninebean.setRelation3(curArray.getJSONObject(3).getString("relation"));

                    ninebean.setAccountid4(curArray.getJSONObject(4).getString("accountId"));
                    ninebean.setName4(curArray.getJSONObject(4).getString("name"));
                    ninebean.setCity4(curArray.getJSONObject(4).getString("city"));
                    ninebean.setImageUrl4(curArray.getJSONObject(4).getString("profile"));
                    ninebean.setRelation4(curArray.getJSONObject(4).getString("relation"));

                    ninebean.setAccountid5(curArray.getJSONObject(5).getString("accountId"));
                    ninebean.setName5(curArray.getJSONObject(5).getString("name"));
                    ninebean.setCity5(curArray.getJSONObject(5).getString("city"));
                    ninebean.setImageUrl5(curArray.getJSONObject(5).getString("profile"));
                    ninebean.setRelation5(curArray.getJSONObject(5).getString("relation"));

                    list.add(ninebean);
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_search:
                    Snackbar.make(toolbar,"Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_mainpage_message:
                    //Snackbar.make(toolbar,"消息按钮被点击 in user", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), InformationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar,"Click More", Snackbar.LENGTH_SHORT).show();
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
        pActivity = (AppCompatActivity) getActivity();
        toolbar= (Toolbar) pActivity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView)pActivity.findViewById(R.id.mainpage_toolbar_title);
        tv_title.setText("主页");
        pActivity.setSupportActionBar(toolbar);
        //设置筛选Icon，必须在setSupportActionBar(toolbar)之后设置
        toolbar.setNavigationIcon(R.mipmap.ic_menu_filter_24dp);
        //添加筛选菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.select, null);
                final AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle("筛选").setView(view)
                        .create();
                final RadioButton friendRadio = (RadioButton) view.findViewById(R.id.main_select_friend);
                final RadioButton zhijiRadio = (RadioButton) view.findViewById(R.id.main_select_zhiji);
                final RadioButton qinmiRadio = (RadioButton) view.findViewById(R.id.main_select_qinmi);
                final EditText cityEdit = (EditText) view.findViewById(R.id.select_city);
                final EditText ageEdit = (EditText) view.findViewById(R.id.select_age);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                //添加确定按钮
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String relation = friendRadio.isChecked()? "朋友":zhijiRadio.isChecked()?"知己":"亲密";
                        String city = cityEdit.getText().toString();
                        if(city.equals(""))
                            city = "all";
                        String age = ageEdit.getText().toString();
                        if(age.equals(""))
                            age = "-1";
                        try{
                            int num = Integer.parseInt(age);
                            sendRequestWithHttpURLConnectionHaveParam(relation,city,age);
                            //写在主线程中可以刷新，线程中只有触摸或者滑动才刷新
                            nineAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"年龄请输入数字",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                dialog.show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }


    private void connectRongServer(String token) {

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
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(s, Configure.name, Uri.parse(Configure.picUrl)));
                }
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                //Toast.makeText(getActivity(), "connect server sucess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void sendRequestWithHttpURLConnectionForToken() {

        Thread thread = new Thread(new Runnable() {
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
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        return new UserInfo(accountId, Configure.name, Uri.parse(Configure.picUrl));
    }
}
