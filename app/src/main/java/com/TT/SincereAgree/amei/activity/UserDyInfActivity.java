package com.TT.SincereAgree.amei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.VipSquareAdapter;
import com.TT.SincereAgree.amei.common.Data;
import com.TT.SincereAgree.amei.common.VoiceData;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.util.SquareDateThreadHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 *用户动态 和 资料
 */

public class UserDyInfActivity extends AppCompatActivity {
     private static String accountID;
     // 第几个tab是默认tab
     private static int userInfPageNum=1;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private ImageView backgroundImageView;
    private ImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_inf_dy_inf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //--------------------------------------
        backgroundImageView = (ImageView) findViewById(R.id.usermood_iv_background);
        circleImageView = (ImageView) findViewById(R.id.usermood_iv_head);
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(backgroundImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(circleImageView);
        //--------------------------------------
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        accountID = intent.getStringExtra("ACCOUNTID");//用户id
        // 心情状态是0号界面，详情是1号界面
        userInfPageNum = intent.getIntExtra("SECTIONNUM",0);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(userInfPageNum);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(userInfPageNum).select();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_dy_inf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        String url = "/dynamic/dynamicallbyuserid/";
        String url_voice = "/voice/voiceallbyuserid/";
        /**
         *用户资料START
         */
        private TextView userName;
        private TextView userHeight;
        private TextView userJob;
        private TextView userBirth;
        private TextView userCity;
        private TextView userSanwei;
        //END

        ListView listViewvip;
        private List<Voice> voiceList;
        private List<Dynamic> dynamicList;

        private Data data = new Data();
        private VoiceData voiceData = new VoiceData();

        private VipSquareAdapter vipSquareAdapter;
        /**下拉刷新*/
        private SwipeRefreshLayout swipeRefreshLayout;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            if (accountID != null)
                dynamicList = data.sendRequestWithHttpURLConnection(url+accountID);

            voiceList = voiceData.sendRequestWithVoiceURL(url_voice+accountID);//获取服务器语音数据
            SquareDateThreadHelper helper = new SquareDateThreadHelper();
            helper.sendRequestForSquareDate();

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    rootView = inflater.inflate(R.layout.square_vip, container, false);
                    listViewvip = (ListView)rootView.findViewById(R.id.square_viplist);
                    vipSquareAdapter = new VipSquareAdapter(inflater.getContext(),dynamicList,voiceList);
                    listViewvip.setAdapter(vipSquareAdapter);
                    /**下拉刷新*/
                    swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeLayout);
                    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
                    swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
                    swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white_text);
                    swipeRefreshLayout.setProgressViewEndTarget(true, 200);

                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    mHandler.sendEmptyMessage(0);
                                }
                            }).start();
                        }
                    });
                    /**每一个item的点击事件，跳转到一个动态详细页面*/
                    listViewvip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(),"点我"+position+"看详情",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.user_inf, container, false);
                    userName = (TextView)rootView.findViewById(R.id.uinfm_username);
                    userHeight = (TextView)rootView.findViewById(R.id.uinfm_textView6);
                    userJob = (TextView)rootView.findViewById(R.id.uinfm_textView12);
                    userBirth = (TextView)rootView.findViewById(R.id.uinfm_textView8);
                    userCity = (TextView)rootView.findViewById(R.id.uinfm_textView14);
                    userSanwei = (TextView)rootView.findViewById(R.id.uinfm_textView10);
                    //TODO
                    /**
                     * 此处set上面几个获得的信息
                     * userName.setText(。。。);
                     */

                    break;
            }
            return rootView;
        }
        //handler
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                swipeRefreshLayout.setRefreshing(false);
                dynamicList = data.sendRequestWithHttpURLConnection(url+accountID);
                voiceList = voiceData.sendRequestWithVoiceURL(url_voice);//获取服务器语音数据
                vipSquareAdapter = new VipSquareAdapter(getContext(),dynamicList,voiceList);
                listViewvip.setAdapter(vipSquareAdapter);
            }
        };
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "动态";
                case 1:
                    return "资料";
            }
            return null;
        }
    }
}
