package com.TT.SincereAgree.amei.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.adapter.CommentAdapter;
import com.TT.SincereAgree.amei.adapter.ImageSlipAdapter;
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
import static com.TT.SincereAgree.util.TimeAndTransf.setListViewHeight;

public class SquareDetailDyActivity extends AppCompatActivity {
    /**
     * ViewPager关于动态图片左右滑动
     */
    private ViewPager viewPager;
    private ArrayList<View> imaviewList;
    private ImageView imageView;
    /**
     * 图片资源url
     */
    private String[] imgUrlArray = new String[9];

    private TextView dynamicUserName;//昵称
    private ImageView dyUserHeadima;//头像
    private TextView dyTime;//距离发动态时间

    private TextView dycontent;
    private ImageView dyimaView;

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

    private ListView commentList;
    private CommentAdapter commentAdapter;
    private List<Comment> commList = new ArrayList<>();

    /**评论的内容*/
    private String commentContent;
    /**发送评论的账号ID*/
    private int state;//发布成功与否的状态码
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.square_detail_dyitem);
        app = (App) this.getApplication();
        findview();
        init();
        click();
    }
    public void findview(){
        dynamicUserName = (TextView)findViewById(R.id.userName);
        dyUserHeadima = (ImageView)findViewById(R.id.iv_leftlogo);
        dyTime = (TextView)findViewById(R.id.tvAge);

        dycontent = (TextView)findViewById(R.id.showInfo);
        /**动态图片显示的地方*/
        viewPager = (ViewPager) findViewById(R.id.showimage);

        likenum = (TextView)findViewById(R.id.dianzan_nums);
        dislikenum = (TextView)findViewById(R.id.tramplenum);
        giftnum = (TextView)findViewById(R.id.giftnums);
        commentnum = (TextView)findViewById(R.id.pinglun_nums);
        sharenum = (TextView)findViewById(R.id.fenxiang_nums);

        likenumLayout = (LinearLayout)findViewById(dianzan);
        dislikenumLayout = (LinearLayout)findViewById(R.id.trample);
        giftnumLayout = (LinearLayout)findViewById(R.id.giftnum);
        commentnumLayout = (LinearLayout)findViewById(R.id.reviewnum);
        sharenumLayout = (LinearLayout)findViewById(R.id.reshipment);

        /**编辑并发送评论*/
        commentLayout = (PercentRelativeLayout)findViewById(R.id.dylayout_pinglun_fenxiang2);
        commentLayout.setVisibility(View.GONE);
        sendCommentBtn = (Button) findViewById(R.id.detail_button_send_comment3);
        commentEdit = (EditText) findViewById(R.id.detail_etPingLunContent3);

        commentList = (ListView)findViewById(R.id.comment);
    }
    public void init(){
        Intent intent = getIntent();
        final Dynamic dynamic = (Dynamic)intent.getSerializableExtra("dynamic");

        String username = dynamic.getDynamicUserName();
        String dytime = dynamic.getDyTime();
        String userheadima = dynamic.getDyUserHeadimag();

        String contentDy = dynamic.getText();
        final int[] fivenums = dynamic.getFivenums();
        /**展示动态的图片*/
        imgUrlArray = dynamic.getDyUserImags();
        imaviewList = new ArrayList<View>();//每张图片显示的容器view
        for (int i=0; i<imgUrlArray.length; i++){
            View view = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.imagelayout, null);
            imageView = (ImageView) view.findViewById(R.id.view_ViewPager);
            Picasso.with(this).load(imgUrlArray[i]).transform(TimeAndTransf.getTransformation(imageView)).into(imageView);
            imaviewList.add(view);
        }

        if (imgUrlArray.length > 0&& !imgUrlArray[0].substring(imgUrlArray[0].length()-4,imgUrlArray[0].length()).equals("null")) {
            viewPager.setVisibility(View.VISIBLE);
            //设置Adapter
            viewPager.setAdapter(new ImageSlipAdapter(this,imaviewList));
            viewPager.setCurrentItem(0);
        }else
            viewPager.setVisibility(View.GONE);

        dynamicUserName.setText(username);
        Picasso.with(this).load(userheadima).transform(TimeAndTransf.getTransformation(dyUserHeadima)).into(dyUserHeadima);
        dyTime.setText(dytime);

        dycontent.setText(contentDy);

        likenum.setText(String.valueOf(fivenums[0]));
        dislikenum.setText(String.valueOf(fivenums[1]));
        giftnum.setText(String.valueOf(fivenums[2]));
        commentnum.setText(String.valueOf(fivenums[3]));
        sharenum.setText(String.valueOf(fivenums[4]));

        commList = dynamic.getComment();

        commentAdapter = new CommentAdapter(this,commList);
        commentList.setAdapter(commentAdapter);
        setListViewHeight(commentList);


        /**点赞*/
        likenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likenum.getText().toString() == String.valueOf(fivenums[0]))
                    likenum.setText(String.valueOf(fivenums[0]+1));
                else
                    likenum.setText(String.valueOf(fivenums[0]));
                final HashMap<String,String> map = new HashMap<String, String>();
                if (dynamic.getdId()!=null){
                    map.put("dId",dynamic.getdId());
                    map.put("support",likenum.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (PostUpdateData.postUpdate(map,rootUrl+"/dynamic/updateDynamic") == 0)
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
                final HashMap<String,String> map2 = new HashMap<String, String>();
                if (dynamic.getdId()!=null){
                    map2.put("dId",dynamic.getdId());
                    map2.put("unlike",dislikenum.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (PostUpdateData.postUpdate(map2,rootUrl+"/dynamic/updateDynamic") == 0)
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
                    mapnum.put("dId",dynamic.getdId());
                    mapnum.put("comment",String.valueOf(Integer.valueOf(commentnum.getText().toString())+1));
                    map.put("account",dynamic.getAccountId());
                    map.put("commentAccount",app.getUserID());
                    map.put("dynaimcId",dynamic.getdId());
                    map.put("content",commentContent);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           /**插入此条评论到服务器*/
                            state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postComment");
                            handler.sendEmptyMessage(0);
                            PostUpdateData.postUpdate(mapnum,rootUrl+"/dynamic/updateDynamic");
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
                String acceptUserID = dynamic.getAccountId();//将礼物发送给此ID的人
                //TODO

                Toast.makeText(getApplicationContext(), "发送礼物给"+acceptUserID, Toast.LENGTH_LONG).show();
            }
        });

        /**转发*/
        sharenumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SquareDetailDyActivity.this).setTitle("提示")
                        .setMessage("你确定要转发分享此动态吗？").
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                                final HashMap<String,String> map = new HashMap<String, String>();
                                final HashMap<String,String> mapnum = new HashMap<String, String>();
                                mapnum.put("dId",dynamic.getdId());
                                mapnum.put("share",String.valueOf(Integer.valueOf(sharenum.getText().toString())+1));
                                map.put("accountId",app.getUserID());
                                map.put("dContent",dynamic.getText());
                                map.put("dReleasetime",dateFormat.format(new Date()));
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
                                        //转发动态，调用发布的方法
                                        state = PostData.postDyVoCoData(map,rootUrl + "/dynamic/postDynamic");
                                        PostUpdateData.postUpdate(mapnum,rootUrl+"/dynamic/updateDynamic");
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
        dyUserHeadima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = dynamic.getAccountId();
                Intent intent = new Intent(SquareDetailDyActivity.this,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                startActivity(intent);
            }
        });

    }

    public void click(){
        /**每一个评论的点击事件*/
        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"点第"+position+"条评论是要赞吗？",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), commentContent, Toast.LENGTH_LONG).show();
            commentLayout.setVisibility(View.GONE);

            commentnum.setText(String.valueOf(Integer.valueOf(commentnum.getText().toString())+1));
            Comment commentNew = new Comment();
            commentNew.setCommentUserName(app.getUserName());
            commentNew.setText(commentContent);
            commentAdapter.addComment(commentNew);
            TimeAndTransf.setListViewHeight(commentList);
            commentEdit.setText("");
        }
    };
}
