package com.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.common.utils.encrypt.AESUtil;

/**
 * Created by zengjing on 10/19/17.
 * store setting info
 * store account
 */

public class SharedPreferencesUtil {

    private static String password = "hello,boy";

    public static void put(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String get(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putSecret(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String v = AESUtil.encrypt(value, password);
        editor.putString(key, v);
        editor.apply();
    }

    public static String getSecret(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(key, defaultValue);
        return AESUtil.decrypt(result, password);
    }

    public static void clear(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void delete(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
