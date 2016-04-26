package com.humphrey.boomshare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.activity.CoverChangeActivity;
import com.humphrey.boomshare.activity.NoteViewActivity;
import com.humphrey.boomshare.adapter.DragAdapter;
import com.humphrey.boomshare.bean.NoteInfo;
import com.humphrey.boomshare.database.NotesInfoDAO;
import com.humphrey.boomshare.view.DragGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.humphrey.boomshare.utils.GlobalUtils.SELECT_COVER;
import static com.humphrey.boomshare.utils.GlobalUtils.getNoteCoverFolderPath;
import static com.humphrey.boomshare.utils.GlobalUtils.getNotePicturesFolderPath;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class NotesFragment extends BaseFragment implements View.OnClickListener {

    private List<NoteInfo> notesInfoList;
    private DragGridView gvNotesShelf;
    public DragAdapter adapter;
    private LinearLayout llNoteEdit;
    private Button btnNoteDelete;
    private Button btnNoteChangeCover;
    private Button btnNoteCancel;
    public boolean isEdited;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter = new DragAdapter(mActivity, notesInfoList, gvNotesShelf);
            gvNotesShelf.setAdapter(adapter);

            gvNotesShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mActivity, NoteViewActivity.class);
                    intent.putExtra("note_name", notesInfoList.get(position).getName());
                    startActivity(intent);
                }
            });
        }
    };

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_notes, null);

        gvNotesShelf = (DragGridView) view.findViewById(R.id.gv_notes_shelf);
        llNoteEdit = (LinearLayout) view.findViewById(R.id.ll_note_edit);
        btnNoteDelete = (Button) view.findViewById(R.id.btn_note_delete);
        btnNoteChangeCover = (Button) view.findViewById(R.id.btn_note_change_cover);
        btnNoteCancel = (Button) view.findViewById(R.id.btn_note_cancel);

        btnNoteDelete.setOnClickListener(this);
        btnNoteChangeCover.setOnClickListener(this);
        btnNoteCancel.setOnClickListener(this);

        mToolBar.setTitle("笔记");
        mActivity.isOptionMenuShown = true;
        mActivity.onPrepareOptionsMenu(mMenu);

        isEdited = false;

        return view;
    }

    @Override
    public void initData() {

        new Thread() {
            @Override
            public void run() {

                NotesInfoDAO dao = new NotesInfoDAO(mActivity);
                notesInfoList = dao.findAll();

                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    public void editNotes() {
        isEdited = true;
        adapter.notifyDataSetChanged();
        llNoteEdit.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_note_delete:
                deleteSelectedNote(adapter.selectNoteList);
                break;
            case R.id.btn_note_change_cover:
                Intent intent = new Intent(mActivity, CoverChangeActivity.class);
                intent.putStringArrayListExtra("coverList", (ArrayList<String>) adapter
                        .selectNoteList);
                startActivity(intent);
                break;
            case R.id.btn_note_cancel:
                break;
        }
        adapter.selectNoteList.clear();
        isEdited = false;
        llNoteEdit.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void deleteSelectedNote(List<String> list) {
        NotesInfoDAO dao = new NotesInfoDAO(mActivity);

        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            NoteInfo info = new NoteInfo();
            info.setName(name);
            dao.delete(info);
            deleteNotePictureInNative(name);
            deleteNoteCoverInNative(name);
        }

        initData();
    }

    private void deleteNotePictureInNative(String name) {
        String path = getNotePicturesFolderPath(name);
        File parentFile = new File(path);
        final File[] childFiles = parentFile.listFiles();

        for (int i = 0; i < childFiles.length; i++) {
            childFiles[i].delete();
        }
        parentFile.delete();
    }

    private void deleteNoteCoverInNative(String name) {
        String path = getNoteCoverFolderPath();
        File coverFile = new File(path, name);
        if (coverFile.exists()) {
            coverFile.delete();
        }
    }

}
