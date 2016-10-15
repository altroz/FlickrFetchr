package com.example.ramrooter.flickrfetchr;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    protected Fragment createFragment(){
        return new PhotoGalleryFragment().newInstance();
    }

}
