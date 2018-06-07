package com.TT.SincereAgree.amei.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.common.Constant;
import com.TT.SincereAgree.amei.entity.Album;
import com.TT.SincereAgree.amei.entity.ImgInfo;
import com.TT.SincereAgree.amei.entity.PersonalAlbum;
import com.TT.SincereAgree.amei.util.FastBlurUtil;
import com.TT.SincereAgree.amei.util.RemoteImageURL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;
import static com.TT.SincereAgree.amei.util.FastBlurUtil.GetBitmap;

public class BlurImageActivity extends AppCompatActivity {
    private static final int defaultImgNum = 4;
    private GridView freeGridView;
    private GridView chargeGridView;
    private GridView vipFreeGridView;

    private RemoteImageURL remoteIma;
    private String friendid;
    // 三种不同类型图片的url
    private List<String> freeImgs = null;
    private List<String> vipFreeImgs = null;
    private List<String> chargeImgs = null;
    // 三种不同类型图片的Bitmap，变量名中带Blur字样的是模糊化后的图片
    private Bitmap[] freeImgsBitmap;
    private Bitmap[] vipFreeImgsBitmap;
    private Bitmap[] vipFreeImgsBlurBitmap;
    private Bitmap[] chargeImgsBitmap;
    private Bitmap[] chargeImgsBlurBitmap;
    // 三种不同类型图片的数量，小于等于4
    private int freeImgNum;
    private int vipFreeImgNum;
    private int chargeImgsNum;
    // 收费相册的对象
    private Album chargeAlbum;
    private String  chargeAlbumid;
    // 是否已经付款
    private boolean isCharged = false;
    // 是否VIP好友
    private boolean isVipFriend = false;
    private PersonalAlbum personalAlbum = new PersonalAlbum();
    private boolean paySuccess = false;

    private List<Map<String, Object>> freeDataList = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> chargeDataList = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> vipfreeDataList = new ArrayList<Map<String, Object>>();
    private SimpleAdapter freeGridadapter;
    private SimpleAdapter chargeGridadapter;
    private SimpleAdapter vipfreeGridadapter;

    private ImageView backgroundImageView;
    private ImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_image);
        backgroundImageView = (ImageView) findViewById(R.id.person_iv_background);
        circleImageView = (ImageView) findViewById(R.id.person_iv_head);
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(backgroundImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(circleImageView);

        findview();
        initData();
    }

    private void findview() {
        freeGridView = (GridView) findViewById(R.id.life_grid_free);
        chargeGridView = (GridView) findViewById(R.id.life_grid_charge);
        vipFreeGridView = (GridView) findViewById(R.id.life_grid_vipfree);
    }

    private void initData() {
        String[] from = {"img"};
        int[] to = {R.id.iv_grid_item};
        freeGridadapter = new SimpleAdapter(this, freeDataList, R.layout.activity_blur_grid_item, from, to);
        chargeGridadapter = new SimpleAdapter(this, chargeDataList, R.layout.activity_blur_grid_item, from, to);
        vipfreeGridadapter = new SimpleAdapter(this, vipfreeDataList, R.layout.activity_blur_grid_item, from, to);
        freeGridadapter.setViewBinder(viewBinder);
        chargeGridadapter.setViewBinder(viewBinder);
        vipfreeGridadapter.setViewBinder(viewBinder);
        freeGridView.setAdapter(freeGridadapter);
        chargeGridView.setAdapter(chargeGridadapter);
        vipFreeGridView.setAdapter(vipfreeGridadapter);

        Intent intent = getIntent();
        friendid = intent.getStringExtra("ACCOUNTID");//用户的id
        remoteIma = new RemoteImageURL();
        /**
         *获取远程图片并模糊化
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 根据url获取服务器的json数据responseData
                 */
                App app = (App) getApplication();
                String accountid = app.getUserID();
                String url = "album/getPersonalAlbums?watcher=" + accountid + "&watched=" + friendid;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(rootUrl + url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        System.out.println("--->" + responseData);
                        personalAlbum = parseJSONWithJSONArray(responseData);
                        // 获得第一个收费相册,以及其收费与否
                        if (personalAlbum.getChargeAlbum() != null && personalAlbum.getChargeAlbum().size() > 0) {
                            chargeAlbum = personalAlbum.getChargeAlbum().get(0);
                            chargeAlbumid = chargeAlbum.getAlbumid();
                            if (personalAlbum.getCharged() != null) {
                                System.out.println(personalAlbum.getCharged());
                                isCharged = personalAlbum.getCharged().get(chargeAlbumid);
                                System.out.println("--->" + isCharged);
                            }
                        }
                        isVipFriend = personalAlbum.getVipFriend();
                        freeImgs = getFreeImgs();
                        vipFreeImgs = getVipFreeImgs();
                        chargeImgs = getChargeImgs();
                        // 图片数量不大于4个
                        freeImgNum = freeImgs.size() > defaultImgNum ? defaultImgNum : freeImgs.size();
                        vipFreeImgNum = vipFreeImgs.size() > defaultImgNum ? defaultImgNum : vipFreeImgs.size();
                        chargeImgsNum = chargeImgs.size() > defaultImgNum ? defaultImgNum : chargeImgs.size();
                        int scaleRatio = Integer.parseInt(Constant.BLUR_LEVEL);

                        // 下面的这个方法必须在子线程中执行
                        freeImgsBitmap = new Bitmap[freeImgNum];
                        vipFreeImgsBitmap = new Bitmap[vipFreeImgNum];
                        vipFreeImgsBlurBitmap = new Bitmap[vipFreeImgNum];
                        chargeImgsBitmap = new Bitmap[chargeImgsNum];
                        chargeImgsBlurBitmap = new Bitmap[chargeImgsNum];
                        for (int i = 0; i < freeImgNum; i++) {
                            freeImgsBitmap[i] = GetBitmap(freeImgs.get(i));
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("img", freeImgsBitmap[i]);
                            freeDataList.add(map);
                        }
                        for (int i = 0; i < vipFreeImgNum; i++) {
                            vipFreeImgsBitmap[i] = FastBlurUtil.GetBitmap(vipFreeImgs.get(i));
                            vipFreeImgsBlurBitmap[i] = FastBlurUtil.toBlur(vipFreeImgsBitmap[i], scaleRatio);
                            Map<String, Object> map = new HashMap<String, Object>();
                            if (isVipFriend)
                                map.put("img", vipFreeImgsBitmap[i]);
                            else
                                map.put("img", vipFreeImgsBlurBitmap[i]);
                            vipfreeDataList.add(map);
                        }
                        for (int i = 0; i < chargeImgsNum; i++) {
                            chargeImgsBitmap[i] = FastBlurUtil.GetBitmap(chargeImgs.get(i));
                            chargeImgsBlurBitmap[i] = FastBlurUtil.toBlur(chargeImgsBitmap[i], scaleRatio);
                            Map<String, Object> map = new HashMap<String, Object>();
                            if ( isCharged )
                                map.put("img", chargeImgsBitmap[i]);
                            else
                                map.put("img", chargeImgsBlurBitmap[i]);
                            chargeDataList.add(map);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                freeGridadapter.notifyDataSetChanged();
                                chargeGridadapter.notifyDataSetChanged();
                                vipfreeGridadapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }).start();

        /**
         * 查看全部公开图片
         * */
        freeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent userima_detail = new Intent(BlurImageActivity.this, UserImaDetailActivity.class);
                userima_detail.putExtra("URL", (Serializable) freeImgs);
                startActivity(userima_detail);
            }
        });

        /**
         * 查看付费图片，需先支付
         * */
        chargeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 如果没有付费需要在这里付费
                if (!isCharged) {
                    new AlertDialog.Builder(BlurImageActivity.this).setTitle("提示")
                            .setMessage("支付" + chargeAlbum.getPrice() + "可查看图片" + "你确定要支付吗？").
                            setPositiveButton("确定", payimaListener).
                            setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                } else {//如果已经付费就可以直接进去看图片
                    System.out.println("into next activity");
                }
            }

            DialogInterface.OnClickListener payimaListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        addFileTradeForChargeAlbum();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (paySuccess) {
                        Toast.makeText(BlurImageActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                        isCharged = true;
                    } else {
                        Toast.makeText(BlurImageActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                    }
                }
            };
        });
    }



    private static PersonalAlbum parseJSONWithJSONArray(String responseData) {
        PersonalAlbum personalAlbum = null;
        try {
            JSONObject object = JSON.parseObject(responseData);
            if ( object.getInteger("code") == 200 )
                return null;
            personalAlbum = object.getJSONObject("data").getObject("personal",PersonalAlbum.class);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return personalAlbum;
    }

    public List<String> getFreeImgs(){
        List<String> res = new ArrayList<>();
        if ( personalAlbum.getFreeAlbum() != null && personalAlbum.getFreeAlbum().size() != 0 ){
            for (Album album: personalAlbum.getFreeAlbum()){
                if ( album.getImgs() != null && album.getImgs().size() != 0 ) {
                    for (ImgInfo info : album.getImgs()) {
                        res.add(Configure.albumImgUrl + info.getImgurl());
                    }
                }
            }
        }
        return res;
    }

    public List<String> getVipFreeImgs(){
        List<String> res = new ArrayList<>();
        if ( personalAlbum.getVipFreeAlbum() != null && personalAlbum.getVipFreeAlbum().size() != 0 ){
            for (Album album: personalAlbum.getVipFreeAlbum()){
                if ( album.getImgs() != null && album.getImgs().size() != 0 ) {
                    for (ImgInfo info : album.getImgs()) {
                        res.add(Configure.albumImgUrl + info.getImgurl());
                    }
                }
            }
        }
        return res;
    }

    public List<String> getChargeImgs(){
        List<String> res = new ArrayList<>();
        if ( personalAlbum.getChargeAlbum() != null && personalAlbum.getChargeAlbum().size() != 0 ){
            for (Album album: personalAlbum.getChargeAlbum()) {
                if (album.getImgs() != null && album.getImgs().size() != 0) {
                    for (ImgInfo info : album.getImgs()) {
                        res.add(Configure.albumImgUrl + info.getImgurl());
                    }
                }
            }
        }
        return res;
    }


    //添加付费记录
    public void addFileTradeForChargeAlbum() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("acceptside",friendid).add("fileid",chargeAlbumid).add("charge",chargeAlbum.getPrice()+"").build();
                    Request request = new Request.Builder().url(rootUrl+"filetrade/addAlbumTrade").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("--->"+responseData);
                    org.json.JSONObject jSONObject = new org.json.JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 100) {
                        paySuccess = true;
                    }
                    else {
                        paySuccess = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

    //以下方法通过setViewBinder接口将bitmap转化一下 这一步很重要！
    private SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {
        @Override
        public boolean setViewValue(View view, Object bitmapData, String s) {
            if(view instanceof ImageView && bitmapData instanceof Bitmap){
                ImageView i = (ImageView)view;
                i.setImageBitmap((Bitmap) bitmapData);
                return true;
            }
            return false;
        }
    };


}
