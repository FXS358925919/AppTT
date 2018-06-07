package com.TT.SincereAgree.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.rong.imkit.RongIM;

/**
 * Created by 冯雪松 on 2018-03-15.
 */

public class TimerSchedule {
    public static List<Integer> messageId = new ArrayList<>();
    public static Timer timer = new Timer();

    public static void test() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!messageId.isEmpty()) {
                    int id = messageId.remove(0);
                    RongIM.getInstance().deleteMessages(new int[]{id}, null);
                }

            }
        },10000);
    }
}
