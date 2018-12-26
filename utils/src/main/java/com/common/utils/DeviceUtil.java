package com.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ada Zeng on 10/17/17.
 * scope 1,single app 2,group of apps 3,device
 * resettability and persistence 1,restart app reset 2,install reset 3,factory-resets 4,factory-persistence
 * uniqueness
 * MAC Address, after Android M, through wifi or bluetooth, you can only get 02:00:00:00:00:00
 * Advertising ID, user resettable
 * Instance ID, GUID
 */

public class DeviceUtil {
    /**
     * ANDROID_ID 2.2 has some problem, 16 letters
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }

    /**
     * device softwate and hardware information
     * @return
     */
    public static Map<String, String> getDeviceInformation(){
        Map<String, String> info = new HashMap<>();
        info.put("model", Build.MODEL);//phone model, like MI4
        info.put("sdk", Build.VERSION.SDK_INT+"");
        if(Build.VERSION.SDK_INT>=21){
            info.put("ABIS", Arrays.toString(Build.SUPPORTED_ABIS));
            info.put("32_ABIS", Arrays.toString(Build.SUPPORTED_32_BIT_ABIS));
            info.put("64_ABIS", Arrays.toString(Build.SUPPORTED_64_BIT_ABIS));
        }else{
            info.put("CPU_ABI", Build.CPU_ABI);
            info.put("CPU_ABI2", Build.CPU_ABI2);
        }

        info.put("vm.name", System.getProperty("java.vm.name"));//dalvik,art
        info.put("os.arch", System.getProperty("os.arch"));//armv7l
        info.put("os.name", System.getProperty("os.name"));//Linux
        info.put("os.version", System.getProperty("os.version"));//2.6.32.9-g103d848
        return info;
    }

    /**
     * need android.PermissionUtil.ACCESS_NETWORK_STATE
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info.isConnected();
    }

}
