package com.TT.SincereAgree.amei.common;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.util.TimeAndTransf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by Amei on 2018/4/25.
 */

public class VoiceData {
    private List<Voice> voiceList = new ArrayList<>();
    private List<Voice> voices;

    public VoiceData() {
    }

    public List<Voice> sendRequestWithVoiceURL(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * 根据url获取服务器的json数据responseData
                     */
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("-----------------------------"+responseData);
                    voices = parseJSONWithJSONArray(responseData);
                    voiceList.clear();

                    for (int i = 0; i<voices.size();i++){
                        Voice voice = new Voice(voices.get(i).getvId(),voices.get(i).getAccountId(),voices.get(i).getdCategory(),voices.get(i).getDyTime(),voices.get(i).getVoitime(),
                                voices.get(i).getAudper(),
                                voices.get(i).getVoiceUserName(),
                                voices.get(i).getVoUserHeadimag(),voices.get(i).getRaratingStart(),
                                voices.get(i).getVoiceUrl(),voices.get(i).getComment(),
                                voices.get(i).getFivenums());
                        voiceList.add(voice);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return voiceList;
    }

    private List<Voice> parseJSONWithJSONArray(String responseData) {
        List<Voice> voiceList = new ArrayList<>();
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            if(jSONObject.getInt("code") == 200)
                return null;

            JSONObject jsondata = jSONObject.getJSONObject("data");
            //所有语音动态相关数据
            JSONArray voiceArry = jsondata.getJSONArray("allVoice");
            for (int i = 0; i<voiceArry.length();i++){
                JSONObject jsonsigle = voiceArry.getJSONObject(i);//单条语音相关动态

                Voice voice = new Voice();
                List<Comment> commentList = new ArrayList<>();

                voice.setvId(jsonsigle.getString("vId"));
                voice.setAccountId(jsonsigle.getString("accountId"));
                voice.setdCategory(jsonsigle.getInt("vCategory"));
                voice.setVoitime(jsonsigle.getInt("vTime"));
                voice.setAudper(jsonsigle.getInt("vPeroflisen"));
                voice.setVoiceUserName(jsonsigle.getString("name"));//用户名
                String voUserHeadIma = Configure.rootUrl+"/mine/home/profile?url="+jsonsigle.getString("profile");
                voice.setVoUserHeadimag(voUserHeadIma);//用户头像

                int[] fivenums = new int[5];
                fivenums[0] = Integer.valueOf(jsonsigle.getString("support"));
                fivenums[1] = Integer.valueOf(jsonsigle.getString("unlike"));
                fivenums[2] = Integer.valueOf(jsonsigle.getString("gift"));
                fivenums[3] = Integer.valueOf(jsonsigle.getString("comment"));
                fivenums[4] = Integer.valueOf(jsonsigle.getString("share"));
                voice.setFivenums(fivenums);
                voice.setRaratingStart(jsonsigle.getDouble("vRate"));
                /**语音的地址*/
                String voiceURL = Configure.rootUrl+"/voice/voiceSource/voice1?url="+jsonsigle.getString("voiceUrl");
                voice.setVoiceUrl(voiceURL);//TODO

                /**从json数据中拿语音动态发布的时间字符串*/
                String voPublishTime = jsonsigle.getString("voicePubtime");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                String nowTime = dateFormat.format(new java.util.Date());

                voice.setDyTime(TimeAndTransf.getDatePoor(voPublishTime,nowTime));

                JSONArray commentArray = jsonsigle.getJSONArray("commentList");
                for (int j = 0;j<commentArray.length();j++){
                    JSONObject commentSigle = commentArray.getJSONObject(j);
                    Comment comment = new Comment();
                    if (commentSigle.getString("commentAccount")!="null")
                    {
                        comment.setUserId(commentSigle.getString("commentAccount"));
                        comment.setCommentUserName(commentSigle.getString("commentUserName"));
                        comment.setText(commentSigle.getString("content"));
                    }
                    commentList.add(comment);
                }
                voice.setComment(commentList);
                voiceList.add(voice);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return voiceList;
    }

}
