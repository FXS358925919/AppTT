package com.TT.SincereAgree.amei.activity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.common.PostData;
import com.TT.SincereAgree.amei.common.PostFile;
import com.TT.SincereAgree.amei.view.TitleBar;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * 发布语音
 */
public class AudioRecoder extends AppCompatActivity {
    private TitleBar titleBar;
    /**上传语音的路径(图片)*/
    String netURL = rootUrl + "/uploadFile/uploadvoice";
    /**发布的时间*/
    private String releasetime;
    /**可试听百分比*/
    private String listenPersent;
    /**语音价格**/
    private Double price;
    /**语音时长*/
    private String voicetime;
    /**发布的语音的名称*/
    private String voiceName;
    /**发布的账号ID  app.getUserID()*/
    private int state;//发布成功与否的状态码
    private App app;


    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private File audiofile;
    private String voiceurl;

    private ExecutorService mExecutorService;
    private ImageButton recoderbutton;
    /**水平进度条*/
    private IndicatorSeekBar seekBar;
    private Button bofangbutton;
    private NumberPicker pricePicker;
    private long startrecordertime, stoprecordertime;
    private Handler mainthreadHandler;
    private volatile boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recoder);
        //录音函数不具备线程安全性，所以用单线程
        mExecutorService = Executors.newSingleThreadExecutor();
        mainthreadHandler = new Handler(Looper.getMainLooper());
        app = (App) this.getApplicationContext();
        findview();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.showLeftImageAndRightStr("发布", "发布", "取消");

        titleBar.clickright(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                releasetime = dateFormat.format(new Date());
                System.out.println("----------------shijian----"+voicetime);
                final HashMap<String,String> map = new HashMap<String, String>();
                map.put("accountId",app.getUserID());
                map.put("vPeroflisen",listenPersent);
                map.put("voicePubtime",releasetime);
                map.put("vTime",voicetime);//语音时长

                if (voicetime !=null && !voicetime.isEmpty()&& audiofile!=null && voiceurl!=null) {
                    /**
                     * 此处将本地音频上传至服务器中*/
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            voiceName = PostFile.post_file(netURL,null,new File(voiceurl),2);
                            Log.e("voiceName1",voiceName);
                            if (voiceName!=null && !voiceName.isEmpty()){
                                voiceName = "/00/00/00/"+voiceName.substring(2,voiceName.length()-2);
                                map.put("voiceUrl",voiceName);
                                state = PostData.postDyVoCoData(map,rootUrl + "/voice/postVoicedy");
                            }
                            if (state == 1)
                                handler.sendEmptyMessage(0);
                        }
                    }).start();
                    Toast.makeText(AudioRecoder.this, "正在上传音频", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AudioRecoder.this, "请录制有效语音!", Toast.LENGTH_LONG).show();
                }
            }
        });
        titleBar.clickleft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void findview() {
        recoderbutton = (ImageButton) findViewById(R.id.audio_recoder_audiobutton);
        bofangbutton = (Button) findViewById(R.id.audio_recoder_bofang);
        seekBar = (IndicatorSeekBar) findViewById(R.id.record_audio_seekbar);
        pricePicker = (NumberPicker) findViewById(R.id.record_numberpicker_price);
    }

    public void init() {
        // 设置NumberPicker属性
        pricePicker.setMinValue(0);
        pricePicker.setMaxValue(1000);
        pricePicker.setValue(0);
        seekBar.setIndicatorTextFormat("${PROGRESS} %");
        /**调整可试听比例*/
        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                listenPersent = String.valueOf(seekParams.progress);
            }
            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) { }
        });
        // 监听数值改变事件
        pricePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                price = Double.valueOf(newVal);
            }
        });

        /**按下说话，松开发出*/
        recoderbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            startRecorder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        stopRecorder();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        bofangbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audiofile!=null&&!isPlaying)
                {
                    //设置为播放状态
                    isPlaying = true;
                    mExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            doplay(audiofile);
                        }
                    });
                }else if(audiofile == null)
                    Toast.makeText(AudioRecoder.this, "请录制语音!", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 开始录音
     */
    private void startRecorder() throws Exception {

        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                releaseRecorder();
                if (!dostartRecorder()) {
                    recorderfail();
                }
            }
        });
    }

    /**
     * 停止录音
     */
    private void stopRecorder() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (!dostopRecorder()) {
                    recorderfail();
                }
                releaseRecorder();
            }
        });
    }


    private boolean dostartRecorder() {
        try {
            mediaRecorder = new MediaRecorder();
            // 根目录
            File sdcard = Environment.getExternalStorageDirectory();
            audiofile = new File(sdcard.getAbsolutePath() + "/TT/" + System.currentTimeMillis() + ".m4a");
            audiofile.getParentFile().mkdirs();
            audiofile.createNewFile();
            voiceurl = audiofile.getAbsolutePath();
            Log.e("------音频录制路径----", voiceurl);

            //麦克风的采集
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //采样频率
            mediaRecorder.setAudioSamplingRate(44100);
            // 编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //音质比较好的频率
            mediaRecorder.setAudioEncodingBitRate(96000);
            // 设置录制音频的输出存放文件
            mediaRecorder.setOutputFile(audiofile.getAbsolutePath());
            // 预备！
            mediaRecorder.prepare();
            // 开始录音！
            mediaRecorder.start();
            //开始录音时间
            startrecordertime = System.currentTimeMillis();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean dostopRecorder() {
        try {
            // 停止录音
            mediaRecorder.stop();
            stoprecordertime = System.currentTimeMillis();
            final int second = (int) ((stoprecordertime - startrecordertime) / 1000);
            voicetime = String.valueOf(second/60+1);

            mainthreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AudioRecoder.this, "录音成功" + second + "秒", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void recorderfail() {
        audiofile = null;
        mainthreadHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AudioRecoder.this, "录音失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    /**
     *开始播放声音音频
     */
    private void doplay(File audiofile) {
        mediaPlayer = new MediaPlayer();
        try {
            // 设置播放器的声音源
            mediaPlayer.setDataSource(audiofile.getAbsolutePath());
            Log.e("------播放音频路径--", audiofile.getAbsolutePath());
            //设置监听回调
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlay();
                }
            });
            mediaPlayer.setVolume(1,1);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException | RuntimeException e){
            e.printStackTrace();
            playfail();
            stopPlay();
        }
    }

    // 停止播放
    private void stopPlay() {
        // 重置播放状态
        isPlaying=false;
        if (mediaPlayer!=null){
            //重置监听器，防止内存泄露
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            // 释放资源
            mediaPlayer.release();
            mediaPlayer = null;
       }
    }
    private void playfail() {
        mainthreadHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AudioRecoder.this, "播放失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 注意在onDestory中销毁、回收资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExecutorService!=null)
            mExecutorService.shutdownNow();
        if (isPlaying) {
            mediaPlayer.stop();
            // 释放资源
            mediaPlayer.release();
        }
        finish();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(AudioRecoder.this, "发布语音成功", Toast.LENGTH_LONG).show();
            finish();
        }

    };
}
