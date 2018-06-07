package com.TT.SincereAgree.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.TT.SincereAgree.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener  {
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private float x1,x2,y1,y2;
    private Button start;
    private ViewPager vPager;
    private VpAdapter vpAdapter;
    private static  int[] imgs = {R.drawable.guide,R.drawable.guide2, R.drawable.guide3,R.drawable.guide4};
    private ArrayList<ImageView> imageViews;
    private ImageView[] dotViews;//小圆点
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        vPager = (ViewPager)findViewById(R.id.guide_ViewPager);
        start = (Button) findViewById(R.id.guide_enter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        initImages();
        initDots();
        vpAdapter = new VpAdapter(imageViews);
        vPager.setAdapter(vpAdapter);
        vPager.addOnPageChangeListener(this);
    }

    /**
     * 把引导页要显示的图片添加到集合中，以传递给适配器，用来显示图片。
     */
    private void initImages(){
        //设置每一张图片都填充窗口
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        imageViews = new ArrayList<ImageView>();

        for(int i=0; i<imgs.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            iv.setImageResource(imgs[i]);//为ImageView添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//这里也是一个图片的适配
            imageViews.add(iv);
            if (i == imgs.length -1 ){
                //为最后一张图片添加点击事件
                iv.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        //跳转到主界面
                        /*if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            //当手指按下的时候
                            x1 = event.getX();
                            y1 = event.getY();
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            //当手指离开的时候
                            x2 = event.getX();
                            y2 = event.getY();
                            if(y1 - y2 > 50) {
                                Toast.makeText(GuideActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                            } else if(y2 - y1 > 50) {
                                Toast.makeText(GuideActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                            } else if(x1 - x2 > 50) {
                                Toast.makeText(GuideActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                            } else if(x2 - x1 > 50) {
                                Toast.makeText(GuideActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                            }
                        }*/

                        return true;

                    }
                });
            }

        }


    }

    private void initDots(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.dot_Layout);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        mParams.setMargins(10, 0, 10,0);//设置小圆点左右之间的间隔
        dotViews = new ImageView[imgs.length];
        //判断小圆点的数量，从0开始，0表示第一个
        for(int i = 0; i < imageViews.size(); i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dotselector);
            if(i== 0)
            {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            }
            else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            layout.addView(imageView);//添加到布局里面显示
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    /**
     * arg0：当前滑动显示页面的索引值，可以根据这个值，来设置相应小圆点的状态。
     */
    @Override
    public void onPageSelected(int arg0) {
        for(int i = 0; i < dotViews.length; i++)
        {
            if(arg0 == i)
            {
                dotViews[i].setSelected(true);
            }
            else {
                dotViews[i].setSelected(false);
            }
        }

    }

}
