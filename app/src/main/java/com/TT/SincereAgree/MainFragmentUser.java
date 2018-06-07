package com.TT.SincereAgree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.TT.SincereAgree.mainpage.NineAdapter;
import com.TT.SincereAgree.mainpage.NineBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by TT on 2017/12/17.
 * Email:admin@TT.com
 */

public class MainFragmentUser extends Fragment {
    View view;
    private Toolbar toolbar=null;
    AppCompatActivity pMainActivity;
    private List<NineBean> list = new ArrayList<NineBean>();

    public static MainFragmentUser newInstance(String info) {
        Bundle args = new Bundle();
        MainFragmentUser fragment = new MainFragmentUser();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainpage_user, null);
        initList();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mainpage_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainFragmentUser.this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        NineAdapter nineAdapter = new NineAdapter(list);
        recyclerView.setAdapter(nineAdapter);

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
                    System.out.println("===="+responseData);
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
                    Snackbar.make(toolbar,"Click Search",Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_mainpage_message:
                    Snackbar.make(toolbar,"消息按钮被点击 in user",Snackbar.LENGTH_SHORT).show();
                    //testLoadImage();
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar,"Click More",Snackbar.LENGTH_SHORT).show();
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
        pMainActivity = (AppCompatActivity) getActivity();
        toolbar= (Toolbar) pMainActivity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.getMenu().clear();
        //title
        toolbar.setTitle("");
        TextView tv_title = (TextView)pMainActivity.findViewById(R.id.mainpage_toolbar_title);
        tv_title.setText("主页");
        pMainActivity.setSupportActionBar(toolbar);
        //设置筛选Icon，必须在setSupportActionBar(toolbar)之后设置
        toolbar.setNavigationIcon(R.mipmap.ic_menu_filter_24dp);
        //添加筛选菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar, "用户界面消息被获取", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }
    /*
    private void testLoadImage(){
//        /*      for test - load images
        int ScreenHeight = Utilities.getScreenHeight(this.getContext());// - Utilities.dip2px(this.getContext(), 45);   //需要减去菜单栏高度
        int ScreenWidth = Utilities.getScreenWidth(this.getContext());
        ImageView imageView1 = (ImageView)view.findViewById(R.id.MainPage_UserPhoto_1);
        int imageView_h = (int)((double)ScreenWidth * 0.4);
        int imageView_w = (int)((double)ScreenWidth * 0.6);
        imageView1.setMinimumHeight(imageView_h);
        imageView1.setMaxHeight(imageView_h);
        imageView1.setMinimumWidth(imageView_w);
        imageView1.setMaxWidth(imageView_w);
        imageView1.setImageResource(R.mipmap.pic1);
        //Bitmap bm = getLoacalBitmap("/picTest/pic1.png");
        //Bitmap bm = getURLimage("http://images.csdn.net/20130609/zhuanti.jpg");
        //Bitmap bm = decodeFromUrl("http://images.csdn.net/20130609/zhuanti.jpg");
        //imageView1.setImageBitmap(bm);
//
    }*/

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static Bitmap decodeFromUrl(String url)
    {
        Bitmap bitmap = null;
        try
        {
            URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(2000);
            connection.connect();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
