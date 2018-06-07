package com.TT.SincereAgree.amei.util;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;

/**
 * using in VoiceSquareAdapter、SquareDetailVoiActivity
 * Created by Amei on 2018/4/27.
 */

public class VoiceUtil {
    public static MediaPlayer mediaPlayer;
    public static Handler mainthreadHandler;
    public static volatile boolean isPlaying;

    /**
     * 下载语音文件方法
     * @param url 网络地址
     * @param name 缓存语音文件的命名
     */
    public static void downloadFile(String url, String name, final ExecutorService mExecutorService){
        String storeurl = SdCardUtil.getStoragePath() +SdCardUtil.FILEVOICECACHE+"/"+name + ".mp3";
        if (!fileIsexist(storeurl)) {
            OkHttpUtils.get().url(url).build().execute(new FileCallBack(
                    SdCardUtil.getStoragePath() +SdCardUtil.FILEVOICECACHE, name + ".mp3") {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.e("error", "onError :" + e.getMessage());
                }

                @Override
                public void onResponse(File response, int id) {
                    Log.e("response", "onResponse :" + response.getAbsolutePath());
                    final File voicefile = new File(response.getAbsolutePath());
                    if (voicefile != null && !isPlaying) {
                        //设置为播放状态
                        isPlaying = true;
                        mExecutorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                doplay(voicefile);
                            }
                        });
                    }
                }
            });
        }
        else {
            Log.e("response", "--------文件存在直接播放");
            final File voicefile = new File(storeurl);
            if (voicefile!=null&&!isPlaying)
            {
                //设置为播放状态
                isPlaying = true;
                mExecutorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        doplay(voicefile);
                    }
                });
            }
        }
    }


    /**
     * 文件是否存在
     * @param url
     * @return
     */
    public static boolean fileIsexist(String url){
        try {
            File f = new File(url);
            if (f.exists())
                return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *开始播放声音音频
     */
    public static void doplay(File audiofile) {
        mediaPlayer = new MediaPlayer();
        try {
            // 设置播放器的声音源
            mediaPlayer.setDataSource(audiofile.getAbsolutePath());
            Log.e("------播放音频路径--", audiofile.getAbsolutePath());
            /**设置监听回调*/
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
    /**停止播放*/
    public static void stopPlay() {
        /**重置播放状态*/
        isPlaying=false;
        if (mediaPlayer!=null){
            /**重置监听器，防止内存泄露*/
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            /**释放资源*/
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    /**播放失败*/
    public static void playfail() {
        mainthreadHandler.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(context, "播放失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
