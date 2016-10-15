package com.example.ramrooter.flickrfetchr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Ram Rooter on 10/15/2016.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        FragmentManager fm =  getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_photo_gallery);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.activity_photo_gallery, fragment)
                    .commit();
        }
    }
}
