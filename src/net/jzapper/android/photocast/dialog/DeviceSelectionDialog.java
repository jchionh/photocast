package net.jzapper.android.photocast.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.google.cast.CastDevice;
import com.google.cast.CastDeviceAdapter;
import com.google.cast.DeviceManager;
import net.jzapper.android.photocast.R;
import net.jzapper.android.photocast.app.PhotocastApp;

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


    // ctor
    public DeviceSelectionDialog(Context appContext) {
        context = appContext;
        castDeviceAdapter = new CastDeviceAdapter(context);
        deviceManager = new DeviceManager(PhotocastApp.getInstance().getCastContext());

        deviceManager.addListener(new DeviceManager.Listener() {
            @Override
            public void onScanStateChanged(int state) {
                if (state == DeviceManager.SCAN_SUSPENDED_NETWORK_ERROR) {
                    new AlertDialog.Builder(context)
                            .setMessage(R.string.scan_failed_network_error)
                            .setPositiveButton(R.string.ok, null)
                            .create()
                            .show();
                }
            }

            @Override
            public void onDeviceOnline(CastDevice castDevice) {
                castDeviceAdapter.add(castDevice);
            }

            @Override
            public void onDeviceOffline(CastDevice castDevice) {
                castDeviceAdapter.remove(castDevice);
            }
        });
    }


    /**
     * Initializes and displays the dialog being used for device selection.
     *
     * @throws IllegalStateException if the dialog is already being displayed
     */
    public void show() throws IllegalStateException {
        if (alertDialog != null) {
            throw new IllegalStateException("Can't show dialog more than once");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.device_discover_title);
        DialogListener listener = new DialogListener();
        builder.setAdapter(castDeviceAdapter, listener);
        alertDialog = builder.create();
        alertDialog.setOnDismissListener(listener);
        alertDialog.setOnCancelListener(listener);
        deviceManager.startScan();
        alertDialog.show();
    }

    /**
     * Dismisses the dialog if it is being displayed.
     */
    public void dismiss() {
        deviceSelectionListener = null;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /**
     * Returns the device selected from the dialog, or {@code null} if none is selected.
     */
    public CastDevice selectedDevice() {
        return castDevice;
    }

    public void setListener(DeviceSelectionListener listener) {
        deviceSelectionListener = listener;
    }

    /**
     * A Listener class attached to this object's displayed dialog, which handles dismiss, cancel,
     * and click events by modifying instance variables accordingly.
     */
    private class DialogListener implements DialogInterface.OnClickListener,
            DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            deviceManager.stopScan();
            if (deviceSelectionListener != null) {
                if (castDevice != null) {
                    deviceSelectionListener.onSelected(DeviceSelectionDialog.this);
                } else {
                    deviceSelectionListener.onCancelled(DeviceSelectionDialog.this);
                }
            }
            alertDialog = null;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            castDevice = null;
        }

        @Override
        public void onClick(DialogInterface dialog, int index) {
            CastDevice selected = castDeviceAdapter.getItem(index);
            if (selected != null) {
                castDevice = selected;
            }
        }
    }
}
