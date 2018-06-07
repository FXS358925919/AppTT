package com.TT.SincereAgree.amei.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.GridViewAdapter;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.common.PostFile;
import com.TT.SincereAgree.amei.photopicker.PhotoPickerActivity;
import com.TT.SincereAgree.amei.photopicker.PhotoPreviewActivity;
import com.TT.SincereAgree.amei.photopicker.SelectModel;
import com.TT.SincereAgree.amei.photopicker.intent.PhotoPickerIntent;
import com.TT.SincereAgree.amei.photopicker.intent.PhotoPreviewIntent;
import com.TT.SincereAgree.amei.util.LogUtil;
import com.TT.SincereAgree.amei.view.TitleBar;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * 发布图文
 */
public class SendDynamicActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;

    private TitleBar titleBar;
    private GridView gridView;

    private GridViewAdapter gridAdapter;

    List<Bitmap> allImages = new ArrayList<Bitmap>();
    List<String> imageUrl = new ArrayList<String>();

    private ArrayList<String> imagePaths = new ArrayList<>();

    private boolean selectFlag = false;//选择的状态
    private Dialog dialog;

    private EditText etMood;

    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
    /**上传文件的路径(图片)*/
    String netURL = rootUrl + "/uploadFile/upload";
    /**发布的内容*/
    private String textContent;
    /**发布的时间*/
    private String releasetime;
    /**发布的图片的名称*/
    private String pictureName;
    private StringBuilder picNames = new StringBuilder();
    /**发布的账号ID*/
    private int state;//发布成功与否的状态码
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writedynamic);
        app = (App) this.getApplication();
        initViews();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        titleBar = (TitleBar) findViewById(R.id.writedy_titleBar);
        titleBar.showLeftImageAndRightStr("发布", "发布", "取消");
        titleBar.clickright(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textContent = etMood.getText().toString().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                releasetime = dateFormat.format(new Date());
                final HashMap<String,String> map = new HashMap<String, String>();
                map.put("accountId",app.getUserID());
                map.put("dContent",textContent);
                map.put("dReleasetime",releasetime);
                for (int m = 0;m<imagePaths.size();m++)
                    System.out.println("##############"+imagePaths.get(m));

                if (!textContent.isEmpty()) {
                    /**
                     * 此处将本地图片上传至服务器
                     * 本地图片地址在imagePaths中*/
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int m = 0;m<imagePaths.size()-2;m++){
                                pictureName = PostFile.post_file(netURL,null,new File(imagePaths.get(m)),1);
                                pictureName = "/00/00/00/"+pictureName.substring(2,pictureName.length()-2);
                                picNames.append(pictureName);
                                picNames.append(',');
                            }
                            pictureName = PostFile.post_file(netURL,null,new File(imagePaths.get(imagePaths.size()-2)),1);
                            pictureName = "/00/00/00/"+pictureName.substring(2,pictureName.length()-2);
                            picNames.append(pictureName);
                            pictureName = picNames.toString();
                            map.put("dyima",pictureName);
                            state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postDynamic");
                            if (state == 1)
                                handler.sendEmptyMessage(0);
                        }
                    }).start();
                    Toast.makeText(SendDynamicActivity.this, "正在发布", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendDynamicActivity.this, "内容不能为空!", Toast.LENGTH_LONG).show();
                }
            }
        });
        titleBar.clickleft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void initViews() {
        gridView = (GridView) findViewById(R.id.writedy_gridView);
        etMood = (EditText) findViewById(R.id.writedy_etMood);

        allImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.addima));

        imagePaths.add("addima");
        gridAdapter = new GridViewAdapter(SendDynamicActivity.this, imagePaths);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == imagePaths.size() - 1) {
                    if (imagePaths.size() <= 9) {
                        //new SelectPopuWindow(SendDynamicActivity.this, gridView, position);

                        PhotoPickerIntent intent = new PhotoPickerIntent(SendDynamicActivity.this);
                        intent.setSelectModel(SelectModel.MULTI);
                        intent.setShowCarema(true); // 是否显示拍照
                        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                        intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                        startActivityForResult(intent,REQUEST_CAMERA_CODE);

                    } else {
                        Toast.makeText(SendDynamicActivity.this, "照片数量不能超过9张",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    //否则是点击的图片本身，则做放大图片预览处理
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(SendDynamicActivity.this);
                    intent.setCurrentItem(position);
                    imagePaths.remove(imagePaths.size()-1);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("delImage");
        registerReceiver(delBroadCast, intentFilter);

    }

    BroadcastReceiver delBroadCast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            String action = arg1.getAction();
            if (action.equals("delImage")) {
                int position = arg1.getExtras().getInt("position");
                Log.i("广播收到的删除下标为:", position + "");
                imageUrl.remove(position);
                allImages.remove(position);
                gridAdapter.notifyDataSetChanged();

                for (int i = 0; i < imageUrl.size(); i++) {
                    LogUtil.i("图片:" + imageUrl.get(i));
                }
            }

        }
    };






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d("---", "数量："+list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }
    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("addima")){
            paths.remove("addima");
        }
        paths.add("addima");
        imagePaths.addAll(paths);
        gridAdapter  = new GridViewAdapter(SendDynamicActivity.this,imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(SendDynamicActivity.this, "发布成功", Toast.LENGTH_LONG).show();
            finish();
        }

    };

}
