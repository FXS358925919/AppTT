package com.TT.SincereAgree.amei.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.CommentAdapter;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.common.PostUpdateData;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.amei.util.VoiceUtil;
import com.TT.SincereAgree.pocket.SquareGiftActivity;
import com.TT.SincereAgree.util.TimeAndTransf;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.TT.SincereAgree.Configure.rootUrl;
import static com.TT.SincereAgree.R.id.dianzan;

public class SquareDetailVoiActivity extends AppCompatActivity {
    private TextView vonamicUserName;//昵称
    private ImageView voUserHeadima;//头像
    private TextView voTime;//距离发动态时间

    private TextView voicetime;//声音的时间长度
    private TextView auditableper;//声音可试听百分比

    private TextView likenum;
    private TextView dislikenum;
    private TextView giftnum;
    private TextView commentnum;
    private TextView sharenum;

    private LinearLayout likenumLayout;
    private LinearLayout dislikenumLayout;
    private LinearLayout giftnumLayout;
    private LinearLayout commentnumLayout;
    private LinearLayout sharenumLayout;

    private PercentRelativeLayout commentLayout;
    private Button sendCommentBtn;
    private EditText commentEdit;
    /**评论的内容*/
    private String commentContent;
    /**发送评论的账号ID*/
    private int state;//发布成功与否的状态码
    private App app;


    private LinearLayout voiceLayout;//语音所在的布局

    private RelativeLayout ratingLayout;
    private RatingBar ratingBar;

    private ListView voicecommentList;
    private CommentAdapter voicecommentAdapter;
    private List<Comment> voicecommListData = new ArrayList<>();

    //private boolean full = false;
    private static MediaPlayer mediaPlayer;
    private static Handler mainthreadHandler;
    private static volatile boolean isPlaying;
    private static ExecutorService mExecutorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.square_detail_voice);
        app = (App) this.getApplication();
        //录音函数不具备线程安全性，所以用单线程
        mExecutorService = Executors.newSingleThreadExecutor();
        mainthreadHandler = new Handler(Looper.getMainLooper());
        findview();
        init();
    }

    public void findview(){
        vonamicUserName = (TextView)findViewById(R.id.voiuserName);
        voUserHeadima = (ImageView)findViewById(R.id.voiv_leftlogo);
        voTime = (TextView)findViewById(R.id.voitvAge);
        voicetime = (TextView)findViewById(R.id.voicetime);
        auditableper = (TextView)findViewById(R.id.voauditableper);

        likenum = (TextView)findViewById(R.id.voidianzan_nums);
        dislikenum = (TextView)findViewById(R.id.voitramplenum);
        giftnum = (TextView)findViewById(R.id.voigiftnums);
        commentnum = (TextView)findViewById(R.id.voipinglun_nums);
        sharenum = (TextView)findViewById(R.id.voifenxiang_nums);

        likenumLayout = (LinearLayout)findViewById(dianzan);
        dislikenumLayout = (LinearLayout)findViewById(R.id.trample);
        giftnumLayout = (LinearLayout)findViewById(R.id.giftnum);
        commentnumLayout = (LinearLayout)findViewById(R.id.reviewnum);
        sharenumLayout = (LinearLayout)findViewById(R.id.reshipment);

        /**编辑并发送评论*/
        commentLayout = (PercentRelativeLayout)findViewById(R.id.vo_layout_pinglun_fenxiang2);
        commentLayout.setVisibility(View.GONE);
        sendCommentBtn = (Button) findViewById(R.id.vo_button_send_comment3);
        commentEdit = (EditText) findViewById(R.id.vo_etPingLunContent3);

        voiceLayout =(LinearLayout)findViewById(R.id.layout_voiceInfo);
        ratingBar = (RatingBar)findViewById(R.id.voiratingbar);
        ratingLayout = (RelativeLayout)findViewById(R.id.voratewhole);

        voicecommentList = (ListView)findViewById(R.id.voice_comment);
    }
    public void init(){
        Intent intent = getIntent();
        final Voice voice = (Voice) intent.getSerializableExtra("voice");

        String username = voice.getDynamicUserName();
        String dytime = voice.getDyTime();
        String userheadima = voice.getVoUserHeadimag();
        final int[] fivenums = voice.getFivenums();
        //String audiper = voice.getAuditableper();
        //voUserHeadima.setImageResource(userheadima);
        //String voicetimelong = voice.getVoicetime();//语音的时长
        //int userheadima = voice.getDyUserHeadima();//临时

        vonamicUserName.setText(username);

        Picasso.with(this).load(userheadima).transform(TimeAndTransf.getTransformation(voUserHeadima)).into(voUserHeadima);
        voTime.setText(dytime);

        int voicetimelong = voice.getVoitime();//语音的时长
        voicetime.setText(String.valueOf(voicetimelong).concat("\""));////
        int audiper = voice.getAudper();
        auditableper.setText("可试听"+String.valueOf(audiper).concat("%"));/////

        likenum.setText(String.valueOf(fivenums[0]));
        dislikenum.setText(String.valueOf(fivenums[1]));
        giftnum.setText(String.valueOf(fivenums[2]));
        commentnum.setText(String.valueOf(fivenums[3]));
        sharenum.setText(String.valueOf(fivenums[4]));
        ratingBar.setRating((float)voice.getRaratingStart());//

        voicecommListData = voice.getComment();

        /*
        打赏用户评级只是展示平均评级
        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() == 5.0||full == true){
                    ratingBar.setRating(ratingBar.getRating()-1.0f);
                    if (ratingBar.getRating() == 0)
                        full = false;
                }
                else{
                    ratingBar.setRating(ratingBar.getRating()+1.0f);
                    if (ratingBar.getRating() == 5)
                        full = true;
                }
            }
        });*/
        voiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying == false) {
                    String url = voice.getVoiceUrl();
                    String namenummber = url.substring(url.length() - 14, url.length() - 4);
                    String filename = voice.getAccountId() + namenummber;
                    Toast.makeText(getApplicationContext(), "听声音!", Toast.LENGTH_LONG).show();

                    VoiceUtil.downloadFile(url, filename, mExecutorService);
                    isPlaying = true;//声音已经在播放标志位
                }
                else {
                    isPlaying = false;//声音已经停止播放标志位
                    VoiceUtil.stopPlay();
                    Toast.makeText(getApplicationContext(), "已停止声音!", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**点赞*/
        likenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likenum.getText().toString() == String.valueOf(fivenums[0]))
                    likenum.setText(String.valueOf(fivenums[0]+1));
                else
                    likenum.setText(String.valueOf(fivenums[0]));
                final HashMap<String,String> map = new HashMap<String, String>();
                if (voice.getvId()!=null){
                    map.put("vId",voice.getvId());
                    map.put("support",likenum.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           if (PostUpdateData.postUpdate(map,rootUrl+"/voice/updateVoicedy") == 0)
                               likenum.setText(String.valueOf(fivenums[0]+1));
                        }
                    }).start();
                }
            }
        });

        /**点踩*/
        dislikenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dislikenum.getText().toString() == String.valueOf(fivenums[1]))
                    dislikenum.setText(String.valueOf(fivenums[1]+1));
                else
                    dislikenum.setText(String.valueOf(fivenums[1]));
                final HashMap<String,String> map = new HashMap<String, String>();
                if (voice.getvId()!=null){
                    map.put("vId",voice.getvId());
                    map.put("unlike",dislikenum.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (PostUpdateData.postUpdate(map,rootUrl+"/voice/updateVoicedy") == 0)
                                dislikenum.setText(String.valueOf(fivenums[1]+1));
                        }
                    }).start();
                }
            }
        });

        /**显示评论布局，发送评论*/
        commentnumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                commentLayout.setVisibility(View.VISIBLE);
            }
        });
        /**发送按钮的点击事件*/
        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentContent = commentEdit.getText().toString().trim();
                final HashMap<String,String> map = new HashMap<String, String>();
                final HashMap<String,String> mapnum = new HashMap<String, String>();
                if (!commentContent.isEmpty()){
                    mapnum.put("vId",voice.getvId());
                    mapnum.put("comment",String.valueOf(Integer.valueOf(commentnum.getText().toString())+1));
                    map.put("account",voice.getAccountId());
                    map.put("commnetAccount",app.getUserID());
                    map.put("voicedyId",voice.getvId());
                    map.put("content",commentContent);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            /**插入此条评论到服务器*/
                            state = PostData.postDyVoCoData(map,rootUrl + "/voice/postVoComment");

                            handler.sendEmptyMessage(0);
                            PostUpdateData.postUpdate(mapnum,rootUrl+"/voice/updateVoicedy");
                            // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                            InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            im.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                        }
                    }).start();

                }else
                    Toast.makeText(getApplicationContext(), "评论内容不能为空!", Toast.LENGTH_LONG).show();

            }
        });

        /**礼物*/
        giftnumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acceptUserID = voice.getAccountId();//将礼物发送给此ID的人
                //TODO
                String dyId = voice.getvId();
                String giftNum = giftnum.getText().toString();
                String newGiftNum = String.valueOf(Integer.parseInt(giftNum)+1);
                Intent intentGift = new Intent(SquareDetailVoiActivity.this,SquareGiftActivity.class);
                intentGift.putExtra("toid",acceptUserID);
                intentGift.putExtra("dyId",dyId);
                intentGift.putExtra("newGiftNum",newGiftNum);
                intentGift.putExtra("type","2");
                startActivity(intentGift);
            }
        });

        /**转发*/
        sharenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SquareDetailVoiActivity.this).setTitle("提示")
                        .setMessage("你确定要转发分享此动态吗？").
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                                final HashMap<String,String> map = new HashMap<String, String>();
                                final HashMap<String,String> mapnum = new HashMap<String, String>();
                                mapnum.put("vId",voice.getvId());
                                mapnum.put("share",String.valueOf(Integer.valueOf(sharenum.getText().toString())+1));
                                map.put("accountId",app.getUserID());
                                map.put("vPeroflisen",String.valueOf(voice.getAudper()));
                                map.put("voicePubtime",dateFormat.format(new Date()));
                                map.put("vTime",String.valueOf(voice.getVoitime()));//语音时长
                                map.put("voiceUrl",voice.getVoiceUrl().substring(rootUrl.length()+30));

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //转发语音动态，调用发布的方法
                                        state = PostData.postDyVoCoData(map,rootUrl + "/voice/postVoicedy");
                                        System.out.println("========="+state);
                                        PostUpdateData.postUpdate(mapnum,rootUrl+"/voice/updateVoicedy");
                                    }
                                }).start();

                                Toast.makeText(getApplicationContext(), "转发", Toast.LENGTH_SHORT).show();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });
        /**用户头像点击查看用户详情*/
        voUserHeadima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = voice.getAccountId();
                Intent intent = new Intent(SquareDetailVoiActivity.this,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                startActivity(intent);

            }
        });


        voicecommentAdapter = new CommentAdapter(this,voicecommListData);
        voicecommentList.setAdapter(voicecommentAdapter);
        TimeAndTransf.setListViewHeight(voicecommentList);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "评论", Toast.LENGTH_LONG).show();
            commentLayout.setVisibility(View.GONE);

            commentnum.setText(String.valueOf(Integer.valueOf(commentnum.getText().toString())+1));
            Comment commentNew = new Comment();
            commentNew.setCommentUserName(app.getUserName());
            commentNew.setText(commentContent);
            voicecommentAdapter.addComment(commentNew);
            TimeAndTransf.setListViewHeight(voicecommentList);
            commentEdit.setText("");
        }

    };

}
