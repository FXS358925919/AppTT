package com.TT.SincereAgree.amei.common;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * 上传文件到服务器（图片、语音）
 * Created by Amei on 2018/4/30.
 */
public class PostFile {

    private static String fileName;
    /**
     *上传file到服务器
     * @param url
     * @param map
     * @param file
     * @param type 上传文件的类型 1图片 2语音
     */
    public static String post_file(final String url, final Map<String, Object> map, final File file,int type) {
        OkHttpClient client = new OkHttpClient();
        /**form 表单形式上传*/
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            /**MediaType.parse() 里面是上传的文件类型*/
            RequestBody body;
            if (type == 1)
                body = RequestBody.create(MediaType.parse("image/*"), file);
            else
                body = RequestBody.create(MediaType.parse("audio/*"), file);

            /** 参数分别为， 请求key ，文件名称 ， RequestBody*/
            requestBody.addFormDataPart("myfiles", file.getName(), body);
        }
        if (map != null) {
            /**map 里面是请求中所需要的 key 和 value*/
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        /**readTimeout("请求超时时间" , 时间单位)*/
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            fileName = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("hahahaha",fileName);
        return fileName;
    }

}
