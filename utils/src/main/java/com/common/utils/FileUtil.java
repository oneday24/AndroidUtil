package com.common.utils;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zengjing on 10/17/17.
 *
 * 1 External Storage, Android/data/pacakage-name/
 *   removable storage media (such as an SD card) or an internal (non-removable) storage
 *   need android.PermissionUtil.WRITE_EXTERNAL_STORAGE
 *   1.1 files public to others, root path, like Music/, Pictures/, DCIM, and Ringtones/
 *   1.2 files private to app, Android/data/pacakage-name/, above 4.4, do not need PermissionUtil
 *      when uninstalled, system will delete the pacakage
 *      1.2.1 files
 *      1.2.2 cache, maintain the cache files yourself
 * 2 Internal Storage, data/data/pacakage-name/
 *   2.1 cache, getCacheDir,
 *       system will delete cache files when app is low on internal storage,
 *       maintain yourself is best.
 *   2.2 lib
 *   2.3 files, getFilesDir
 *   2.4 shared_prefs
 * files
 */

public class FileUtil {

    public static String userDir = "user";//分用户存文件
    public static void init(String dir){
        userDir = dir;
    }

    public static File[] getFileList(File file){
        return file.listFiles();
    }

    /**
     * 文件夹里有下级目录时，file.delete()删除不了
     * 所以需要递归调用删除
     * @param path
     * @return
     */
    public static boolean deleteFile(String path){
        File file = new File(path);
        if(!file.isDirectory()){
            return file.delete();
        }
        File[] files = file.listFiles();
        if(files==null){
            return file.delete();
        }
        for(File f : files){
            deleteFile(f.getPath());
        }
        return file.delete();
    }

    /*----------External Storage----------*/

    /**
     *  Checks if external storage is available for read and write
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * DCIM
     * @return
     */
    public static File getDCIMDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getPath());
        return file;
    }

    public static File getExternalCacheDir(Context context){

        return context.getExternalCacheDir();
    }
    public static File[] getExternalCacheDirs(Context context){

        return ContextCompat.getExternalCacheDirs(context);
    }

    /**
     * Android/data/pacakage-name/files
     * 调用方法时，自动创建文件夹
     * @param context
     * @return
     */
    public static File getExternalFilesDir(Context context){
        return context.getExternalFilesDir(null);
    }
    /**
     * Android/data/pacakage-name/files/$type
     * 调用方法时，自动创建文件夹及type下级目录
     * @param context
     * @param type
     * @return
     */
    public static File getExternalFilesDir(Context context, String type){
        return context.getExternalFilesDir(type);
    }
    public static File[] getExternalFilesDirs(Context context){
        return ContextCompat.getExternalFilesDirs(context, null);
    }
    public static File[] getExternalFilesDirs(Context context, String type){
        return ContextCompat.getExternalFilesDirs(context, type);
    }


    /*----------Internal Storage----------*/

    /**
     * /data/data/pacakage/dirname
     * @param context
     * @param dirname
     * @return
     */
    public static File getInternalDir(Context context, String dirname){
        return context.getDir(dirname, Context.MODE_PRIVATE);
    }

    /**
     * /data/data/pacakage/cache
     * @param context
     * @return
     */
    public static File getInternalCacheDir(Context context){
        return context.getCacheDir();
    }

    /**
     * /data/data/pacakage/files
     * @param context
     * @return
     */
    public static File getInternalFilesDir(Context context){
        return context.getFilesDir();
    }

    /**
     * data/data/pacakage-name/files/filename
     * @param context
     * @param filename
     * @param content
     * @throws IOException
     */
    public static void writeInternalFile(Context context, String filename, String content) throws IOException{
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        fos.write(content.getBytes());
        fos.close();
    }

    public static InputStream readInternalFile(Context context, String filename) throws IOException{
        FileInputStream fis = context.openFileInput(filename);
        return fis;
    }

    public static File getAppFileDir(Context context){
        return context.getFilesDir();
    }

    public static String getAppFileDirPath(Context context){
        File file = new File(context.getFilesDir().getPath()+"/"+userDir);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getPath();
    }

    public static void saveString(String path, String content){
        if(TextUtils.isEmpty(content)) return;
        try {
            FileOutputStream fout = new FileOutputStream(path);
            byte [] bytes = content.getBytes();
            fout.write(bytes);
            fout.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * BufferedOutputStream write方法将数据写入缓冲区，缓冲区满时将数据写入文件，默认缓存8192byte
     * FileOutputStream write一次写入文件一次
     * 所以该方法中用不到BufferedOutputStream，因为content是全部的数据，一次写入就行
     * 如果是边从InputStream中读入再写入文件的话，则需要BufferedOutputStream的缓冲功能
     * @param path
     * @param content
     */
    public static void saveByte(String path, byte[] content){
        if(content==null) return;
        File file = new File(path);
        File dir = file.getParentFile();
        if(!dir.exists()){
            dir.mkdirs();// 如果文件夹不存在，则会报FileNotFoundException，文件不存在没有关系
        }
        try {
            FileOutputStream fos= new FileOutputStream(path);
            fos.write(content);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
