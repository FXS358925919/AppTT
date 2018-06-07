package com.TT.SincereAgree.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.TT.SincereAgree.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivityNext extends AppCompatActivity {

    private Button regButton;
    private ImageView addPicture;
    private RadioButton sexRadio;
    private RelativeLayout layout;
    private static final int DATE_DIALOG = 1;
    private static final int CHOOSE_PHOTO = 2;
    private String imagePath = null;
    private EditText nicknameText;
    private EditText birthDateText;
    int mYear, mMonth, mDay;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        regButton = (Button) findViewById(R.id.reg_next_button);
        addPicture = (ImageView) findViewById(R.id.reg_next_addpicture);
        sexRadio = (RadioButton) findViewById(R.id.reg_next_male);
        nicknameText = (EditText) findViewById(R.id.reg_next_nicknametext);
        birthDateText = (EditText) findViewById(R.id.reg_next_age_text);
        layout = (RelativeLayout) findViewById(R.id.reg_next_DatePickerDialog);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegisterActivityNext.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivityNext.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imagePath == null){
                    Toast.makeText(RegisterActivityNext.this,"请选择头像",Toast.LENGTH_SHORT).show();
                }else{
                    boolean isMale = sexRadio.isChecked();
                    String nickname = nicknameText.getText().toString();
                    String birth = birthDateText.getText().toString();
                    int age = yearsBetween(birth, sdf.format(new Date()));
                    Intent intent = new Intent(RegisterActivityNext.this,RelationActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    intent.putExtra("imagepath",imagePath);
                    intent.putExtra("sex",isMale);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("age",String.valueOf(age));
                    startActivity(intent);
                }
            }
        });
        birthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    public void display() {
        birthDateText.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    //打开相册
    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    //获取权限结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }


    //开启活动后的返回处理代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //判断是打开摄像头还是点开相册
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImaage(imagePath);

    }


    //得到图片u'r'l
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImaage(imagePath);
    }


    //得到图片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //显示图片
    private void displayImaage(String imagePath) {
        if(imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            addPicture.setImageBitmap(bitmap);
        }else
            Toast.makeText(this,"fail to get image",Toast.LENGTH_SHORT).show();
    }

    public static int yearsBetween(String start, String end){
        try{
            startDate.setTime(sdf.parse(start));
            endDate.setTime(sdf.parse(end));
            return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

}
