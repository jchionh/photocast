package net.jzapper.android.photocast.dialog;

import android.app.AlertDialog;
import android.content.Context;
import com.google.cast.CastDevice;
import com.google.cast.CastDeviceAdapter;
import com.google.cast.DeviceManager;

/**
 * User: jchionh
 * Date: 8/26/13
 * Time: 7:24 PM
 *
 * This handles the device selection
 *
 */
public class DeviceSelectionDialog {

    public interface DeviceSelectionListener {
        /**
         * Called if a device is selected.
         *
         * @param dialog the selection dialog where the device is selected
         */
        public void onSelected(DeviceSelectionDialog dialog);

        /**
         * Called if the selection is cancelled.
         *
         * @param dialog the dialog that was cancelled
         */
        public void onCancelled(DeviceSelectionDialog dialog);
    }

    private AlertDialog alertDialog;
    private Context context;
    private DeviceManager deviceManager;
    private CastDeviceAdapter castDeviceAdapter;
    private CastDevice castDevice;
    private DeviceSelectionListener deviceSelectionListener;



}
