package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.humphrey.boomshare.R;
import com.lidroid.xutils.BitmapUtils;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends Activity {

    private PhotoView pvMyPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        pvMyPhoto = (PhotoView) findViewById(R.id.pv_photo_view);
        String path = getIntent().getStringExtra("path");


        BitmapUtils bitmapUtils = new BitmapUtils(this);

        bitmapUtils.display(pvMyPhoto, path);
    }
}
