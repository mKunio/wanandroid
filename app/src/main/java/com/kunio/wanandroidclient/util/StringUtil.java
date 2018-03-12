package com.kunio.wanandroidclient.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class StringUtil {
    public static String removeParentheses(String old){
        Pattern p = Pattern.compile("<(.*?)>");
        Matcher matcher = p.matcher(old);
        while (matcher.find()){
            old = old.replace(matcher.group(),"");
        }
        return old;
    }
}
