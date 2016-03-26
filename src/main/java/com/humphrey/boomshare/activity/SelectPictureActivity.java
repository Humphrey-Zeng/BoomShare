package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.adapter.ChildAdapter;

import java.util.List;

public class SelectPictureActivity extends Activity {

    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        mGridView = (GridView) findViewById(R.id.gv_select_photo);
        list = getIntent().getStringArrayListExtra("data");

        adapter = new ChildAdapter(this, list, mGridView);
        mGridView.setAdapter(adapter);
    }


}
