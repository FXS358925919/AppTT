package com.TT.SincereAgree.amei.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.DynamicAdapter;
import com.TT.SincereAgree.amei.common.Data;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Dynamic;

import java.util.List;

/**
 * Created by Amei on 2017/11/28.
 */
public class DynamicActivity extends AppCompatActivity{

    private DynamicAdapter dynamicAdapter;
    private ListView listDynamic;

    /**
     * 评论内容
     * 评论数据
     * */
    private List<Dynamic> dynamicList;
    private List<List<Comment>> dynamicCommentList;
    private List<Comment> dynamicHotcomment;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamiclist);
        findview();
        init();
    }

    public void findview(){
        listDynamic = (ListView)findViewById(R.id.dynamiclist);
    }

    public void init(){
        data = new Data();
        dynamicCommentList = data.initCommentData();
        dynamicList = data.initDynamic();

        dynamicAdapter = new DynamicAdapter(this,dynamicList,dynamicHotcomment);
        listDynamic.setAdapter(dynamicAdapter);
    }
}
