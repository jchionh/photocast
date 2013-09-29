package net.jzapper.android.photocast.utils;

import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static net.jzapper.android.photocast.utils.LogLevel.*;

/**
 * User: jchionh
 * Date: 9/29/13
 * Time: 11:32 PM
 */
public class Logger {
    private static final String PREFIX = "XLB_";
    private static final String NULL_STRING = "<null>";
    private static final ConcurrentMap<String, String> tagMap = new ConcurrentHashMap<String, String>();

    /**
     * prefix our log tag with our app prefix
     * @param tag
     * @return
     */
    private static String getLogTag(String tag) {
        if (tag == null) {
            tag = NULL_STRING;
        }
        String returnTag = tagMap.get(tag);
        if (returnTag == null) {
            returnTag = PREFIX + tag;
            tagMap.putIfAbsent(tag, returnTag);
        }
        return returnTag;
    }

    /**
     * create the log string here
     * @param format
     * @param args
     * @return
     */
    private static String getLog(String format, Object... args) {
        if (format == null) {
            return NULL_STRING;
        }
        if (args.length == 0) {
            return format;
        }
        return String.format(format, args);
    }

    public static void v(String tag, String format, Object... args) {
        if (VERBOSE.canLog()) {
            Log.v(getLogTag(tag), getLog(format, args));
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (INFO.canLog()) {
            Log.i(getLogTag(tag), getLog(format, args));
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (DEBUG.canLog()) {
            Log.d(getLogTag(tag), getLog(format, args));
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (WARN.canLog()) {
            Log.w(getLogTag(tag), getLog(format, args));
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (ERROR.canLog()) {
            Log.e(getLogTag(tag), getLog(format, args));
        }
    }
}
