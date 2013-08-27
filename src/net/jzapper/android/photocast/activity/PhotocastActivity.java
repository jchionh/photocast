package net.jzapper.android.photocast.activity;

import android.app.Activity;
import android.os.Bundle;
import com.google.cast.*;
import net.jzapper.android.photocast.R;
import net.jzapper.android.photocast.app.PhotocastApp;
import net.jzapper.android.photocast.dialog.DeviceSelectionDialog;

import java.io.IOException;

public class PhotocastActivity extends Activity  {

    private static final String TAG = PhotocastActivity.class.getSimpleName();
    private static Logger sLog = new Logger(TAG, true);

    private DeviceSelectionDialog deviceSelectionDialog;
    private ApplicationSession applicationSession;
    private ApplicationSession.Listener appSessionListener = new ApplicationSession.Listener() {
        @Override
        public void onSessionStarted(ApplicationMetadata applicationMetadata) {
            sLog.d("#onSessionStarted");
        }

        @Override
        public void onSessionStartFailed(SessionError sessionError) {
            sLog.d("#onSessionStartFailed");
        }

        @Override
        public void onSessionEnded(SessionError sessionError) {
            sLog.d("#onSessionEnded");
            if (sessionError != null) {
                // The session ended due to an error.
                sLog.d("The session ended due to an error.");
            } else {
                // The session ended normally.
                sLog.d("The session ended normally.");
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        selectDevice();
    }

    /**
     * Creates a new DeviceSelectionDialog with an attached listener, which listens for device
     * selection, sets the device in the top-level Application, and enables the game start button.
     */
    private void selectDevice() {
        deviceSelectionDialog = new DeviceSelectionDialog(this);
        deviceSelectionDialog.setListener(new DeviceSelectionDialog.DeviceSelectionListener() {

            @Override
            public void onSelected(DeviceSelectionDialog dialog) {
                deviceSelectionDialog = null;
                CastDevice device = dialog.selectedDevice();
                if (device != null) {
                    //setConnectedDeviceTextView(dialog.selectedDevice().getFriendlyName());
                    PhotocastApp.getInstance().setCastDevice(device);
                    //findViewById(R.id.start).setEnabled(true);
                    launchReceiver(device, PhotocastApp.getInstance().getCastContext());
                } else {
                    //setConnectedDeviceTextView(
                    //        MainActivity.this.getResources().getString(R.string.no_device));
                    //findViewById(R.id.start).setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DeviceSelectionDialog dialog) {
                deviceSelectionDialog = null;
            }
        });
        try {
            deviceSelectionDialog.show();
        } catch (IllegalStateException e) {
            //showErrorDialog(e.getMessage());
            e.printStackTrace();
        }
    }

    private void launchReceiver(CastDevice castDevice, CastContext castContext) {

        applicationSession = new ApplicationSession(castContext, castDevice);
        applicationSession.setListener(appSessionListener);

        try {
            applicationSession.startSession("530efe8a-f8e2-4cef-aecb-7d3dc8d5463b");
        } catch (IOException e) {
            sLog.e("Failed to open a session", e);
        }

    }


    @Override
    protected void onStop() {
        if (applicationSession != null) {
            if (applicationSession.hasChannel()) {
                //mGameMessageStream.leave();
            }
            try {
                if (applicationSession.hasStarted()) {
                    applicationSession.endSession();
                }
            } catch (IOException e) {
                sLog.e("Failed to end the session.", e);
            } catch (IllegalStateException e) {
                sLog.e("Unable to end session.", e);
            }
        }
        applicationSession = null;
        super.onStop();
    }
}
