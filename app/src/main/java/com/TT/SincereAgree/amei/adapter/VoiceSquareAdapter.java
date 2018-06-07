package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.activity.UsermainpageActivity;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.common.PostUpdateData;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.amei.util.VoiceUtil;
import com.TT.SincereAgree.pocket.SquareGiftActivity;
import com.TT.SincereAgree.util.TimeAndTransf;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.TT.SincereAgree.Configure.rootUrl;
import static com.TT.SincereAgree.R.id.dianzan;
import static com.TT.SincereAgree.R.id.giftnum;

/**
 * Created by Amei on 2017/12/20.
 */

public class VoiceSquareAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Voice> voiceList;

    private static Context context;
    private CommentAdapter commentAdapter;
    private List<List<Comment>> commentVoList;//所有语音的评论
    private static MediaPlayer mediaPlayer;
    private static Handler mainthreadHandler;
    private static volatile boolean isPlaying;
    private static ExecutorService mExecutorService;

    private ViewHolder holder = null;

    /**评论的内容*/
    private int state;
    private App app;


    public VoiceSquareAdapter(Context context, List<Voice> voices, List<List<Comment>> commentVoList){
        super();
        this.context = context;
        app = (App) context.getApplicationContext();
        voiceList = voices;
        layoutInflater = LayoutInflater.from(context);
        this.commentVoList = commentVoList;
    }

    public VoiceSquareAdapter(Context context, List<Voice> voices){
        super();
        this.context = context;
        app = (App) context.getApplicationContext();
        voiceList = voices;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return voiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return voiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mainthreadHandler = new Handler(Looper.getMainLooper());

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.squarevoice_layout,parent,false);
            holder = new ViewHolder();
            holder.voicetime = (TextView)convertView.findViewById(R.id.voicetime);
            holder.auditableper = (TextView)convertView.findViewById(R.id.auditableper);
            holder.dynamicUserName = (TextView)convertView.findViewById(R.id.voiuserName);
            holder.dyTime = (TextView)convertView.findViewById(R.id.voitvAge);
            holder.dyUserHeadima = (ImageView)convertView.findViewById(R.id.iv_leftlogo);

            holder.likenum = (TextView)convertView.findViewById(R.id.voidianzan_nums);
            holder.dislikenum = (TextView)convertView.findViewById(R.id.voitramplenum);
            holder.giftnum = (TextView)convertView.findViewById(R.id.voigiftnums);
            holder.commentnum = (TextView)convertView.findViewById(R.id.voipinglun_nums);
            holder.sharenum = (TextView)convertView.findViewById(R.id.voifenxiang_nums);

            holder.likenumLayout = (LinearLayout)convertView.findViewById(dianzan);
            holder.dislikenumLayout = (LinearLayout)convertView.findViewById(R.id.trample);
            holder.giftnumLayout = (LinearLayout)convertView.findViewById(giftnum);
            holder.commentnumLayout = (LinearLayout)convertView.findViewById(R.id.reviewnum);
            holder.sharenumLayout = (LinearLayout)convertView.findViewById(R.id.reshipment);

            holder.vohotcommentlayout = (LinearLayout)convertView.findViewById(R.id.voice_hcomment);
            holder.vohotcomcontent = (TextView)convertView.findViewById(R.id.voice_hcomment_item_comcontent);
            holder.vohotcomima = (ImageView)convertView.findViewById(R.id.voice_hcomment_item_isgoodcomment);
            holder.vohotcomname = (TextView)convertView.findViewById(R.id.voice_hcomment_item_comname);

            holder.voiceLayout = (LinearLayout)convertView.findViewById(R.id.layout_voiceInfo);
            /**
             * 声音显示布局的点击事件
             * */
            holder.voiceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = voiceList.get(position).getVoiceUrl();
                    String namenummber = url.substring(url.length()-14,url.length()-4);
                    String filename = voiceList.get(position).getAccountId()+namenummber;
                    Toast.makeText(context, "试听声音!", Toast.LENGTH_LONG).show();
                    /**点击布局就缓存音频到本地音频*/
                    if (isPlaying == true){
                        VoiceUtil.stopPlay();
                        isPlaying = false;
                        Toast.makeText(context, "已停止!", Toast.LENGTH_LONG).show();
                    }else {
                        VoiceUtil.downloadFile(url, filename, mExecutorService);
                        isPlaying = true;
                    }
                }
            });

            holder.ratingBar = (RatingBar)convertView.findViewById(R.id.voiratingbar);
            holder.ratingLayout = (RelativeLayout)convertView.findViewById(R.id.ratewhole);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Voice voice = voiceList.get(position);
        holder.voicetime.setText(String.valueOf(voice.getVoitime()).concat("\""));//时长
        holder.auditableper.setText(String.valueOf("可试听"+voice.getAudper()).concat("%"));//百分比
        holder.dynamicUserName.setText(voice.getDynamicUserName());//用户名
        holder.dyTime.setText(voice.getDyTime());//时间

        /**
         * 毕加索图片加载*/
        String dyurl = voice.getVoUserHeadimag();
        if (dyurl!=null&&!dyurl.equals("null")&&!dyurl.isEmpty())
            Picasso.with(context).load(dyurl).transform(TimeAndTransf.getTransformation(holder.dyUserHeadima)).into(holder.dyUserHeadima);

        holder.ratingBar.setRating((float) voice.getRaratingStart());

        //如果数量为零就不显示
        if (voice.getFivenums(0) == 0)
        {
            holder.likenum.setText(null);
        }else
            holder.likenum.setText(String.valueOf(voice.getFivenums(0)));
        if (voice.getFivenums(1) == 0)
        {
            holder.dislikenum.setText(null);
        }else
            holder.dislikenum.setText(String.valueOf(voice.getFivenums(1)));
        if (voice.getFivenums(2) == 0)
        {
            holder.giftnum.setText(null);
        }else
            holder.giftnum.setText(String.valueOf(voice.getFivenums(2)));
        if (voice.getFivenums(3) == 0)
        {
            holder.commentnum.setText(null);
        }else
            holder.commentnum.setText(String.valueOf(voice.getFivenums(3)));
        if (voice.getFivenums(4) == 0)
        {
            holder.sharenum.setText(null);
        }else
            holder.sharenum.setText(String.valueOf(voice.getFivenums(4)));

        if (voice.getComment().size()>0){
            Comment comment = voice.getComment().get(0);
            holder.vohotcommentlayout.setVisibility(View.GONE);
            if (comment.getCommentUserName() != null&&comment.getText()!= null){
                holder.vohotcommentlayout.setVisibility(View.VISIBLE);
                holder.vohotcomima.setVisibility(View.VISIBLE);
                holder.vohotcomname.setText(comment.getCommentUserName()+": ");
                holder.vohotcomcontent.setText(comment.getText());
            }
        }

        /**用户头像的点击事件，点击用户头像跳转到用户主页面*/
        holder.dyUserHeadima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = voice.getAccountId();
                Intent intent = new Intent(context,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                context.startActivity(intent);
                System.out.println("-------用户头像的用户的id"+accountId);
            }
        });

        holder.likenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voice.getFivenums()[0] = voice.getFivenums(0) + 1;
                notifyDataSetChanged();

                final HashMap<String,String> map = new HashMap<String, String>();
                if (voice.getvId()!=null){
                    map.put("vId",voice.getvId());
                    map.put("support",String.valueOf(voice.getFivenums(0)));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (PostUpdateData.postUpdate(map,rootUrl+"/voice/updateVoicedy") == 0)
                                holder.likenum.setText(String.valueOf(voice.getFivenums(0)+1));
                        }
                    }).start();
                }
            }
        });
        holder.dislikenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voice.getFivenums()[1] = voice.getFivenums(1) + 1;
                notifyDataSetChanged();
                final HashMap<String,String> map = new HashMap<String, String>();
                state = 0;
                if (voice.getvId()!=null){
                    map.put("vId",voice.getvId());
                    map.put("unlike",String.valueOf(voice.getFivenums(1)));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            state = PostUpdateData.postUpdate(map,rootUrl+"/voice/updateVoicedy");
                        }
                    });
                    thread.start();
                }
            }
        });
        //送礼物点击事件
        holder.giftnumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = voice.getAccountId();
                //TODO
                String dyId = voice.getvId();
                String giftNum =  String.valueOf(voice.getFivenums(2));
                String newGiftNum = String.valueOf(Integer.parseInt(giftNum)+1);
                Intent intentGift = new Intent(context,SquareGiftActivity.class);
                intentGift.putExtra("toid",accountId);
                intentGift.putExtra("dyId",dyId);
                intentGift.putExtra("newGiftNum",newGiftNum);
                intentGift.putExtra("type","2");
                context.startActivity(intentGift);

            }
        });

        //评论
        holder.commentnumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View commentDialogView = inflater.inflate(R.layout.commentsend_dialog,null);
                new AlertDialog.Builder(context).setView(commentDialogView).
                        setPositiveButton("发送",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText commentEdit = (EditText) commentDialogView.findViewById(R.id.comment_dialog_et);
                                String content = commentEdit.getText().toString().trim();
                                if ( content != null && ! content.equals("") ){
                                    final HashMap<String,String> map = new HashMap<String, String>();
                                    final HashMap<String,String> mapnum = new HashMap<String, String>();
                                    voice.getFivenums()[3] = voice.getFivenums(3) + 1;
                                    notifyDataSetChanged();
                                    mapnum.put("vId",voice.getvId());
                                    mapnum.put("comment",String.valueOf(voice.getFivenums()[3]));
                                    map.put("account",voice.getAccountId());
                                    map.put("commnetAccount",app.getUserID());
                                    map.put("voicedyId",voice.getvId());
                                    map.put("content",content);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            /**插入此条评论到服务器*/
                                            state = PostData.postDyVoCoData(map,rootUrl + "/voice/postVoComment");
                                            PostUpdateData.postUpdate(mapnum,rootUrl+"/voice/updateVoicedy");
                                            // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                                            InputMethodManager im = (InputMethodManager)context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            im.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                                        }
                                    }).start();
                                    notifyDataSetChanged();
                                }
                                else
                                    Toast.makeText(context, "评论内容不能为空!", Toast.LENGTH_LONG).show();
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


        //转发
        holder.sharenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("提示")
                        .setMessage("你确定要转发分享此动态吗？").
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                voice.getFivenums()[4] = voice.getFivenums(4) + 1;
                                notifyDataSetChanged();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                                final HashMap<String,String> map = new HashMap<String, String>();
                                final HashMap<String,String> mapnum = new HashMap<String, String>();
                                mapnum.put("vId",voice.getvId());
                                mapnum.put("share",String.valueOf(voice.getFivenums(4)));
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
                                        PostUpdateData.postUpdate(mapnum,rootUrl+"/voice/updateVoicedy");
                                    }
                                }).start();
                                notifyDataSetChanged();
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


        return convertView;
    }

    private class ViewHolder {
        TextView voicetime;//声音的时间长度
        TextView auditableper;//声音可试听百分比

        TextView dynamicUserName;//昵称
        ImageView dyUserHeadima;//头像

        TextView dyTime;//距离发动态时间

        TextView likenum;
        TextView dislikenum;
        TextView giftnum;
        TextView commentnum;
        TextView sharenum;

        LinearLayout likenumLayout;
        LinearLayout dislikenumLayout;
        LinearLayout giftnumLayout;
        LinearLayout commentnumLayout;
        LinearLayout sharenumLayout;

        LinearLayout vohotcommentlayout;
        ImageView vohotcomima;//hot评论的标志图片
        TextView vohotcomname;//评论人的昵称
        TextView vohotcomcontent;//hot评论的内容


        LinearLayout voiceLayout;
        RelativeLayout ratingLayout;
        RatingBar ratingBar;
    }

}
