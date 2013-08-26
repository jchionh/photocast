package net.jzapper.android.photocast.activity;

import android.app.Activity;
import android.os.Bundle;
import com.google.cast.CastDevice;
import net.jzapper.android.photocast.R;

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
