package com.TT.SincereAgree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.amei.activity.SquareDetailDyActivity;
import com.TT.SincereAgree.amei.activity.SquareDetailVoiActivity;
import com.TT.SincereAgree.amei.adapter.DynamicAdapter;
import com.TT.SincereAgree.amei.adapter.SquareDateAdapter;
import com.TT.SincereAgree.amei.adapter.VipSquareAdapter;
import com.TT.SincereAgree.amei.adapter.VoiceSquareAdapter;
import com.TT.SincereAgree.amei.common.Constant;
import com.TT.SincereAgree.amei.common.Data;
import com.TT.SincereAgree.amei.common.VoiceData;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.amei.entity.SquareDate;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.util.SquareDateThreadHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TT on 2017/12/17.
 * Email:admin@TT.com
 */

public class MainFragmentSquare extends Fragment {
    private String[] squareTitle;
    private List<String> squareTitlelist = new ArrayList<>();
    private List<Fragment> list;
    private FragmentManager manager;
    private FragmentTransaction transtion;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    TabLayout tabLayout;


    private Toolbar toolbar=null;
    AppCompatActivity pMainActivity;

    public static MainFragmentSquare newInstance(String info) {
        Bundle args = new Bundle();
        MainFragmentSquare fragment = new MainFragmentSquare();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab1,  container, false);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.tab1_container);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        squareTitle = Constant.SQUARE_TITLE;
        for (int i =0;i<squareTitle.length;i++){
            squareTitlelist.add(squareTitle[i]);
        }
        list = new ArrayList<Fragment>();
        Fragment f1 = new Fragment();
        Fragment f2 = new Fragment();
        Fragment f3 = new Fragment();
        Fragment f4 = new Fragment();
        Fragment f5 = new Fragment();
        Fragment f6 = new Fragment();
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        list.add(f5);
        list.add(f6);
        // Create the adapter that will return a fragment
        manager = getChildFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(manager);
        transtion = manager.beginTransaction();
        transtion.commit();
        mViewPager.setAdapter(mSectionsPagerAdapter);
         //tablayout.addTab可以将标题添加进Tab里面，true表示默认选中
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(1)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(2)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(3)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(4)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(5)), false);

        //这两个方法是将Tablayout和Viewpager联合起来
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        onFocus();


        return view;
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_tab, menu);
            return true;
        }
    */
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
        String url = "/dynamic/dynamicallVO";
        String url_voice = "/voice/voiceallVO";

        ListView listViewvip,listViewimatext,listViewvoice;

        private Data data = new Data();
        private VoiceData voiceData = new VoiceData();
        /**
         * case 1*/
        private VipSquareAdapter vipSquareAdapter;
        /**下拉刷新*/
        private SwipeRefreshLayout swipeRefreshLayout;
        /**
         * case 2*/
        private DynamicAdapter dynamicAdapter;
        private List<Dynamic> dynamicList;
        /**
         * case 3*/
        private VoiceSquareAdapter voiceSquareAdapter;
        private List<Voice> voiceList;
        private List<SquareDate> squareDateList;


        /**
         * case 5*/
        private List<SquareDate> list = new ArrayList<SquareDate>();

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

        //handler
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        swipeRefreshLayout.setRefreshing(false);
                        dynamicList = data.sendRequestWithHttpURLConnection(url);
                        voiceList = voiceData.sendRequestWithVoiceURL(url_voice);//获取服务器语音数据
                        vipSquareAdapter = new VipSquareAdapter(getContext(),dynamicList,voiceList);
                        listViewvip.setAdapter(vipSquareAdapter);
                        //adapter.notifyDataSetChanged();
                        //swipeRefreshLayout.setEnabled(false);
                        break;
                    case 2:
                        swipeRefreshLayout.setRefreshing(false);
                        dynamicList = data.sendRequestWithHttpURLConnection(url);
                        dynamicAdapter = new DynamicAdapter(getContext(),dynamicList);
                        listViewimatext.setAdapter(dynamicAdapter);
                        break;
                    case 3:
                        swipeRefreshLayout.setRefreshing(false);
                        voiceList = voiceData.sendRequestWithVoiceURL(url_voice);
                        voiceSquareAdapter = new VoiceSquareAdapter(getContext(),voiceList);
                        listViewvoice.setAdapter(voiceSquareAdapter);
                        break;
                    default:
                        break;
                }
            }
        };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            dynamicList = data.sendRequestWithHttpURLConnection(url);
            voiceList = voiceData.sendRequestWithVoiceURL(url_voice);//获取服务器语音数据
            SquareDateThreadHelper helper = new SquareDateThreadHelper();
            helper.sendRequestForSquareDate();
            squareDateList = helper.list;

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
                                    mHandler.sendEmptyMessage(1);
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
                    rootView = inflater.inflate(R.layout.square_imagetext, container, false);
                    listViewimatext = (ListView) rootView.findViewById(R.id.square_imatextlist);
                    dynamicAdapter = new DynamicAdapter(inflater.getContext(),dynamicList);
                    listViewimatext.setAdapter(dynamicAdapter);
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
                                    mHandler.sendEmptyMessage(2);
                                }
                            }).start();
                        }
                    });
                    /**每一个item的点击事件，跳转到一个动态详细页面*/
                    listViewimatext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Dynamic detailDynamic = dynamicList.get(position);
                            Intent intent = new Intent(getContext(), SquareDetailDyActivity.class);
                            intent.putExtra("dynamic",detailDynamic);
                            startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.square_voice, container, false);
                    listViewvoice = (ListView) rootView.findViewById(R.id.square_voicelist);
                    voiceSquareAdapter = new VoiceSquareAdapter(inflater.getContext(),voiceList);
                    listViewvoice.setAdapter(voiceSquareAdapter);
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
                                    mHandler.sendEmptyMessage(3);
                                }
                            }).start();
                        }
                    });
                    /**每一个item的点击事件，跳转到一个动态详细页面*/
                    listViewvoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Voice detailVoice = voiceList.get(position);
                            Intent intent = new Intent(getContext(), SquareDetailVoiActivity.class);
                            intent.putExtra("voice",detailVoice);
                            startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.square_video, container, false);
                    TextView textView3 = (TextView) rootView.findViewById(R.id.section_labe4);
                    textView3.setText("视频");
                    break;
                case 5:
                    rootView = inflater.inflate(R.layout.square_paiddate, container, false);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.square_date_recyclerview1);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    SquareDateAdapter adapter = new SquareDateAdapter(squareDateList);
                    recyclerView.setAdapter(adapter);
                    break;
                case 6:
                    rootView = inflater.inflate(R.layout.square_resmarket, container, false);
                    TextView textView5 = (TextView) rootView.findViewById(R.id.section_labe6);
                    textView5.setText("资源市场");
                    break;
            }
            return rootView;

        }
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
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return squareTitlelist.get(position);
        }
    }

    public void onFocus(){
        setHasOptionsMenu(true);
        pMainActivity = (AppCompatActivity) getActivity();

        toolbar= (Toolbar) pMainActivity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
}
