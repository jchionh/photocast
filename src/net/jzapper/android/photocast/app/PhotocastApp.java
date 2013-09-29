package net.jzapper.android.photocast.app;

import android.app.Application;
import net.jzapper.android.photocast.utils.Logger;

/**
 * User: jchionh
 * Date: 8/26/13
 * Time: 6:16 PM
 */
public class PhotocastApp extends Application {
    private static final String TAG = PhotocastApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "Photocast Sender App #onCreate()");
    }
}
