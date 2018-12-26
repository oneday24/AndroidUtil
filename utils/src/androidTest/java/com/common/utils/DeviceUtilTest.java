package com.common.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * Test some thing like sharepreference, parcelable, context
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DeviceUtilTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.common.utils.test", appContext.getPackageName());
    }

    Context appContext = InstrumentationRegistry.getTargetContext();

    /**
     * genymotion 3424feec3d8206fe
     * 我的小米手机 a8b615025329e2d3
     * @throws Exception
     */
    @Test
    public void test_deviceId() throws Exception {
        String id = DeviceUtil.getDeviceId(appContext);
        TestUtil.showLog(id);
        assertNotNull(id);
    }

    @Test
    public void test_display() throws Exception {
        DisplayMetrics displayMetrics = DeviceUtil.getDisplayMetrics(appContext);
        TestUtil.showLog(displayMetrics.toString());
        assertNotNull(displayMetrics);
    }

    @Test
    public void test_deviceInformation() throws Exception {
        Map<String, String> map = DeviceUtil.getDeviceInformation();
        for(Map.Entry<String, String> entry : map.entrySet()){
            TestUtil.showLog(entry.getKey()+" "+entry.getValue());
        }
    }

    @Test
    public void test_network() throws Exception {
        TestUtil.showLog("net "+DeviceUtil.isNetworkConnected(appContext));
    }

}
