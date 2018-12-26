package com.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zengjing on 10/18/17.
 * privacy information of the user
 * return null if have no PermissionUtil or have no information
 */

public class PrivacyUtil {

    /**
     *
     * @param context
     * @return
     */
    public static List<List<String>> getAllPhoneNumber(Context context){
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                     new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                             ,ContactsContract.CommonDataKinds.Phone.NUMBER,},null,null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
        if(cursor==null) return null;
        List<List<String>> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(!TextUtils.isEmpty(phoneNumber)){
                List<String> list = new ArrayList<>();
                list.add(name);
                list.add(phoneNumber);
                result.add(list);
            }
        }
        cursor.close();
        return result;
    }

    public static List<String> getAllEmails(Context context){
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,null,null, null);
        if(cursor==null) return null;
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            if(!TextUtils.isEmpty(address)){
                list.add(name+" "+address);
            }
        }
        cursor.close();

        return list;
    }

    /**
     * get all the call log
     * @param context
     * @return
     * @throws SecurityException
     */
    public static List<List<String>> getCallHistory(Context context) throws SecurityException{
        return getCallHistory(context, -1, -1);
    }

    /**
     * get call log by time
     * android.PermissionUtil.READ_CALL_LOG
     * need explicit requiring for PermissionUtil, users need to agree
     * @param context
     * @return
     * @throws SecurityException
     */
    public static List<List<String>> getCallHistory(Context context, long startTime, long endTime) throws SecurityException{

        String selection = startTime==-1 ? null : CallLog.Calls.DATE + " between ? and ?";
        String[] selectionArgs = endTime==-1 ? null : new String[]{startTime+"", endTime+""};
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                new String[] { CallLog.Calls.CACHED_NAME// 通话记录的联系人
                        , CallLog.Calls.NUMBER// 通话记录的电话号码
                        , CallLog.Calls.DATE// 通话记录的日期
                        , CallLog.Calls.DURATION// 通话时长
                        , CallLog.Calls.TYPE }// 通话类型
                ,selection,selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        if(cursor==null) return null;
        List<List<String>> result = new ArrayList<>();
        while (cursor.moveToNext()){
            List<String> list = new ArrayList<>();
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            if(TextUtils.isEmpty(number)) continue;
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA).format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String typeString;
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeString = "打入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeString = "打出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeString = "未接";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    typeString = "拒绝";
                    break;
                default:
                    typeString = "";
                    break;
            }
            list.add(name==null ? "" : name);
            list.add(number);
            list.add(date==null ? "" : date);
            list.add(duration+"");
            list.add(typeString);
            result.add(list);
        }
        cursor.close();
        return result;
    }

    /**
     * android.PermissionUtil.READ_PHONE_STATE
     * Cell Location,IMEI/MEID,Device Phone Number,Network Name,SIM Card Infomarion,
     * Network Type
     * @param context
     * @return
     */
    public static List<String> getTelephonyInfo(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        List<String> result = new ArrayList<>();
        result.add(manager.getLine1Number());//有风险，有些sim卡没有写入本机号码
        String deviceId;
        if(Build.VERSION.SDK_INT>=26){
            deviceId = manager.getImei();
            deviceId = deviceId!=null ? deviceId : manager.getMeid();
        }else{
            deviceId = manager.getDeviceId();
        }
        result.add(deviceId);
        result.add(manager.getNetworkOperatorName());
        result.add(manager.getSimOperatorName());
        result.add(manager.getNetworkType()+"");
        int phoneType = manager.getPhoneType();
        switch (phoneType){
            case TelephonyManager.PHONE_TYPE_CDMA:
                result.add("CDMA");
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                result.add("GSM");
                break;
            case TelephonyManager.PHONE_TYPE_SIP:
                result.add("SIP");
                break;
            default:
                result.add("");
                break;
        }
        return result;
    }

    public static List<String> getInstalledApps(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);

        List<String> result = new ArrayList<>();
        for(PackageInfo packageInfo : list){
            if( (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1){
                String str = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                if(!TextUtils.isEmpty(str)){
                    result.add(str);
                }
            }
        }
        return result;
    }
}
