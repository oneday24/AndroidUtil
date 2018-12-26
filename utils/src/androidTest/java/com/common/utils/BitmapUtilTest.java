package com.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.ArrayMap;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by zengjing on 10/20/17.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BitmapUtilTest {
    Context appContext = InstrumentationRegistry.getTargetContext();
    @Test
    public void test_bitmap(){
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
        bitmap.recycle();
    }
}
