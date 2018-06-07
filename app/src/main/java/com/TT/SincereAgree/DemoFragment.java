package com.TT.SincereAgree;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TT on 2017/12/01.
 * Email:admin@TT.com
 */

public class DemoFragment extends Fragment {
    public static DemoFragment newInstance(String info) {
        Bundle args = new Bundle();
        DemoFragment fragment = new DemoFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        tvInfo.setText(getArguments().getString("info"));
        //makeText(this.getContext(), tvInfo.getText(), LENGTH_SHORT).show();
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
