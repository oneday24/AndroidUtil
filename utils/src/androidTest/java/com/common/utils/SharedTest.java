package com.common.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by zengjing on 10/20/17.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SharedTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void test_set() throws Exception{
        SharedPreferencesUtil.put(appContext, "test1", "ni de le ya");
        String str = SharedPreferencesUtil.get(appContext, "test1", null);
        TestUtil.showLog(str);
    }

    @Test
    public void test_get() throws Exception{
        String str = SharedPreferencesUtil.get(appContext, "test2", null);
        TestUtil.showLog(str);
    }

    @Test
    public void test_secret() throws Exception{
        SharedPreferencesUtil.putSecret(appContext, "test4", "wu qiong wu jin");
        String str = SharedPreferencesUtil.getSecret(appContext, "test4", null);
        TestUtil.showLog(str);
    }

    @Test
    public void test_clear() throws Exception{
        SharedPreferencesUtil.clear(appContext);
    }
}
