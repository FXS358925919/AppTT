package com.TT.SincereAgree.pocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.TT.SincereAgree.R;

import java.util.ArrayList;
import java.util.List;

public class RechargeViewPageActivity extends AppCompatActivity {
    private int integralCount;
    private int sincerityCount;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"积分", "诚意分"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Intent intent = getIntent();
        integralCount = intent.getIntExtra("integralCount",0);
        sincerityCount = intent.getIntExtra("sincerityCount", 0);
        //实例化
        viewPager = (ViewPager) findViewById(R.id.test_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.test_tablayout);
        //页面，数据源
        list = new ArrayList<>();
        Bundle integralBundle = new Bundle();
        integralBundle.putInt("integralNum", integralCount);
        Bundle sincerictyBundle = new Bundle();
        sincerictyBundle.putInt("sincerityNum", sincerityCount);
        RechargeIntegralFragment RechargeIntegralFragment = new RechargeIntegralFragment();
        RechargeIntegralFragment.setArguments(integralBundle);
        RechargeSincerityFragment rechargeSincerityFragment = new RechargeSincerityFragment();
        rechargeSincerityFragment.setArguments(sincerictyBundle);
        list.add(RechargeIntegralFragment);
        list.add(rechargeSincerityFragment);
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
