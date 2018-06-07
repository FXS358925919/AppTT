package com.TT.SincereAgree;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.TT.SincereAgree.amei.activity.AudioRecoder;
import com.TT.SincereAgree.amei.activity.SendDynamicActivity;
import com.TT.SincereAgree.amei.activity.SendPaidDateActivity;
import com.TT.SincereAgree.amei.activity.SendTextDyActivity;
import com.TT.SincereAgree.amei.activity.UserInfActivity;
import com.TT.SincereAgree.amei.adapter.PopwindowAdapter;
import com.TT.SincereAgree.mainpage.MainPageFragment;
import com.TT.SincereAgree.mine.MyFragment;
import com.TT.SincereAgree.pocket.PocketFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 定义Fragment页面
    private MainPageFragment fragmentUser;
    private MainFragmentSquare fragmentSquare;
    private PocketFragment fragmentWallet;
    private MyFragment fragmentMy;
    // 定义布局对象
    private FrameLayout UserFl, SquareFl, WalletFl, MyFl;

    // 定义图片组件对象
    private ImageView UserIv, SquareIv, WalletIv, MyIv;

    // 定义按钮图片组件
    private ImageView plusImageView;

    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;


    /**
     * popwindow相关变量*/
    private Context context;
    private PopupWindow pw;
    private View popView;
    private GridView popgrid;
    private ImageButton exitpop;


    private static MainActivity mainActivity;


    public MainActivity() {
        mainActivity = this;
    }
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
        // 初始化默认为选中点击了“动态”按钮
        clickUserBtn();
    }




    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        UserFl = (FrameLayout) findViewById(R.id.layout_bottom_user);
       SquareFl= (FrameLayout) findViewById(R.id.layout_bottom_square);
        WalletFl = (FrameLayout) findViewById(R.id.layout_bottom_wallet);
        MyFl = (FrameLayout) findViewById(R.id.layout_bottom_me);

        // 实例化图片组件对象
        UserIv = (ImageView) findViewById(R.id.image_user);
        SquareIv = (ImageView) findViewById(R.id.image_square);
       WalletIv = (ImageView) findViewById(R.id.image_wallet);
       MyIv = (ImageView) findViewById(R.id.image_me);

        // 实例化按钮图片组件
        plusImageView = (ImageView) findViewById(R.id.plus_btn);

        popView=getLayoutInflater().inflate(
                R.layout.item_popupwindows,null);
        exitpop=(ImageButton) popView.findViewById(
                R.id.popimagebute);
        popgrid=(GridView)popView.findViewById(
                R.id.popwindowgrid);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        UserFl.setOnClickListener(this);
        SquareFl.setOnClickListener(this);
        WalletFl.setOnClickListener(this);
        MyFl.setOnClickListener(this);

        // 给按钮图片设置监听
        plusImageView.setOnClickListener(this);

        /**
         * 以下为PopupWindow里面的点击事件和相关适配*/
        String[] titles = new String[]{
                "文字", "拍摄", "相册", "语音", "视频",
                "有偿约会", "悬赏寻陪聊", "陪聊赚悬赏"
        };

        Integer[] images = {
                R.mipmap.sendtext,
                R.mipmap.shoot,

                R.mipmap.album,
                R.mipmap.sendtext,
                R.mipmap.sendtext,

                R.mipmap.paiddate,
                R.mipmap.sendtext,
                R.mipmap.sendtext};
        PopwindowAdapter po = new PopwindowAdapter(titles,images,this);
        popgrid.setAdapter(po);
        popgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent_sendtext = new Intent(MainActivity.this,SendTextDyActivity.class);
                        startActivity(intent_sendtext);
                        break;
                    case 1:
                        Intent intent_takephoto = new Intent(MainActivity.this,SendDynamicActivity.class);
                        startActivity(intent_takephoto);
                        break;
                    case 2:
                        Intent sendDynamic_intent = new Intent(MainActivity.this,SendDynamicActivity.class);
                        startActivity(sendDynamic_intent);
                        break;
                    case 3:
                        Intent intent_audio = new Intent(MainActivity.this,AudioRecoder.class);
                        startActivity(intent_audio);
                        break;
                    case 4:
                        Intent intent_usermood1 = new Intent(MainActivity.this,UserInfActivity.class);
                        //startActivity(intent_usermood1);
                        break;
                    case 5:
                        Intent sendpaiddate_intent = new Intent(MainActivity.this,SendPaidDateActivity.class);
                        startActivity(sendpaiddate_intent);
                        break;
                    case 6:
                        Intent intent_usermood2 = new Intent(MainActivity.this,UserInfActivity.class);
                        //startActivity(intent_usermood2);
                        break;
                    case 7:
                        Intent sendDynamic_intent2 = new Intent(MainActivity.this,SendDynamicActivity.class);
                        //startActivity(sendDynamic_intent2);
                        break;
                }
            }
        });
        exitpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击动态按钮
            case R.id.layout_bottom_user:
                clickUserBtn();
                break;
            // 点击与广场相关按钮
            case R.id.layout_bottom_square:
                clickSquareBtn();
                break;
            // 点击钱包相关按钮
            case R.id.layout_bottom_wallet:
                clickWalletBtn();
                break;
            // 点击我的相关按钮
            case R.id.layout_bottom_me:
                clickMineBtn();
                break;
            // 点击中间按钮
            case R.id.plus_btn:
                clickToggleBtn();
                break;
        }
    }
    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        pw = getPopWindow(popView);

    }

    public PopupWindow getPopWindow(View view){
        PopupWindow popupWindow=new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,true
        );
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.anim.fade_in);

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        View parent = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.user_mainpage, null);

        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams ll=
                        getWindow().getAttributes();
                ll.alpha=1f;
                getWindow().setAttributes(ll);
                //设置View可见
            }
        });
        return popupWindow;
    }


    /**
     * 点击了“用户”按钮
     */
    private void clickUserBtn() {
        // 实例化Fragment页面
        fragmentUser = new MainPageFragment();
        //fragmentUser = MainFragmentUser.newInstance("用户");
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_main_content, fragmentUser);
        // 事务管理提交
        fragmentTransaction.commit();
        // 改变选中状态
        UserFl.setSelected(true);
        UserIv.setSelected(true);

        SquareFl.setSelected(false);
        SquareIv.setSelected(false);

        WalletFl.setSelected(false);
        WalletIv.setSelected(false);

        MyFl.setSelected(false);
        MyIv.setSelected(false);
    }

    /**
     * 点击了“广场”按钮
     */
    private void clickSquareBtn() {
        // 实例化Fragment页面
        fragmentSquare = new MainFragmentSquare();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_main_content, fragmentSquare);
        // 事务管理提交
        fragmentTransaction.commit();

        UserFl.setSelected(false);
        UserIv.setSelected(false);

        SquareFl.setSelected(true);
        SquareIv.setSelected(true);

        WalletFl.setSelected(false);
        WalletIv.setSelected(false);

        MyFl.setSelected(false);
        MyIv.setSelected(false);
    }

    /**
     * 点击了“钱包”按钮
     */
    private void clickWalletBtn() {
        // 实例化Fragment页面
        //fragmentWallet = new MainFragmentWallet();
        fragmentWallet = new PocketFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_main_content, fragmentWallet);
        // 事务管理提交
        fragmentTransaction.commit();

        UserFl.setSelected(false);
        UserIv.setSelected(false);

        SquareFl.setSelected(false);
        SquareIv.setSelected(false);

        WalletFl.setSelected(true);
        WalletIv.setSelected(true);

        MyFl.setSelected(false);
        MyIv.setSelected(false);
    }

    /**
     * 点击了“我”按钮
     */
    private void clickMineBtn() {
        // 实例化Fragment页面
        fragmentMy = new MyFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_main_content, fragmentMy);
        // 事务管理提交
        fragmentTransaction.commit();

        UserFl.setSelected(false);
        UserIv.setSelected(false);

        SquareFl.setSelected(false);
        SquareIv.setSelected(false);

        WalletFl.setSelected(false);
        WalletIv.setSelected(false);

        MyFl.setSelected(true);
        MyIv.setSelected(true);
    }


}
