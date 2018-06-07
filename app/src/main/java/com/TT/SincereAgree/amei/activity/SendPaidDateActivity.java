package com.TT.SincereAgree.amei.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.GridViewAdapter;
import com.TT.SincereAgree.amei.adapter.PaidDateTypeAdapter;
import com.TT.SincereAgree.amei.common.Constant;
import com.TT.SincereAgree.amei.photopicker.PhotoPickerActivity;
import com.TT.SincereAgree.amei.photopicker.PhotoPreviewActivity;
import com.TT.SincereAgree.amei.photopicker.SelectModel;
import com.TT.SincereAgree.amei.photopicker.intent.PhotoPickerIntent;
import com.TT.SincereAgree.amei.photopicker.intent.PhotoPreviewIntent;
import com.TT.SincereAgree.amei.util.LogUtil;
import com.TT.SincereAgree.amei.view.TitleBar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SendPaidDateActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;

    private TitleBar titleBar;
    private GridView imaloadgridView,p_datetypegridView;
    private String[] type;
    private List<String> typelist = new ArrayList<>();
    private int typeindex;

    private PaidDateTypeAdapter paidDateTypeAdapter;
    private GridViewAdapter gridAdapter;
    List<Bitmap> allImages = new ArrayList<Bitmap>();
    List<String> imageUrl = new ArrayList<String>();
    private ArrayList<String> imagePaths = new ArrayList<>();
    private EditText etMoodp;

    private final String[] items = Constant.SEX;
    private RelativeLayout p_datetimeLayout,p_datesexLayout;
    private TextView p_datetimeTV,p_datesexTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_paid_date);
        initViews();
        init();
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.showLeftImageAndRightStr("发布", "发布", "取消");
        titleBar.clickright(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMoodp.getText().toString().trim().isEmpty()) {
                    /**
                     * 此处将本地图片上传至服务器
                     * 本地图片地址在imagePaths中*/
                    Toast.makeText(SendPaidDateActivity.this, imagePaths.get(0), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendPaidDateActivity.this, "内容不能为空!", Toast.LENGTH_LONG).show();
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
        imaloadgridView = (GridView) findViewById(R.id.gridViewp);
        p_datetypegridView = (GridView)findViewById(R.id.datatypegridview);
        etMoodp = (EditText) findViewById(R.id.etMoodp);

        p_datetimeLayout = (RelativeLayout)findViewById(R.id.datetimer);
        p_datesexLayout = (RelativeLayout)findViewById(R.id.datesexr);

        p_datesexTV = (TextView)findViewById(R.id.setdatesex);
        p_datetimeTV = (TextView)findViewById(R.id.setdatetime);
    }
    private void init(){

        p_datetimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SendPaidDateActivity.this);
                View view = View.inflate(SendPaidDateActivity.this, R.layout.telectimedialog,null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                builder.setView(view);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                builder.setTitle("请选择约会时间");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(String.format("%02d",timePicker.getCurrentHour()));
                        sb.append(":").append(String.format("%02d",timePicker.getCurrentMinute()));

                        p_datetimeTV.setText(sb);
                        dialog.cancel();
                    }
                }).create().show();



            }
        });

        p_datesexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(SendPaidDateActivity.this).setTitle("请选择报名性别")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                p_datesexTV.setText(items[which]);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });





        allImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.addima));


        type = Constant.DATA_TYPE;
        for (int i =0;i<type.length;i++){
            typelist.add(type[i]);
        }
        paidDateTypeAdapter = new PaidDateTypeAdapter(SendPaidDateActivity.this,typelist);
        p_datetypegridView.setAdapter(paidDateTypeAdapter);
        p_datetypegridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paidDateTypeAdapter.setSelected(position);
                typeindex = position;
                Log.e("---",String.valueOf(typeindex));
            }
        });


        imagePaths.add("addima");
        gridAdapter = new GridViewAdapter(SendPaidDateActivity.this, imagePaths);
        imaloadgridView.setAdapter(gridAdapter);
        imaloadgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == imagePaths.size() - 1) {
                    if (imagePaths.size() <= 7) {
                        PhotoPickerIntent intent = new PhotoPickerIntent(SendPaidDateActivity.this);
                        intent.setSelectModel(SelectModel.MULTI);
                        intent.setShowCarema(true); // 是否显示拍照
                        intent.setMaxTotal(7); // 最多选择照片数量，默认为7
                        intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                        startActivityForResult(intent,REQUEST_CAMERA_CODE);

                    } else {
                        Toast.makeText(SendPaidDateActivity.this, "照片数量不能超过7张",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    //否则是点击的图片本身，则做放大图片预览处理
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(SendPaidDateActivity.this);
                    intent.setCurrentItem(position);
                    imagePaths.remove(imagePaths.size()-1);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imaloadgridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

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
        gridAdapter  = new GridViewAdapter(SendPaidDateActivity.this,imagePaths);
        imaloadgridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
