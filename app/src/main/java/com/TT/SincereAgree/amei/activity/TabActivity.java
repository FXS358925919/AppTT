package com.TT.SincereAgree.amei.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.DynamicAdapter;
import com.TT.SincereAgree.amei.adapter.SquareDateAdapter;
import com.TT.SincereAgree.amei.adapter.VoiceSquareAdapter;
import com.TT.SincereAgree.amei.common.Constant;
import com.TT.SincereAgree.amei.common.Data;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.amei.entity.SquareDate;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.util.SquareDateThreadHelper;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    private String[] squareTitle;
    private List<String> squareTitlelist = new ArrayList<>();
    private List<Fragment> list;
    private FragmentManager manager;
    private FragmentTransaction transtion;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.tab1_container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
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
        manager = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(manager);
        transtion = manager.beginTransaction();
        transtion.commit();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // tablayout.addTab可以将标题添加进Tab里面，true表示默认选中
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(1)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(2)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(3)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(4)), false);
        tabLayout.addTab(tabLayout.newTab().setText(squareTitlelist.get(5)), false);
        //这两个方法是将Tablayout和Viewpager联合起来
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
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
        private  ImageView imavip;


        ListView listViewimatext,listViewvoice;

        private Data data = new Data();
        /**
         * case 2*/
        private DynamicAdapter dynamicAdapter;
        private List<Dynamic> dynamicList;
        private List<List<Comment>> dynamicCommentList;
        private List<Comment> dynamicHotComment;

        /**
         * case 3*/
        private VoiceSquareAdapter voiceSquareAdapter;
        private List<Voice> voiceList;
        private List<List<Comment>> voiCommentList;
        //private Data data;
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    rootView = inflater.inflate(R.layout.square_vip, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.square_imagetext, container, false);
                    listViewimatext = (ListView) rootView.findViewById(R.id.square_imatextlist);
                    dynamicCommentList = data.initSquareCommentData();
                    dynamicList = data.initSquareDynamic();
                    dynamicAdapter = new DynamicAdapter(inflater.getContext(),dynamicList,dynamicHotComment);
                    listViewimatext.setAdapter(dynamicAdapter);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.square_voice, container, false);
                    listViewvoice = (ListView) rootView.findViewById(R.id.square_voicelist);

                    voiCommentList = data.initVoiceCommentData();
                    voiceList = data.initVoice();
                    voiceSquareAdapter = new VoiceSquareAdapter(inflater.getContext(),voiceList,voiCommentList);
                    listViewvoice.setAdapter(voiceSquareAdapter);
                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.square_video, container, false);
                    TextView textView3 = (TextView) rootView.findViewById(R.id.section_labe4);
                    textView3.setText("视频");
                break;
                case 5:
                    SquareDateThreadHelper helper = new SquareDateThreadHelper();
                    helper.sendRequestForSquareDate();
                    list = helper.list;
                    rootView = inflater.inflate(R.layout.square_paiddate, container, false);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.square_date_recyclerview1);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    SquareDateAdapter adapter = new SquareDateAdapter(list);
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
}
