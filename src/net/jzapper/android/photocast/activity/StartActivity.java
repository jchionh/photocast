package net.jzapper.android.photocast.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.cast.*;
import net.jzapper.android.photocast.R;
import net.jzapper.android.photocast.chromecast.CastAppName;
import net.jzapper.android.photocast.chromecast.CastInfo;
import net.jzapper.android.photocast.dialog.DeviceSelectionDialog;
import net.jzapper.android.photocast.message.PhotocastMessageStream;
import net.jzapper.android.photocast.utils.Logger;

import java.io.IOException;

/**
 * User: jchionh
 * Date: 9/29/13
 * Time: 11:07 PM
 */
public class StartActivity extends Activity {
    private static final String TAG = StartActivity.class.getSimpleName();
    private static final String SESSION_TAG = "Session";

    private static final int NOT_CONNECTED = 0;
    private static final int CONNECTED = 1;

    private int connState = NOT_CONNECTED;
    TextView statusView;
    Button connectButton;
    Button disconnectButton;
    PhotocastMessageStream messageStream = new PhotocastMessageStream();

    private DeviceSelectionDialog deviceSelectionDialog;
    private ApplicationSession applicationSession;
    private ApplicationSession.Listener appSessionListener = new ApplicationSession.Listener() {
        @Override
        public void onSessionStarted(ApplicationMetadata applicationMetadata) {
            Logger.d(SESSION_TAG, "#onSessionStarted");

            if (!applicationSession.hasChannel()) {
                Logger.d(SESSION_TAG, "application session does not support channels");
                return;
            }

            ApplicationChannel channel = applicationSession.getChannel();
            channel.attachMessageStream(messageStream);
            Logger.d(TAG, "attached Message stream.");
        }

        @Override
        public void onSessionStartFailed(SessionError sessionError) {
            Logger.d(SESSION_TAG, "#onSessionStartFailed");
        }

        @Override
        public void onSessionEnded(SessionError sessionError) {
            Logger.d(SESSION_TAG, "#onSessionEnded");
            if (sessionError != null) {
                // The session ended due to an error.
                Logger.d(SESSION_TAG, "The session ended due to an error.");
            } else {
                // The session ended normally.
                Logger.d(SESSION_TAG, "The session ended normally.");
            }
        }
    };


    /**
     * on create activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_to_cast);
        connState = NOT_CONNECTED;
        // now get references to our button and status view
        statusView = (TextView) findViewById(R.id.start_act_status);
        connectButton = (Button) findViewById(R.id.btn_connect);
        disconnectButton = (Button) findViewById(R.id.btn_disconnect);

        // now we create a cast context and set it
        if (CastInfo.castContext != null) {
            CastInfo.castContext.dispose();
        }
        CastInfo.castContext = new CastContext(getApplicationContext());
    }

    /**
     * called when the connect to chromecast device button is pressed
     * @param view
     */
    public void disconnectDevice(View view) {
        endSession();
    }

    /**
     * called when the connect to chromecast device button is pressed
     * @param view
     */
    public void selectDevice(View view) {
        Logger.d(TAG, "Select Button pressed.");
        deviceSelectionDialog = new DeviceSelectionDialog(this);
        deviceSelectionDialog.setListener(new DeviceSelectionDialog.DeviceSelectionListener() {

            @Override
            public void onSelected(DeviceSelectionDialog dialog) {
                deviceSelectionDialog = null;
                CastDevice device = dialog.selectedDevice();
                if (device != null) {
                    setStatusText(String.format("Connected to: %s", device.getFriendlyName()));
                    CastInfo.castDevice = device;
                    startSession(device, CastInfo.castContext);
                    //findViewById(R.id.start).setEnabled(true);
                    //launchReceiver(device, PhotocastApp.getInstance().getCastContext());
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

    /**
     * here, we can start a new app session given the cast device and cast context
     * @param castDevice
     * @param castContext
     */
    private void startSession(CastDevice castDevice, CastContext castContext) {
        // end the session if there was a previous session
        endSession();

        // now create a new session and try to connect
        applicationSession = new ApplicationSession(castContext, castDevice);
        applicationSession.setListener(appSessionListener);
        try {
            applicationSession.startSession(CastAppName.CAST_APP_NAME);
        } catch (IOException e) {
            Logger.e(SESSION_TAG, "Failed to open a session", e);
        }
    }

    /**
     * we end the session here
     */
    private void endSession() {
        // if we don't have a session, nothing to do.
        if (applicationSession == null) {
            return;
        }

        if (applicationSession.hasChannel()) {
            //mGameMessageStream.leave();
        }
        try {
            if (applicationSession.hasStarted()) {
                applicationSession.endSession();
            }
        } catch (IOException e) {
            Logger.e(SESSION_TAG, "Failed to end the session.", e);
        } catch (IllegalStateException e) {
            Logger.e(SESSION_TAG, "Unable to end session.", e);
        }
        applicationSession = null;

        setStatusText(getResources().getString(R.string.not_connected));
        CastInfo.castDevice = null;
    }

    /**
     * status text
     * @param text
     */
    private void setStatusText(String text) {
        if (statusView == null) {
            return;
        }
        statusView.setText(text);
    }

    /**
     * cleanup session if we have one during the onStop
     */
    @Override
    protected void onStop() {
        Logger.d(TAG, "#onStop");
        endSession();
        if (CastInfo.castContext != null) {
            CastInfo.castContext.dispose();
            CastInfo.castContext = null;
        }
        super.onStop();
    }

    public void testMessage(View view) {
        messageStream.sendTestMessage();
    }
}
