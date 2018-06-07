package com.TT.SincereAgree.amei.common;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.util.TimeAndTransf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by Amei on 2017/12/18.
 */

public class Data{
    private List<Dynamic> dynamicList = new ArrayList<>();
    private List<Comment> commentList,voicommentList;
    private List<List<Comment>> dynamicCommentList;

    private List<Voice> voiceList;
    private List<List<Comment>> voiceCommentList;

    public Data(){}

    public Data(List<Dynamic> dynamicList,List<List<Comment>> dynamicCommentList){
        this.dynamicList = dynamicList;
        this.dynamicCommentList = dynamicCommentList;
    }

    private List<Dynamic> dynamics;//服务器获取的动态
    public List<Dynamic> sendRequestWithHttpURLConnection(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(rootUrl + url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("-----------------------------"+responseData);
                    dynamics =   parseJSONWithJSONArray(responseData);
                    dynamicList.clear();
                    for(int i = 0; i<dynamics.size();i++)
                    {
                        dynamics.get(i).getDyTime();
                        Dynamic dynamic = new Dynamic(dynamics.get(i).getdId(),dynamics.get(i).getAccountId(),dynamics.get(i).getdCategory(),dynamics.get(i).getText(),
                                dynamics.get(i).getDyTime(),dynamics.get(i).getFivenums(),
                                dynamics.get(i).getDyUserImags(), dynamics.get(i).getComment(),
                                dynamics.get(i).getDynamicUserName(),dynamics.get(i).getDyUserHeadimag());
                        dynamicList.add(dynamic);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return dynamicList;
    }


    private List<Dynamic> parseJSONWithJSONArray(String responseData) {
        List<Dynamic> dynamics = new ArrayList<>();
        try {
            JSONObject jSONObject = new JSONObject(responseData);
            if(jSONObject.getInt("code") == 200)
                return null;

            JSONObject jsondata = jSONObject.getJSONObject("data");
            //所有的动态相关数据
            JSONArray DynamicArray = jsondata.getJSONArray("alldynamic");

            for(int i = 0; i<DynamicArray.length();i++)
            {
                JSONObject jsonsigle = DynamicArray.getJSONObject(i);

                Dynamic dynamic = new Dynamic();
                List<Comment> dyCommentList = new ArrayList<>();

                dynamic.setdId(jsonsigle.getString("dId"));
                dynamic.setAccountId(jsonsigle.getString("accountId"));
                dynamic.setdCategory(jsonsigle.getInt("dCategory"));
                dynamic.setText(jsonsigle.getString("dContent"));

                int[] fivenums = new int[5];
                fivenums[0] = Integer.valueOf(jsonsigle.getString("support"));
                fivenums[1] = Integer.valueOf(jsonsigle.getString("unlike"));
                fivenums[2] = Integer.valueOf(jsonsigle.getString("gift"));
                fivenums[3] = Integer.valueOf(jsonsigle.getString("comment"));
                fivenums[4] = Integer.valueOf(jsonsigle.getString("share"));
                dynamic.setFivenums(fivenums);

                /**从json数据中拿动态发布的时间字符串*/
                String dyPublishTime = jsonsigle.getString("dReleasetime");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                String nowTime = dateFormat.format(new Date());

                dynamic.setDyTime(TimeAndTransf.getDatePoor(dyPublishTime,nowTime));
                /**动态中的一张图片*/
                String url;
                String[] urls = new String[9];
                url = jsonsigle.getString("dyima");
                urls = url.split(",");
                for (int n = 0;n<urls.length;n++){
                    urls[n] = Configure.rootUrl+"/dynamic/dynamicImag/image?url="+urls[n];
                }
                dynamic.setDyUserImags(urls);

                dynamic.setDynamicUserName(jsonsigle.getString("name"));
                String dyUserHeadIma = Configure.rootUrl+"/mine/home/profile?url="+jsonsigle.getString("profile");
                dynamic.setDyUserHeadimag(dyUserHeadIma);

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
                    dyCommentList.add(comment);
                }
                dynamic.setComment(dyCommentList);
                dynamics.add(dynamic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dynamics;
    }





    /**
     * 用户心情动态、动态评论*/
    public List<List<Comment>> initCommentData(){
        dynamicCommentList = new ArrayList<>();

        commentList = new ArrayList<Comment>();
        Comment comment = new Comment("汪洋",":陈东阳逗比一般的存在", R.mipmap.goodcomment);
        commentList.add(comment);
        comment = new Comment("周用",":特别好！",0);
        commentList.add(comment);
        comment = new Comment("丫丫",":我喜欢你的图片！",0);
        commentList.add(comment);
        comment = new Comment("海天相接",":同意楼上！",0);
        commentList.add(comment);
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        comment = new Comment("漱芳华",":一切都在改变", R.mipmap.goodcomment);
        commentList.add(comment);
        comment = new Comment("周用",":特别好666！",0);
        commentList.add(comment);
        comment = new Comment("丫丫",":我喜欢你的图片！",0);
        commentList.add(comment);
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);
        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        comment = new Comment("丫丫",":我喜欢你的图片！", R.mipmap.goodcomment);
        commentList.add(comment);
        comment = new Comment("海天相接",":同意楼上！",0);
        commentList.add(comment);
        dynamicCommentList.add(commentList);

        return dynamicCommentList;
    }

    /**
     * 心情动态、动态数据初始化*/
    public List<Dynamic> initDynamic(){
        //dynamicList = new ArrayList<Dynamic>();
        dynamicCommentList = initCommentData();
        int[] fivenums1 = {20,1,2,4,3};
        int[] fivenums2 = {0,9,2,3,1};
        int[] fivenums3 = {5,10,2,0,5};
        int[] fivenums4 = {2,1,2,0,0};
        int[] fivenums5 = {6,0,2,0,3};
        int[] fivenums6 = {9,1,0,2,0};
        Dynamic dynamic = new Dynamic("每天很早来学校，表面是爱学习，可有几个人知道，我是来抄作业的。","无法无天","2分钟前", R.drawable.blurima, R.drawable.whilesquare,dynamicCommentList.get(0),fivenums1,"0","");
        dynamicList.add(dynamic);
        dynamic = new Dynamic("When you choose to become others, you will lose yourself. 当你选择成为别人时，你将失去你自己。","破小流苏","20分钟前", R.drawable.blurima2,0,dynamicCommentList.get(1),fivenums2,"0","");
        dynamicList.add(dynamic);

        dynamic = new Dynamic("小李师傅拍的，下午还要赶一场活动，好想有个男朋友。","急疯的兔子会咬人","1小时前", R.drawable.blurima3, R.drawable.blurima,dynamicCommentList.get(2),fivenums3,"0","");
        dynamicList.add(dynamic);

        dynamic = new Dynamic("时间才是永恒的爱人，它会赶走爱我不深的人。","如烟","1天前", R.drawable.blurima2,0,dynamicCommentList.get(3),fivenums4,"0","");
        dynamicList.add(dynamic);
        dynamic = new Dynamic("无聊自嗨","无法无天","1天前", R.drawable.blurima3,0,dynamicCommentList.get(4),fivenums5,"0","");
        dynamicList.add(dynamic);

        dynamic = new Dynamic("今天晚上酒吧嗨起来，如果你也是一个人的话可以，恩","张又又","10-15", R.drawable.blurima4, R.drawable.blurima3,dynamicCommentList.get(5),fivenums6,"0","");
        dynamicList.add(dynamic);

        return dynamicList;
    }

    /**
     * 广场图文评论*/
    public List<List<Comment>> initSquareCommentData(){
        dynamicCommentList = new ArrayList<>();

        commentList = new ArrayList<Comment>();
        Comment comment = new Comment("漱芳华",":一切都在改变", R.mipmap.goodcomment);
        commentList.add(comment);
        comment = new Comment("周用",":特别好666！",0);
        commentList.add(comment);
        comment = new Comment("丫丫",":我喜欢你的图片！",0);
        commentList.add(comment);
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);

        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);
        commentList = new ArrayList<Comment>();
        dynamicCommentList.add(commentList);
        return dynamicCommentList;
    }
    /**
     * 广场图文动态数据初始化*/
    public List<Dynamic> initSquareDynamic(){
        dynamicList = new ArrayList<Dynamic>();
        dynamicCommentList = initSquareCommentData();

        int[] fivenums1 = {20,1,2,4,3};
        int[] fivenums2 = {0,9,2,3,1};
        int[] fivenums3 = {5,10,2,0,5};
        int[] fivenums4 = {2,1,2,0,0};

        Dynamic dynamic = new Dynamic("活在当下","无法无天","2分钟前", R.drawable.blurima, 0,dynamicCommentList.get(0),fivenums1,"0","");
        dynamicList.add(dynamic);
        dynamic = new Dynamic("When you choose to become others, you will lose yourself. 当你选择成为别人时，你将失去你自己。","破小流苏","20分钟前", R.drawable.blurima2,R.drawable.whilesquare,dynamicCommentList.get(1),fivenums2,"0","");
        dynamicList.add(dynamic);

        dynamic = new Dynamic("小李师傅拍的，下午还要赶一场活动，好想有个男朋友。","急疯的兔子会咬人","1小时前", R.drawable.blurima3, R.drawable.blurima,dynamicCommentList.get(2),fivenums3,"0","");
        dynamicList.add(dynamic);

        dynamic = new Dynamic("时间才是永恒的爱人，它会赶走爱我不深的人。","如烟","1天前", R.drawable.blurima2,0,dynamicCommentList.get(3),fivenums4,"0","");
        dynamicList.add(dynamic);
        return dynamicList;
    }



    /**
     * 广场声音数据初始化*/
    public List<Voice> initVoice(){
        voiceList = new ArrayList<Voice>();
        voiceCommentList = initVoiceCommentData();
        int[] fivenums1 = {20,1,2,4,0};
        int[] fivenums2 = {0,9,2,3,0};
        int[] fivenums3 = {5,10,2,0,1};
        int[] fivenums4 = {2,1,2,0,0};

        Voice voice = new Voice("54”","可试听50%","李又又","2分钟前", R.drawable.blurima3, R.drawable.whilesquare,voiceCommentList.get(0),fivenums1,1.0f);
        voiceList.add(voice);
        voice = new Voice("50”","可试听40%","joker","20分钟前", R.drawable.blurima4,0,voiceCommentList.get(1),fivenums2,0.0f);
        voiceList.add(voice);

        voice = new Voice("20”","可试听60%","Marry","1小时前", R.drawable.blurima3, R.drawable.blurima,voiceCommentList.get(2),fivenums3,2.0f);
        voiceList.add(voice);

        voice = new Voice("45”","可试听100%","如烟","1天前", R.drawable.blurima2,0,voiceCommentList.get(3),fivenums4,5.0f);
        voiceList.add(voice);
        return voiceList;
    }
    /**
     * 广场语音评论数据*/
    public List<List<Comment>> initVoiceCommentData(){
        voiceCommentList = new ArrayList<>();

        voicommentList = new ArrayList<Comment>();
        Comment comment = new Comment("小朱",":陈东阳逗比一般的存在");
        voicommentList.add(comment);
        comment = new Comment("小周",":特别好！");
        voicommentList.add(comment);
        comment = new Comment("丫丫",":我喜欢你的声音！");
        voicommentList.add(comment);
        comment = new Comment("Joek",":声音真好听！");
        voicommentList.add(comment);
        voiceCommentList.add(voicommentList);

        voicommentList = new ArrayList<Comment>();
        comment = new Comment("小兰",":一切都在改变");
        voicommentList.add(comment);
        comment = new Comment("周用",":特别好666！");
        voicommentList.add(comment);
        comment = new Comment("丫丫",":我喜欢你的图片！");
        voicommentList.add(comment);
        voiceCommentList.add(voicommentList);

        voicommentList = new ArrayList<Comment>();
        voiceCommentList.add(voicommentList);

        voicommentList = new ArrayList<Comment>();
        comment = new Comment("丫丫",":我喜欢你的声音！");
        voicommentList.add(comment);
        comment = new Comment("海天相接",":同意楼上！");
        voicommentList.add(comment);
        voiceCommentList.add(voicommentList);
        return voiceCommentList;
    }
}
