package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.adapter.GroupAdapter;
import com.humphrey.boomshare.bean.ImageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Humphrey on 2016/3/25.
 */
public class SelectFolderActivity extends Activity implements View.OnClickListener {

    private HashMap<String, List<String>> mGroupMap = new HashMap<String, List<String>>();
    private List<ImageBean> list = new ArrayList<ImageBean>();

    private final static int SCAN_OK = 1;
    private ProgressDialog mProgressDialog;
    private GroupAdapter adapter;
    private GridView mGroupGridView;
    private Button btnCancel;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_OK:
                    mProgressDialog.dismiss();
                    adapter = new GroupAdapter(SelectFolderActivity.this, list = subGroupOfImage
                            (mGroupMap), mGroupGridView);
                    mGroupGridView.setAdapter(adapter);
                    break;
            }
        }
    };

    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGroupMap) {

        if (mGroupMap.size() == 0) {
            return null;
        }

        List<ImageBean> list = new ArrayList<ImageBean>();
        Iterator<Map.Entry<String, List<String>>> it = mGroupMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean imageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            imageBean.setFolderName(key);
            imageBean.setImageCount(value.size());
            imageBean.setTopImagePath(value.get(0));

            list.add(imageBean);
        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder);

        mGroupGridView = (GridView) findViewById(R.id.gv_select_picture);
        btnCancel = (Button) findViewById(R.id.btn_cancel_select_folder);

        btnCancel.setOnClickListener(this);
        getImages();

        mGroupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> childList = mGroupMap.get(list.get(position).getFolderName());
                Intent intent = new Intent(SelectFolderActivity.this, SelectPictureActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                startActivity(intent);
            }
        });
    }

    private void getImages() {
        mProgressDialog = ProgressDialog.show(this, null, "正在加载");
        new Thread() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = SelectFolderActivity.this.getContentResolver();

                Cursor cursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media
                        .MIME_TYPE + " = ? or " + MediaStore.Images.Media.MIME_TYPE + " = ?", new
                        String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                if (cursor == null) {
                    return;
                }

                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media
                            .DATA));
                    String parentName = new File(path).getParentFile().getName();

                    if (!mGroupMap.containsKey(parentName)) {
                        List<String> childList = new ArrayList<String>();
                        childList.add(path);
                        mGroupMap.put(parentName, childList);
                    } else {
                        mGroupMap.get(parentName).add(path);
                    }
                    // System.out.println(parentName);
                }
                mHandler.sendEmptyMessage(SCAN_OK);
                cursor.close();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_select_folder:
                finish();
                break;
        }
    }

}
