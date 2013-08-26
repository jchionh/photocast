package net.jzapper.android.photocast;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PhotocastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocast);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photocast, menu);
        return true;
    }
    
}
