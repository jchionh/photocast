package net.jzapper.android.photocast.activity;

import android.app.Activity;
import android.os.Bundle;
import com.google.cast.CastDevice;
import net.jzapper.android.photocast.R;
import net.jzapper.android.photocast.app.PhotocastApp;
import net.jzapper.android.photocast.dialog.DeviceSelectionDialog;

public class PhotocastActivity extends Activity {

    private DeviceSelectionDialog deviceSelectionDialog;

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
}
