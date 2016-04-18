package com.humphrey.boomshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.activity.HomeActivity;
import com.humphrey.boomshare.bean.NoteInfo;
import com.humphrey.boomshare.database.NotesInfoDAO;
import com.humphrey.boomshare.view.DragGridViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Humphrey on 2016/4/18.
 */
public class DragAdapter extends BaseAdapter implements DragGridViewInterface {

    private List<NoteInfo> list;
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private LayoutInflater mInflater;
    private int mHidePosition = -1;
    private HomeActivity mActivity;
    public List<String> selectNoteList;

    public DragAdapter(Context context, List<NoteInfo> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        mActivity = (HomeActivity) context;

        for (int i = 0; i < list.size(); i++){
            map.put(list.get(i).getName(), list.get(i).getPicIndex());
        }
        selectNoteList = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NoteInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_notes_shelf, null);

        ImageView ivNoteCover = (ImageView) convertView.findViewById(R.id.iv_notes_cover);
        TextView tvNoteName = (TextView) convertView.findViewById(R.id.tv_notes_name);
        CheckBox cbNoteSelect = (CheckBox) convertView.findViewById(R.id.cb_note_select);

        tvNoteName.setText(list.get(position).getName());
        ivNoteCover.setBackgroundResource(R.drawable.cover);
        if (mActivity.getNotesFragment().isEdited == false){
            cbNoteSelect.setVisibility(View.GONE);
        }else{
            cbNoteSelect.setVisibility(View.VISIBLE);
            cbNoteSelect.setFocusable(true);

            cbNoteSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selectNoteList.add(list.get(position).getName());
                    }else{
                        selectNoteList.remove(list.get(position).getName());
                    }
                }
            });
        }

        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        NoteInfo temp = list.get(oldPosition);
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }

        list.set(newPosition, temp);
    }

    @Override
    public void setHideItem(int hidePosition) {
        NotesInfoDAO dao = new NotesInfoDAO(mActivity);

        this.mHidePosition = hidePosition;
        notifyDataSetChanged();

        for (int i = 0; i < list.size(); i++){
            if (i != map.get(list.get(i).getName())){
                NoteInfo preInfo = list.get(i);
                NoteInfo updateInfo = list.get(i);

                updateInfo.setPicIndex(i);
                dao.update(preInfo, updateInfo);

                map.remove(list.get(i).getName());
                map.put(list.get(i).getName(), i);
            }
        }
    }
}
