package net.jzapper.android.photocast.app;

import android.app.Application;
import com.google.cast.CastContext;
import com.google.cast.CastDevice;
import com.google.cast.Logger;

/**
 * User: jchionh
 * Date: 8/26/13
 * Time: 6:16 PM
 */
public class PhotocastApp extends Application {
    private static final String TAG = PhotocastApp.class.getSimpleName();
    private static Logger sLog = new Logger(TAG, true);

    // our application singleton
    private static PhotocastApp instance;

    private CastContext castContext;
    private CastDevice castDevice;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sLog.d("Photocast Sender App #onCreate()");
        try {
            castContext = new CastContext(getApplicationContext());
        } catch (IllegalArgumentException e) {
            sLog.e(e, "Unable to create CastContext");
        }
    }

    /**
     * return the cast context that we've created in the onCreate()
     * @return
     */
    public CastContext getCastContext() {
        return castContext;
    }

    /**
     * return our cast device previously set
     * @return
     */
    public CastDevice getCastDevice() {
        return castDevice;
    }

    /**
     * set the cast device once we've selected it
     * @param castDevice
     */
    public void setCastDevice(CastDevice castDevice) {
        this.castDevice = castDevice;
    }

    /**
     * return our app singleton instance
     * @return
     */
    public static PhotocastApp getInstance() {
        return instance;
    }
}
