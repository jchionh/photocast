package net.jzapper.android.photocast.utils;

/**
 * User: jchionh
 * Date: 9/29/13
 * Time: 11:42 PM
 */
public enum LogLevel {

    VERBOSE, DEBUG, INFO, WARN, ERROR;

    /**
     * determine our current log level
     */
    private static LogLevel level = VERBOSE;

    /**
     * are we logging at this level?
     * @return
     */
    public boolean canLog() {
        return ordinal() >= level.ordinal();
    }

    /**
     * set our new log level
     * @param newLevel
     */
    public static void setLogLevel(LogLevel newLevel) {
        level = newLevel;
    }

}
