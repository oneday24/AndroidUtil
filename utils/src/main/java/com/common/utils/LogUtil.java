package com.common.utils;

import android.util.Log;

/**
 * Created by zengjing on 16/3/8.
 * you can also use the BuildConfig property for the trigger
 * private static boolean debug = BuildConfig.DEBUG;
 */
public class LogUtil {
    public static final String TAG = "ITU";

    private static boolean debug = BuildConfig.DEBUG;

    public static int v(String tag, String msg) {
        if (debug) {
            return Log.v(tag, msg);
        }
        return 0;
    }

    public static int d(String tag, String msg) {
        if (debug) {
            return Log.d(tag, msg);
        }
        return 0;
    }

    public static int i(String tag, String msg) {
        if (debug) {
            return Log.i(tag, msg);
        }
        return 0;
    }

    public static int w(String tag, String msg) {
        if (debug) {
            return Log.w(tag, msg);
        }
        return 0;
    }

    public static int e(String tag, String msg) {
        if (debug) {
            return Log.e(tag, msg);
        }
        return 0;
    }
}
