package com.TT.SincereAgree;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TT on 2017/12/17.
 * Email:admin@TT.com
 */

public class MainFragmentWallet extends Fragment {
    private Toolbar toolbar=null;
    AppCompatActivity pMainActivity;

    public static MainFragmentWallet newInstance(String info) {
        Bundle args = new Bundle();
        MainFragmentWallet fragment = new MainFragmentWallet();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage_square, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        onFocus();
        return view;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListenerSquare=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_search:
                    Snackbar.make(toolbar,"Click Search",Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_mainpage_message:
                    Snackbar.make(toolbar,"消息按钮被点击 in wallet",Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar,"Click More",Snackbar.LENGTH_SHORT).show();
                    break;
            }

            return true;
        }
    };

    public void onFocus(){
        setHasOptionsMenu(true);
        pMainActivity = (AppCompatActivity) getActivity();
        toolbar= (Toolbar) pMainActivity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView)pMainActivity.findViewById(R.id.mainpage_toolbar_title);
        tv_title.setText("钱包");
        pMainActivity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.quit_22);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar, "钱包界面消息被获取", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListenerSquare);
    }
}
