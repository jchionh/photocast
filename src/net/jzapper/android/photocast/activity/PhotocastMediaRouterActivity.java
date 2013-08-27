package net.jzapper.android.photocast.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import com.google.cast.*;
import net.jzapper.android.photocast.R;
import net.jzapper.android.photocast.app.PhotocastApp;
import net.jzapper.android.photocast.dialog.DeviceSelectionDialog;

public class PhotocastMediaRouterActivity extends Activity implements MediaRouteAdapter {

    private DeviceSelectionDialog deviceSelectionDialog;
    protected MediaRouteButton mediaRouteButton;
    private MediaRouter mediaRouter;
    private MediaRouteSelector mediaRouteSelector;
    private MediaRouter.Callback mediaRouterCallback;
    private MediaRouteStateChangeListener routeStateListener;
    private CastDevice castDevice;
    private CastContext castContext;

    private class MyMediaRouterCallback extends MediaRouter.Callback {
        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteHelper.requestCastDeviceForRoute(route);
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
            castDevice = null;
            routeStateListener = null;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //selectDevice();
        castContext = new CastContext(getApplicationContext());

        mediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);

        MediaRouteHelper.registerMinimalMediaRouteProvider(castContext, this);
        mediaRouter = MediaRouter.getInstance(getApplicationContext());
        mediaRouteSelector = MediaRouteHelper.buildMediaRouteSelector(
                MediaRouteHelper.CATEGORY_CAST);
        mediaRouteButton.setRouteSelector(mediaRouteSelector);
        mediaRouterCallback = new MyMediaRouterCallback();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaRouter.addCallback(mediaRouteSelector, mediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onStop() {
        mediaRouter.removeCallback(mediaRouterCallback);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MediaRouteHelper.unregisterMediaRouteProvider(castContext);
        castContext.dispose();
        super.onDestroy();
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

    @Override
    public void onDeviceAvailable(CastDevice castDevice, String s, MediaRouteStateChangeListener mediaRouteStateChangeListener) {
        this.castDevice = castDevice;
        routeStateListener = mediaRouteStateChangeListener;
    }

    @Override
    public void onSetVolume(double v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUpdateVolume(double v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
