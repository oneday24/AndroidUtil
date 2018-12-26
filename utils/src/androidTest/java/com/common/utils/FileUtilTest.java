package com.common.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by zengjing on 10/17/17.
 * When test, need to set app PermissionUtil enabled
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class FileUtilTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void test_storage_available() throws Exception{
        assertTrue(FileUtil.isExternalStorageWritable());
    }

    @Test
    public void test_external_storage() throws Exception {

        File filesDir = FileUtil.getExternalFilesDir(appContext);
        TestUtil.showLog(filesDir.getPath());

        File cacheDir = FileUtil.getExternalCacheDir(appContext);
        TestUtil.showLog(cacheDir.getPath());

        boolean deleteResult = FileUtil.deleteFile(filesDir.getPath())
                && FileUtil.deleteFile(cacheDir.getPath());
        assertTrue(deleteResult);
    }

    @Test
    public void test_internal_storage() throws Exception {
        File filesDir = FileUtil.getInternalFilesDir(appContext);
        TestUtil.showLog(filesDir.getPath());

        File cacheDir = FileUtil.getInternalCacheDir(appContext);
        TestUtil.showLog(cacheDir.getPath());

        FileUtil.writeInternalFile(appContext, "haha.txt", "ni sha yi is yo, 狗带");

        InputStream is = FileUtil.readInternalFile(appContext, "haha.txt");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int number;
        int len = 0;
        while((number = is.read(temp))!=-1){
            bos.write(temp, len, number);
            len += number;
        }
        TestUtil.showLog(bos.toString());

    }
}
