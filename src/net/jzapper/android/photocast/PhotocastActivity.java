package net.jzapper.android.photocast;

import android.app.Activity;
import android.os.Bundle;
import com.google.cast.CastDevice;

public class PhotocastActivity extends Activity {

    private CastDevice castDevice;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
