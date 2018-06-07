package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.amei.entity.Voice;
import com.TT.SincereAgree.amei.util.VoiceUtil;
import com.TT.SincereAgree.util.TimeAndTransf;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Amei on 2018/1/10.
 * 后期四个相间显示如abcd aaa bbb ccc ddd aaa bbb ccc ddd aaa...
 * 因此a的下标：position/4 + position%4
 * b的下标：(position+1)/4-1 + (position+1)%4
 * c的下标：(position+2)/4-2 + (position+2)%4
 * d的下标：(position+3)/4-3 + (position+3)%4
 */

public class VipSquareAdapter extends BaseAdapter{
    /**图文*/
    public static final int TYPE_IMATEST = 0;
    /**语音*/
    public static final int TYPE_VOICE = 1;
    /**视频*/
    public static final int TYPE_VIDO = 2;
    /**有偿约会*/
    public static final int TYPE_PAIDDATE = 3;

    private LayoutInflater layoutInflater;
    private Context context;

    private List<Dynamic> dynamicList;
    private List<Voice> voiceList;

    private List<List<Comment>> commentDyList;//所有动态的评论
    private List<List<Comment>> commentVoList;//所有语音的评论

    private static MediaPlayer mediaPlayer;
    private static Handler mainthreadHandler;
    private static volatile boolean isPlaying;
    private static ExecutorService mExecutorService;
    int dysize = 0;
    int vosize = 0;

    //会弃用
    public VipSquareAdapter(Context context, List<Dynamic> dynamicList,List<Voice> voiceList,
                            List<List<Comment>> commentDyList,
                            List<List<Comment>> commentVoList){
        super();
        this.context = context;
        this.dynamicList = dynamicList;
        this.voiceList = voiceList;
        layoutInflater = LayoutInflater.from(context);
        this.commentDyList = commentDyList;
        this.commentVoList = commentVoList;
    }

    /**
     * 构造函数
     * @param context
     * @param dynamicList
     * @param voiceList
     */
    public VipSquareAdapter(Context context, List<Dynamic> dynamicList,List<Voice> voiceList){
        super();
        this.context = context;
        this.dynamicList = dynamicList;
        this.voiceList = voiceList;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 4种类型的出现规则制定
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int p = position;
        int dy = dynamicList.size();
        int vo = voiceList.size();
        if (p % 2 == 0 && p/2<dy) return TYPE_IMATEST;
        else if (p % 2 == 1 && p/2<vo) return TYPE_VOICE;
        else {
            if(p/2<dy)
                return TYPE_IMATEST;
            else
                return TYPE_VOICE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getCount() {
        return dynamicList.size()+voiceList.size();
    }

    @Override
    public Object getItem(int position) {
        int type = getItemViewType(position);
        if(type == TYPE_IMATEST)
            return dynamicList.get(position);
        else
            return voiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewImatestHolder viewImatestHolder = null;
        ViewVoiceHolder viewVoiceHolder = null;

        dysize = dynamicList.size();
        vosize = voiceList.size();

        mExecutorService = Executors.newSingleThreadExecutor();
        mainthreadHandler = new Handler(Looper.getMainLooper());

        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case TYPE_IMATEST:
                    convertView = layoutInflater.inflate(R.layout.dynamiclayout,parent,false);
                    viewImatestHolder = new ViewImatestHolder();
                    viewImatestHolder.content = (TextView)convertView.findViewById(R.id.showInfo);
                    viewImatestHolder.dynamicUserName = (TextView)convertView.findViewById(R.id.userName);
                    viewImatestHolder.dyTime = (TextView)convertView.findViewById(R.id.tvAge);
                    viewImatestHolder.dyUserHeadima = (ImageView)convertView.findViewById(R.id.iv_leftlogo);

                    viewImatestHolder.dyUserima = (ImageView) convertView.findViewById(R.id.showimage);

                    viewImatestHolder.likenum = (TextView)convertView.findViewById(R.id.dianzan_nums);
                    viewImatestHolder.dislikenum = (TextView)convertView.findViewById(R.id.tramplenum);
                    viewImatestHolder.giftnum = (TextView)convertView.findViewById(R.id.giftnums);
                    viewImatestHolder.commentnum = (TextView)convertView.findViewById(R.id.pinglun_nums);
                    viewImatestHolder.sharenum = (TextView)convertView.findViewById(R.id.fenxiang_nums);

                    viewImatestHolder.hotcomima = (ImageView)convertView.findViewById(R.id.hot_comment_item_isgoodcomment);
                    viewImatestHolder.hotcomname = (TextView)convertView.findViewById(R.id.hot_comment_item_comname);
                    viewImatestHolder.hotcomcontent = (TextView)convertView.findViewById(R.id.hot_comment_item_comcontent);

                    viewImatestHolder.hotcommentlayout = (LinearLayout)convertView.findViewById(R.id.hot_comment);

                    convertView.setTag(viewImatestHolder);
                    break;
                case TYPE_VOICE:
                    convertView = layoutInflater.inflate(R.layout.squarevoice_layout,parent,false);
                    viewVoiceHolder = new ViewVoiceHolder();
                    viewVoiceHolder.voicetime = (TextView)convertView.findViewById(R.id.voicetime);
                    viewVoiceHolder.auditableper = (TextView)convertView.findViewById(R.id.auditableper);
                    viewVoiceHolder.dynamicUserName = (TextView)convertView.findViewById(R.id.voiuserName);
                    viewVoiceHolder.dyTime = (TextView)convertView.findViewById(R.id.voitvAge);
                    viewVoiceHolder.dyUserHeadima = (ImageView)convertView.findViewById(R.id.iv_leftlogo);

                    viewVoiceHolder.likenum = (TextView)convertView.findViewById(R.id.voidianzan_nums);
                    viewVoiceHolder.dislikenum = (TextView)convertView.findViewById(R.id.voitramplenum);
                    viewVoiceHolder.giftnum = (TextView)convertView.findViewById(R.id.voigiftnums);
                    viewVoiceHolder.commentnum = (TextView)convertView.findViewById(R.id.voipinglun_nums);
                    viewVoiceHolder.sharenum = (TextView)convertView.findViewById(R.id.voifenxiang_nums);

                    viewVoiceHolder.vohotcommentlayout = (LinearLayout)convertView.findViewById(R.id.voice_hcomment);
                    viewVoiceHolder.vohotcomcontent = (TextView)convertView.findViewById(R.id.voice_hcomment_item_comcontent);
                    viewVoiceHolder.vohotcomima = (ImageView)convertView.findViewById(R.id.voice_hcomment_item_isgoodcomment);
                    viewVoiceHolder.vohotcomname = (TextView)convertView.findViewById(R.id.voice_hcomment_item_comname);

                    viewVoiceHolder.voiceLayout = (LinearLayout)convertView.findViewById(R.id.layout_voiceInfo);

                    viewVoiceHolder.ratingBar = (RatingBar)convertView.findViewById(R.id.voiratingbar);
                    viewVoiceHolder.ratingLayout = (RelativeLayout)convertView.findViewById(R.id.ratewhole);

                    convertView.setTag(viewVoiceHolder);
                    break;
            }
        }else {
            switch (type){
                case TYPE_IMATEST:
                    viewImatestHolder = (ViewImatestHolder) convertView.getTag();
                    break;
                case TYPE_VOICE:
                    viewVoiceHolder = (ViewVoiceHolder) convertView.getTag();
                    break;
            }
        }
        /**设置资源*/
        switch (type) {
            case TYPE_IMATEST:
                Dynamic dynamic = null;
                /**
                 * 根据位置处理坐标，拿到对应的动态标号*/
                if (position / 2 < dysize && position / 2 < vosize)
                    dynamic = dynamicList.get(position / 2);
                else {
                    if ((position - vosize) < dynamicList.size())
                        dynamic = dynamicList.get(position - vosize);
                }

                if (dynamic != null) {
                    viewImatestHolder.content.setText(dynamic.getText());
                    viewImatestHolder.dynamicUserName.setText(dynamic.getDynamicUserName());
                    viewImatestHolder.dyTime.setText(dynamic.getDyTime());
                    /**
                     * 毕加索图片加载*/
                    if (dynamic.getDyUserImags().length>0 && (!dynamic.getDyUserImags()[0].isEmpty()))
                    {
                        Picasso.with(context).load(dynamic.getDyUserImags()[0]).transform(TimeAndTransf.getTransformation(viewImatestHolder.dyUserima)).into(viewImatestHolder.dyUserima);
                    }

                    String dyUserHeadImaurl = dynamic.getDyUserHeadimag();
                    if (dyUserHeadImaurl != null && !dyUserHeadImaurl.equals("null") && !dyUserHeadImaurl.isEmpty())
                        Picasso.with(context).load(dyUserHeadImaurl).transform(TimeAndTransf.getTransformation(viewImatestHolder.dyUserHeadima)).into(viewImatestHolder.dyUserHeadima);

                    /**如果数量为零就不显示*/
                    if (dynamic.getFivenums(0) == 0) {
                        viewImatestHolder.likenum.setText(null);
                    } else
                        viewImatestHolder.likenum.setText(String.valueOf(dynamic.getFivenums(0)));
                    if (dynamic.getFivenums(1) == 0) {
                        viewImatestHolder.dislikenum.setText(null);
                    } else
                        viewImatestHolder.dislikenum.setText(String.valueOf(dynamic.getFivenums(1)));
                    if (dynamic.getFivenums(2) == 0) {
                        viewImatestHolder.giftnum.setText(null);
                    } else
                        viewImatestHolder.giftnum.setText(String.valueOf(dynamic.getFivenums(2)));
                    if (dynamic.getFivenums(3) == 0) {
                        viewImatestHolder.commentnum.setText(null);
                    } else
                        viewImatestHolder.commentnum.setText(String.valueOf(dynamic.getFivenums(3)));
                    if (dynamic.getFivenums(4) == 0) {
                        viewImatestHolder.sharenum.setText(null);
                    } else
                        viewImatestHolder.sharenum.setText(String.valueOf(dynamic.getFivenums(4)));
                }
                /**hot评论的显示*/
                if (dynamic != null && dynamic.getComment().size() > 0) {
                    Comment comment = dynamic.getComment().get(0);
                    viewImatestHolder.hotcommentlayout.setVisibility(View.GONE);
                    if (comment.getCommentUserName() != null && comment.getText() != null) {
                        viewImatestHolder.hotcommentlayout.setVisibility(View.VISIBLE);
                        viewImatestHolder.hotcomima.setVisibility(View.VISIBLE);
                        viewImatestHolder.hotcomname.setText(comment.getCommentUserName() + ": ");
                        viewImatestHolder.hotcomcontent.setText(comment.getText());
                    }
                }
                /**用户头像的点击事件，点击用户头像跳转到用户主页面*/
                viewImatestHolder.dyUserHeadima.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context,"点我"+position+"的头像干嘛",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case TYPE_VOICE:
                Voice voice = null;
                /**
                 * 根据位置处理坐标，拿到对应的动态标号*/
                if (voiceList.size()>0) {
                    if (position / 2 < dysize && position / 2 < vosize)
                        voice = voiceList.get(position / 2);
                    else
                        voice = voiceList.get(position - dysize);
                }

                if (voice!=null){
                    viewVoiceHolder.voicetime.setText(String.valueOf(voice.getVoitime()).concat("\""));
                    viewVoiceHolder.auditableper.setText(String.valueOf("可试听" + voice.getAudper()).concat("%"));
                    viewVoiceHolder.dynamicUserName.setText(voice.getDynamicUserName());
                    viewVoiceHolder.dyTime.setText(voice.getDyTime());

                    String dyurl = voice.getVoUserHeadimag();
                    if (dyurl != null && !dyurl.equals("null") && !dyurl.isEmpty())
                        Picasso.with(context).load(dyurl).transform(TimeAndTransf.getTransformation(viewVoiceHolder.dyUserHeadima)).into(viewVoiceHolder.dyUserHeadima);

                    viewVoiceHolder.ratingBar.setRating((float) voice.getRaratingStart());

                    /**如果数量为零就不显示*/
                    if (voice.getFivenums(0) == 0) {
                        viewVoiceHolder.likenum.setText(null);
                    } else
                        viewVoiceHolder.likenum.setText(String.valueOf(voice.getFivenums(0)));
                    if (voice.getFivenums(1) == 0) {
                        viewVoiceHolder.dislikenum.setText(null);
                    } else
                        viewVoiceHolder.dislikenum.setText(String.valueOf(voice.getFivenums(1)));
                    if (voice.getFivenums(2) == 0) {
                        viewVoiceHolder.giftnum.setText(null);
                    } else
                        viewVoiceHolder.giftnum.setText(String.valueOf(voice.getFivenums(2)));
                    if (voice.getFivenums(3) == 0) {
                        viewVoiceHolder.commentnum.setText(null);
                    } else
                        viewVoiceHolder.commentnum.setText(String.valueOf(voice.getFivenums(3)));
                    if (voice.getFivenums(4) == 0) {
                        viewVoiceHolder.sharenum.setText(null);
                    } else
                        viewVoiceHolder.sharenum.setText(String.valueOf(voice.getFivenums(4)));

                    //hot评论的显示
                    if (voice != null && voice.getComment().size() > 0) {
                        Comment comment = voice.getComment().get(0);
                        viewVoiceHolder.vohotcommentlayout.setVisibility(View.GONE);
                        if (comment.getCommentUserName() != null && comment.getText() != null) {
                            viewVoiceHolder.vohotcommentlayout.setVisibility(View.VISIBLE);
                            viewVoiceHolder.vohotcomima.setVisibility(View.VISIBLE);
                            viewVoiceHolder.vohotcomname.setText(comment.getCommentUserName() + ": ");
                            viewVoiceHolder.vohotcomcontent.setText(comment.getText());
                        }
                    }
                    /**
                     * 声音显示布局的点击事件
                     * */
                    viewVoiceHolder.voiceLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = voiceList.get(position/2).getVoiceUrl();
                            String namenummber = url.substring(url.length()-14,url.length()-4);
                            String filename = voiceList.get(position/2).getAccountId()+namenummber;
                            Toast.makeText(context, "试听声音!"+position/2+"+++"+namenummber, Toast.LENGTH_LONG).show();
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
                    /**用户头像的点击事件，点击用户头像跳转到用户主页面*/
                    viewVoiceHolder.dyUserHeadima.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(context,"点我"+position+"的头像干嘛1",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
        return convertView;
    }

    private class ViewImatestHolder {
        TextView content;//内容
        TextView dynamicUserName;//昵称
        ImageView dyUserHeadima;//头像
        ImageView dyUserima;//动态中的图片
        //ViewPager viewPager;

        TextView dyTime;//距离发动态时间

        TextView likenum;
        TextView dislikenum;
        TextView giftnum;
        TextView commentnum;
        TextView sharenum;

        LinearLayout hotcommentlayout;
        ImageView hotcomima;//hot评论的标志图片
        TextView hotcomname;//评论人的昵称
        TextView hotcomcontent;//hot评论的内容
    }

    private class ViewVoiceHolder {
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

        LinearLayout vohotcommentlayout;
        ImageView vohotcomima;//hot评论的标志图片
        TextView vohotcomname;//评论人的昵称
        TextView vohotcomcontent;//hot评论的内容

        LinearLayout voiceLayout;
        RelativeLayout ratingLayout;
        RatingBar ratingBar;
    }

}
