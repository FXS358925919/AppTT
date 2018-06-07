package com.TT.SincereAgree.pocket;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.TT.SincereAgree.Configure.accountId;
import static com.TT.SincereAgree.Configure.rootUrl;


/**
 * A simple {@link Fragment} subclass.
 */
public class PocketFragment extends Fragment {
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private boolean turnLeft = true;
    private Toolbar toolbar=null;
    AppCompatActivity pActivity;
    String pocketStr;
    private Button button_exchange;
    private Button button_withdrawal;
    private LinearLayout recharge;
    private LinearLayout vipManagement;
    private LinearLayout gift;
    private LinearLayout bill;
    private TextView integralTextView;
    private int integralCount;
    private int sincerityCount;
    private int vipLevel;
    private String expire;
    private TextView sign_acquire;
    private TextView reward_acquire;
    private TextView chat_acquire;
    private TextView share_acquire;
    private FrameLayout pocket_framelayout;
    private ImageView circle1;
    private ImageView circle2;
    public PocketFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pocket, container, false);
        integralTextView = (TextView) view.findViewById(R.id.integral_count);
        sendRequestWithHttpURLConnection();
        /*setHasOptionsMenu(true);
        pActivity = (AppCompatActivity) getActivity();
        toolbar= (Toolbar) pActivity.findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView)pActivity.findViewById(R.id.mainpage_toolbar_title);
        //tv_title.setText("钱包");
        pActivity.setSupportActionBar(toolbar);
        //设置筛选Icon，必须在setSupportActionBar(toolbar)之后设置
        toolbar.setNavigationIcon(R.mipmap.ic_menu_filter_24dp);
        //添加筛选菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar, "筛选消息发出", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);*/

        button_exchange = (Button) view.findViewById(R.id.pocket_fragment_button_exchange);
        button_withdrawal = (Button) view.findViewById(R.id.pocket_fragment_button_withdrawal);
        recharge = (LinearLayout) view.findViewById(R.id.pocket_fragment_recharge_layout);
        vipManagement = (LinearLayout) view.findViewById(R.id.pocket_fragment_vip_layout);
        gift = (LinearLayout) view.findViewById(R.id.pocket_fragment_gift_layout);
        bill = (LinearLayout) view.findViewById(R.id.pocket_fragment_bill_layout);
        button_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, ExchangeActivity.class);
                intent.putExtra("integral",integralCount);
                intent.putExtra("sincerity",sincerityCount);
                startActivity(intent);
            }
        });
        button_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, WithdrawalActivity.class);
                intent.putExtra("integralCount",integralCount);
                startActivity(intent);
            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, RechargeViewPageActivity.class);
                intent.putExtra("integralCount",integralCount);
                intent.putExtra("sincerityCount",sincerityCount);
                startActivity(intent);
            }
        });
        vipManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, VipManamentActivity.class);
                startActivity(intent);
            }
        });
        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, MyGiftActivity.class);
                startActivity(intent);
            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pActivity, BillActivity.class);
                startActivity(intent);
            }
        });
        sign_acquire = (TextView) view.findViewById(R.id.pocket_fragment_sign_acquire);
        reward_acquire = (TextView) view.findViewById(R.id.pocket_fragment_reward_acquire);
        chat_acquire = (TextView) view.findViewById(R.id.pocket_fragment_chat_acquire);
        share_acquire = (TextView) view.findViewById(R.id.pocket_fragment_share_acquire);
        pocket_framelayout = (FrameLayout) view.findViewById(R.id.pocket_framelayout);
        pocket_framelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosX - mPosX > 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            String text = sincerityCount+"<small><small><small>诚意分</small></small></small>";
                            integralTextView.setText(Html.fromHtml(text));
                            circle1.setImageResource(R.drawable.circle1);
                            circle2.setImageResource(R.drawable.circle2);
                            turnLeft = false;

                        } else if (mCurPosX - mPosX < 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            //向上滑动
                            String text = integralCount+"<small><small><small>积分</small></small></small>";
                            integralTextView.setText(Html.fromHtml(text));
                            circle1.setImageResource(R.drawable.circle2);
                            circle2.setImageResource(R.drawable.circle1);
                            turnLeft = true;
                        }

                        break;
                }
                return true;
            }
        });
        circle1 = (ImageView) view.findViewById(R.id.pocket_circle1);
        circle2 = (ImageView) view.findViewById(R.id.pocket_circle2);
        onFocus();
        return view;
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_search:
                    Snackbar.make(toolbar,"Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_mainpage_message:
                    Snackbar.make(toolbar,"消息按钮被点击 in user", Snackbar.LENGTH_SHORT).show();
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
        toolbar.setVisibility(View.GONE);
        /*toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView)pActivity.findViewById(R.id.mainpage_toolbar_title);
        tv_title.setText("钱包");
        pActivity.setSupportActionBar(toolbar);
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

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + "/pocket/home/" + accountId).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    pocketStr = responseData;
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
            showResponse(jSONObject.getJSONObject("data").getJSONObject("wallet"));

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
                    integralCount = Integer.parseInt(jSONObject.getString("integral"));
                    sincerityCount = Integer.parseInt(jSONObject.getString("sincerity"));
                    vipLevel = jSONObject.getInt("vip");
                    expire = jSONObject.getString("expire");
                    String text = integralCount+"<small><small><small>积分</small></small></small>";
                    integralTextView.setText(Html.fromHtml(text));
                    sign_acquire.setText("+"+jSONObject.getString("signcount")+"\n签到获得");
                    reward_acquire.setText("+"+jSONObject.getString("rewardcount")+"\n打赏获得");
                    chat_acquire.setText("+"+jSONObject.getString("chatcount")+"\n聊天获得");
                    share_acquire.setText("+"+jSONObject.getString("sharecount")+"\n分享获得");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sendRequestWithHttpURLConnection();
    }
}
