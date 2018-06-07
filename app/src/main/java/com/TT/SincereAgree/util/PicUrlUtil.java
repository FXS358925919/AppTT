package com.TT.SincereAgree.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amei on 2018/6/3.
 */

public class PicUrlUtil {
    public  static String getPicturePath(String url){
        String pattern = ".*url=(.*)";
        Matcher matcher = Pattern.compile(pattern).matcher(url);
        matcher.find();
        return matcher.group(1);
    }
}
