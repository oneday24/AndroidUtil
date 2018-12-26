package com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
//import android.support.v8.renderscript.Allocation;
//import android.support.v8.renderscript.Element;
//import android.support.v8.renderscript.RenderScript;
//import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zengjing on 10/20/17.
 * 暂时不加高斯模糊的代码，因为在本项目中没有使用
 * 高斯模糊的库libRSSupport每个cpu版本会占据400多k，librsjni.so有20多k
 */

public class BitmapUtil {
    public static Bitmap revitionImageSize(String path){
        if(path==null) return null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(
                    new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            Bitmap bitmap;
            while (true) {
                if ((options.outWidth >> i <= 1000)
                        && (options.outHeight >> i <= 1000)) {
                    in = new BufferedInputStream(
                            new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
            return bitmap;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String compressToBase64(String path){
        if(path==null) return null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(
                    new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            Bitmap bitmap;
            while (true) {
                if ((options.outWidth >> i <= 1000)
                        && (options.outHeight >> i <= 1000)) {
                    in = new BufferedInputStream(
                            new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String fileToBase64(String path){
        File  file = new File(path);
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputFile!=null){
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap base64ToBitmap(String base64String){
        if(TextUtils.isEmpty(base64String)) return null;
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        if(decode==null || decode.length==0) return null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return bitmap;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if(bitmap==null) return null;
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.flush();
            byte[] bitmapBytes = baos.toByteArray();
            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * jpg的图片大小比png小一半
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] buffer = baos.toByteArray();

        return buffer;
    }

    public static Bitmap byteToBitmap(byte[] data){
        Bitmap bitmap;
        if (data != null) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            return bitmap;
        }
        return null;
    }

    /**
     * 按最大边按一定大小缩放图片
     * 根据宽度缩放图片
     * */
    public static Bitmap scaleImage(String path, float width){
        // 获取原图宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 计算缩放比例
        float size = options.outWidth / width;
        // 缩放
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) size;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public static Bitmap scaleImage(byte[] data, int width){
        if(data==null || data.length==0) return null;
        // 获取原图宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 计算缩放比例
        float size = options.outWidth / width;
        // 缩放
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) size;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        return bm;
    }

//    public static Bitmap takeScreenShot(Activity activity) {
//        View view = activity.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//
//
//        Bitmap b1 = view.getDrawingCache();
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//
//        Display display = activity.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
//        view.destroyDrawingCache();
//        return b;
//    }
//    //图片缩放比例
//    private static final float BITMAP_SCALE = 0.4f;
//
//    /**
//     * 模糊图片的具体方法
//     *
//     * @param context 上下文对象
//     * @param image   需要模糊的图片
//     * @return 模糊处理后的图片
//     */
//    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {
//        // 计算图片缩小后的长宽
//        int width = Math.round(image.getWidth() * BITMAP_SCALE);
//        int height = Math.round(image.getHeight() * BITMAP_SCALE);
//
//        // 将缩小后的图片做为预渲染的图片
//        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
//        // 创建一张渲染后的输出图片
//        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
//
//        // 创建RenderScript内核对象
//        RenderScript rs = RenderScript.create(context);
//        // 创建一个模糊效果的RenderScript的工具对象
//        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//
//        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
//        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
//        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
//        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
//
//        // 设置渲染的模糊程度, 25f是最大模糊度
//        blurScript.setRadius(blurRadius);
//        // 设置blurScript对象的输入内存
//        blurScript.setInput(tmpIn);
//        // 将输出数据保存到输出内存中
//        blurScript.forEach(tmpOut);
//
//        // 将数据填充到Allocation中
//        tmpOut.copyTo(outputBitmap);
//
//        return outputBitmap;
//    }
}
