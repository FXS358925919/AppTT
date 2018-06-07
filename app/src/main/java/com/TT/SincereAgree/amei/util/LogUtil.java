package com.TT.SincereAgree.amei.util;

import android.util.Log;

/**
 * Created by Amei on 2017/11/29.
 */

public class LogUtil {
    public static boolean Debug=true;

    public static void i(String str){
        if(Debug){
            Log.i("TT", str);
        }
    }
}
