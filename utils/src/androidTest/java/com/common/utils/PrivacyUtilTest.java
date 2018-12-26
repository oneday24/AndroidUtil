package com.common.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zengjing on 10/18/17.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PrivacyUtilTest {
    Context appContext = InstrumentationRegistry.getTargetContext();
    @Test
    public void test_phone_number() throws Exception{
        List<List<String>> list = PrivacyUtil.getAllPhoneNumber(appContext);
        for(List<String> str : list){
            TestUtil.showLog(str.get(0)+" "+str.get(1));
        }
        TestUtil.showLog(list.size()+"");
        TestUtil.showLog("######################");
    }
    @Test
    public void test_email() throws Exception{
        List<String> list = PrivacyUtil.getAllEmails(appContext);
        for(String str : list){
            TestUtil.showLog(str);
        }
        TestUtil.showLog("######################");
    }
    @Test
    public void test_app() throws Exception{
        List<String> list = PrivacyUtil.getInstalledApps(appContext);
        for(String str : list){
            TestUtil.showLog(str);
        }
        TestUtil.showLog("######################");
    }
    @Test
    public void test_call_history() throws Exception{
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        List<List<String>> list = PrivacyUtil.getCallHistory(appContext, start.getTimeInMillis(), end.getTimeInMillis());
        for(List<String> row : list){
            StringBuilder builder = new StringBuilder();
            for(String str : row){
                builder.append(str);
                builder.append("=");
            }
            TestUtil.showLog(builder.toString());
        }
        TestUtil.showLog("######################");
    }
    @Test
    public void test_telephony() throws Exception{
        List<String> list = PrivacyUtil.getTelephonyInfo(appContext);
        for(String str : list){
            TestUtil.showLog("telephony " + str);
        }
        TestUtil.showLog("######################");
    }


}
