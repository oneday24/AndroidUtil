package com.common.utils;

import android.text.TextUtils;

/**
 * Created by zengjing on 18/10/22.
 */

public class DataMaskingUtil {
    public static String bankMasking(String str){
        if(TextUtils.isEmpty(str)) return "";
        return str.replaceAll("\\d+(\\d{4})","**** **** **** $1");
    }

    public static String phoneMasking(String str){
        if(TextUtils.isEmpty(str)) return "";
        return str.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
