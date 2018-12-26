package com.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengjing on 17/12/8.
 */

public class PermissionUtil {
    public interface PermissionResult{
        void grant();
        void deny();
    }

    /**
     * 如果没有权限，并且点击了不再提醒，则告诉用户拒绝了权限，不再请求权限了
     * @param context
     * @param requestCode
     * @param permission
     * @param resultListener
     */
    public static void checkPermission(Activity context, int requestCode, String[] permission, PermissionResult resultListener){
        if(permission==null || permission.length==0) return;
        if(Build.VERSION.SDK_INT >= 23){
            List<String> permissions = new ArrayList<>();
            for(int i=0; i<permission.length; i++){
                if(context.checkSelfPermission(permission[i]) != PackageManager.PERMISSION_GRANTED){
//在M 开发者预览版2上Fragment.shouldShowRequestPermissionRationale() 总是返回false。这个bug会在今后版本修复。
//                    if(!ActivityCompat.shouldShowRequestPermissionRationale(context, permission[i])){
//                        resultListener.deny();
//                        return;
//                    }
                    permissions.add(permission[i]);
                }
            }
            if(permissions.size()>0){
                String[] permissionArray = new String[permissions.size()];
                permissions.toArray(permissionArray);
                context.requestPermissions(permissionArray,requestCode);
            }else{
                resultListener.grant();
            }
        }else{
            resultListener.grant();
        }
    }

    public static void onRequestPermissionsResult(int[] grantResults, PermissionResult resultListener){
        for(int g : grantResults){
            if(g != PackageManager.PERMISSION_GRANTED){
                resultListener.deny();
                return;
            }
        }
        resultListener.grant();
    }
}
