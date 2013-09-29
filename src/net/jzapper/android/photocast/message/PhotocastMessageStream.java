package net.jzapper.android.photocast.message;

import com.google.cast.MessageStream;
import net.jzapper.android.photocast.chromecast.CastInfo;
import net.jzapper.android.photocast.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * User: jchionh
 * Date: 9/30/13
 * Time: 1:22 AM
 */
public class PhotocastMessageStream extends MessageStream {
    private static final String TAG = PhotocastMessageStream.class.getSimpleName();

    private static final String KEY_ACTION = "action";
    private static final String KEY_EVENT = "event";
    private static final String KEY_DATA = "data";


    public PhotocastMessageStream() throws IllegalArgumentException {
        super(CastInfo.CAST_NAMESPACE);
    }

    public final void sendTestMessage() {
        JSONObject payload = new JSONObject();
        try {
            payload.put(KEY_ACTION, "test_action");
            payload.put(KEY_DATA, "test_data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            sendMessage(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(JSONObject message) {
        String event = message.optString(KEY_EVENT);
        Logger.d(TAG, "recv: %s", message.toString());

        /*
        if (EVENT_ALARM.equals(event)) {
            // The alarm has fired...make lots of noise!
        } else {
            // Unrecognized message; ignore and/or log an error.
        }
        */
    }
}
