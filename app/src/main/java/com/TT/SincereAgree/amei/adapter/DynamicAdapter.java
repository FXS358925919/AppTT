package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.activity.UsermainpageActivity;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.common.PostUpdateData;
import com.TT.SincereAgree.amei.entity.Comment;
import com.TT.SincereAgree.amei.entity.Dynamic;
import com.TT.SincereAgree.util.PicUrlUtil;
import com.TT.SincereAgree.util.TimeAndTransf;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.TT.SincereAgree.Configure.rootUrl;
import static com.TT.SincereAgree.R.id.dianzan;

/**
 * Created by Amei on 2017/12/4.
 * 广场中图文的适配
 */

public class DynamicAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Dynamic> dynamicList;

    private Context context;
    private List<Comment> commentDyList;

    private ViewHolder holder = null;

    /**评论的内容*/
    private String commentContent;
    private int state;
    private App app;



    public  DynamicAdapter(Context context, List<Dynamic> dynamics,List<Comment> commentDyList){
        super();
        app = (App) context.getApplicationContext();
        this.context = context;
        dynamicList = dynamics;
        layoutInflater = LayoutInflater.from(context);
        this.commentDyList = commentDyList;
    }

    public  DynamicAdapter(Context context, List<Dynamic> dynamics){
        super();
        this.context = context;
        app = (App) this.context.getApplicationContext();
        dynamicList = dynamics;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dynamicList.size();
    }

    @Override
    public Object getItem(int position) {
        return dynamicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = new ViewHolder();
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.dynamiclayout,parent,false);
            holder = new ViewHolder();

            holder.content = (TextView)convertView.findViewById(R.id.showInfo);
            holder.dynamicUserName = (TextView)convertView.findViewById(R.id.userName);
            holder.dyTime = (TextView)convertView.findViewById(R.id.tvAge);
            holder.dyUserHeadima = (ImageView)convertView.findViewById(R.id.iv_leftlogo);

            holder.dyUserima = (ImageView) convertView.findViewById(R.id.showimage);

            holder.likenum = (TextView)convertView.findViewById(R.id.dianzan_nums);
            holder.dislikenum = (TextView)convertView.findViewById(R.id.tramplenum);
            holder.giftnum = (TextView)convertView.findViewById(R.id.giftnums);
            holder.commentnum = (TextView)convertView.findViewById(R.id.pinglun_nums);
            holder.sharenum = (TextView)convertView.findViewById(R.id.fenxiang_nums);

            holder.likenumLayout = (LinearLayout)convertView.findViewById(dianzan);
            holder.dislikenumLayout = (LinearLayout)convertView.findViewById(R.id.trample);
            holder.giftnumLayout = (LinearLayout)convertView.findViewById(R.id.giftnum);
            holder.commentnumLayout = (LinearLayout)convertView.findViewById(R.id.reviewnum);
            holder.sharenumLayout = (LinearLayout)convertView.findViewById(R.id.reshipment);

            holder.hotcomima = (ImageView)convertView.findViewById(R.id.hot_comment_item_isgoodcomment);
            holder.hotcomname = (TextView)convertView.findViewById(R.id.hot_comment_item_comname);
            holder.hotcomcontent = (TextView)convertView.findViewById(R.id.hot_comment_item_comcontent);

            holder.hotcommentlayout = (LinearLayout)convertView.findViewById(R.id.hot_comment);

            /**评论相关布局*/
            /**编辑并发送评论*/
            holder.commentLayout = (PercentRelativeLayout)convertView.findViewById(R.id.layout_pinglun_fenxiang2);
            holder.commentLayout.setVisibility(View.GONE);
            holder.sendCommentBtn = (Button) convertView.findViewById(R.id.button_send_comment3);
            holder.commentEdit = (EditText) convertView.findViewById(R.id.etPingLunContent3);


            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Dynamic dynamic = dynamicList.get(position);
        ArrayList<View> imaviewList = new ArrayList<View>();
        ImageView imageView;

        holder.content.setText(dynamic.getText());
        holder.dynamicUserName.setText(dynamic.getDynamicUserName());
        holder.dyTime.setText(dynamic.getDyTime());

        /**
         * 毕加索图片加载*/
       // String dyurl = dynamic.getDyUserImags()[0];
        if (dynamic.getDyUserImags().length>0 && (!dynamic.getDyUserImags()[0].isEmpty()))
            Picasso.with(context).load(dynamic.getDyUserImags()[0]).transform(TimeAndTransf.getTransformation(holder.dyUserima)).into(holder.dyUserima);

        String dyUserHeadImaurl = dynamic.getDyUserHeadimag();
        if (dyUserHeadImaurl!=null&&!dyUserHeadImaurl.equals("null")&&!dyUserHeadImaurl.isEmpty())
            Picasso.with(context).load(dyUserHeadImaurl).transform(TimeAndTransf.getTransformation(holder.dyUserHeadima)).into(holder.dyUserHeadima);


        /**如果数量为零就不显示*/
        if (dynamic.getFivenums(0) == 0)
        {
            holder.likenum.setText(null);
        }else {
            holder.likenum.setText(String.valueOf(dynamic.getFivenums(0)));
        }

        if (dynamic.getFivenums(1) == 0)
        {
            holder.dislikenum.setText(null);
        }else
            holder.dislikenum.setText(String.valueOf(dynamic.getFivenums(1)));
        if (dynamic.getFivenums(2) == 0)
        {
            holder.giftnum.setText(null);
        }else
            holder.giftnum.setText(String.valueOf(dynamic.getFivenums(2)));
        if (dynamic.getFivenums(3) == 0)
        {
            holder.commentnum.setText(null);
        }else
            holder.commentnum.setText(String.valueOf(dynamic.getFivenums(3)));
        if (dynamic.getFivenums(4) == 0)
        {
            holder.sharenum.setText(null);
        }else
            holder.sharenum.setText(String.valueOf(dynamic.getFivenums(4)));
        //hot评论的显示
        if (dynamic.getComment().size()>0){
            Comment comment = dynamic.getComment().get(0);
            holder.hotcommentlayout.setVisibility(View.GONE);
            if (comment.getCommentUserName() != null&&comment.getText()!= null){
                holder.hotcommentlayout.setVisibility(View.VISIBLE);
                holder.hotcomima.setVisibility(View.VISIBLE);
                holder.hotcomname.setText(comment.getCommentUserName()+": ");
                holder.hotcomcontent.setText(comment.getText());
            }
        }

        /**用户头像的点击事件，点击用户头像跳转到用户主页面*/
        holder.dyUserHeadima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = dynamic.getAccountId();
                Intent intent = new Intent(context,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                context.startActivity(intent);
                System.out.println("-------用户头像的用户的id"+accountId);
            }
        });


        holder.likenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamic.getFivenums()[0] = dynamic.getFivenums(0) + 1;
                notifyDataSetChanged();

                final HashMap<String,String> map = new HashMap<String, String>();
                if (dynamic.getdId()!=null){
                    map.put("dId",dynamic.getdId());
                    map.put("support",String.valueOf(dynamic.getFivenums(0)));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (PostUpdateData.postUpdate(map,rootUrl+"/dynamic/updateDynamic") == 0)
                                holder.likenum.setText(String.valueOf(dynamic.getFivenums(0)+1));
                        }
                    }).start();
                }
            }
        });
        holder.dislikenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamic.getFivenums()[1] = dynamic.getFivenums(1) + 1;
                notifyDataSetChanged();
                final HashMap<String,String> map = new HashMap<String, String>();
                state = 0;
                if (dynamic.getdId()!=null){
                    map.put("dId",dynamic.getdId());
                    map.put("unlike",String.valueOf(dynamic.getFivenums(1)));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            state = PostUpdateData.postUpdate(map,rootUrl+"/dynamic/updateDynamic");
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
                String accountId = dynamic.getAccountId();
                //TODO

            }
        });
        //评论
        holder.commentnumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
               // imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
               // holder.commentLayout.setVisibility(View.VISIBLE);
               // notifyDataSetChanged();
                LayoutInflater inflater = LayoutInflater.from(context);
                final View commentDialogView = inflater.inflate(R.layout.commentsend_dialog,null);
                new AlertDialog.Builder(context).setView(commentDialogView).
                        setPositiveButton("发送",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText commentEdit = (EditText) commentDialogView.findViewById(R.id.comment_dialog_et);
                                String content = commentEdit.getText().toString().trim();
                                if ( content != null && ! content.equals("") ){
                                    final HashMap<String,String> map = new HashMap<String, String>();
                                    final HashMap<String,String> mapnum = new HashMap<String, String>();
                                    dynamic.getFivenums()[3] = dynamic.getFivenums(3) + 1;
                                    notifyDataSetChanged();
                                    mapnum.put("dId",dynamic.getdId());
                                    mapnum.put("comment",String.valueOf(dynamic.getFivenums()[3]));
                                    map.put("account",dynamic.getAccountId());
                                    map.put("commentAccount",app.getUserID());
                                    map.put("dynaimcId",dynamic.getdId());
                                    map.put("content",content);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            /**插入此条评论到服务器*/
                                            state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postComment");
                                            PostUpdateData.postUpdate(mapnum,rootUrl+"/dynamic/updateDynamic");
                                            // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                                            InputMethodManager im = (InputMethodManager)context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            im.hideSoftInputFromWindow(holder.commentEdit.getWindowToken(), 0);
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
                                dynamic.getFivenums()[4] = dynamic.getFivenums(4) + 1;
                                notifyDataSetChanged();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                                final HashMap<String,String> map = new HashMap<String, String>();
                                final HashMap<String,String> mapnum = new HashMap<String, String>();
                                mapnum.put("dId",dynamic.getdId());
                                mapnum.put("share",String.valueOf(dynamic.getFivenums(4)));
                                map.put("accountId",app.getUserID());
                                map.put("dContent",dynamic.getText());
                                map.put("dReleasetime",dateFormat.format(new Date()));
                                //map.put("dyima",dynamic.getDyUserImag().substring(rootUrl.length()+31));
                                StringBuilder imags = new StringBuilder();
                                String[] imagsURL = dynamic.getDyUserImags();
                                if (imagsURL.length > 0 && imagsURL[0]!=null) {
                                    for (int i = 0; i < imagsURL.length - 1; i++) {
                                        imags.append(PicUrlUtil.getPicturePath(imagsURL[i]));
                                        imags.append(',');
                                    }
                                    imags.append(PicUrlUtil.getPicturePath(imagsURL[imagsURL.length-1]));
                                    System.out.println("============="+imags.toString());
                                    map.put("dyima", imags.toString());
                                }

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //转发语音动态，调用发布的方法
                                        state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postDynamic");
                                        PostUpdateData.postUpdate(mapnum,rootUrl+"/dynamic/updateDynamic");
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
        TextView content;//内容
        TextView dynamicUserName;//昵称
        ImageView dyUserHeadima;//头像
        ImageView dyUserima;//动态中的图片

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

        LinearLayout hotcommentlayout;
        ImageView hotcomima;//hot评论的标志图片
        TextView hotcomname;//评论人的昵称
        TextView hotcomcontent;//hot评论的内容

        /**评论相关*/
        PercentRelativeLayout commentLayout;
        Button sendCommentBtn;
        EditText commentEdit;

    }

}
