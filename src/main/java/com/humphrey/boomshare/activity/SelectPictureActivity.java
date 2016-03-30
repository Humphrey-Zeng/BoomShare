package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.adapter.ChildAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectPictureActivity extends Activity implements View.OnClickListener {

    private GridView mGridView;
    private List<String> list;
    private List<String> selectList;
    private ChildAdapter adapter;
    private static int mCount;
    private Button tvSelectPictureOK;
    private Button tvSelectPictureCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        mGridView = (GridView) findViewById(R.id.gv_select_photo);
        list = getIntent().getStringArrayListExtra("data");
        RelativeLayout rlSelectPicture = (RelativeLayout) findViewById(R.id.rl_select_picture);
        tvSelectPictureOK = (Button) findViewById(R.id.btn_select_picture_OK);
        tvSelectPictureCancel = (Button) findViewById(R.id.btn_select_picture_cancel);

        adapter = new ChildAdapter(this, list, mGridView, rlSelectPicture, tvSelectPictureOK);
        mGridView.setAdapter(adapter);
        mCount = 0;

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectPictureActivity.this, PhotoViewActivity.class);
                intent.putExtra("path", list.get(position));
                System.out.println(list.get(position));
                startActivity(intent);
            }
        });

        tvSelectPictureOK.setOnClickListener(this);
        tvSelectPictureCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_picture_OK:
                List<Integer> listSelectedItems = adapter.getSelectItems();
                selectList = new ArrayList<String>();
                for (int itemPosition : listSelectedItems) {
                    selectList.add(list.get(itemPosition));
                }
                break;
        }
        for (int i = 0; i < selectList.size(); i++)
            System.out.println(selectList.get(i));
        finish();
    }
}
