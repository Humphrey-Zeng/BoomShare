package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.adapter.ChildAdapter;
import com.humphrey.boomshare.bean.NoteDetailInfo;
import com.humphrey.boomshare.bean.NoteInfo;
import com.humphrey.boomshare.database.NotesDetailInfoDAO;
import com.humphrey.boomshare.database.NotesInfoDAO;
import com.humphrey.boomshare.utils.SharedPreferencesUtils;
import com.lidroid.xutils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            finish();
        }
    };

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
                showNotesInfoDialog();
                break;
            case R.id.btn_select_picture_cancel:
                finish();
                break;
        }
    }

    private void showNotesInfoDialog() {
        final NotesInfoDAO dao = new NotesInfoDAO(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_notes_info, null);

        dialog.setView(view);


        Button btnDialogOK = (Button) view.findViewById(R.id.btn_dialog_notes_info_ok);
        Button btnDialogCancel = (Button) view.findViewById(R.id.btn_dialog_notes_info_cancel);
        final EditText etNotesName = (EditText) view.findViewById(R.id.et_dialog_notes_name);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_dialog_notes_type);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notesName = etNotesName.getText().toString();
                if (TextUtils.isEmpty(notesName)) {
                    Toast.makeText(SelectPictureActivity.this, "笔记名称不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else if (dao.findName(notesName)) {
                    Toast.makeText(SelectPictureActivity.this, "该名称已存在", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    NoteInfo info = new NoteInfo();
                    info.setName(notesName);
                    info.setType((String) spinner.getSelectedItem());
                    dao.add(info);
                    dialog.dismiss();
                    addNote(notesName);
                }
            }
        });

        dialog.show();
    }

    private void addNote(String noteName) {
        final String name = noteName;
        final NotesDetailInfoDAO dao = new NotesDetailInfoDAO(this);

        new Thread(){
            @Override
            public void run() {

                for (int i = 0; i < selectList.size(); i++) {
                    NoteDetailInfo info = new NoteDetailInfo();
                    info.setName(name);
                    info.setIndex(i + 1);
                    String path = selectList.get(i);
                    
//                    BitmapUtils bitmapUtils = new BitmapUtils(SelectPictureActivity.this);
//                    Bitmap bitmap = bitmapUtils.getBitmapFromMemCache(path, null);


//                    ByteArrayOutputStream os = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
//                    info.setPicture(os.toByteArray());
//                    System.out.println(os.toByteArray());
//
//                    dao.add(info);
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
