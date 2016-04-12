package com.humphrey.boomshare.fragment;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.activity.NoteViewActivity;
import com.humphrey.boomshare.bean.NoteInfo;
import com.humphrey.boomshare.database.NotesInfoDAO;

import java.io.File;
import java.util.List;

import static com.humphrey.boomshare.utils.GlobalUtils.getNotePicturesFolderPath;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class NotesFragment extends BaseFragment {

    private List<NoteInfo> notesInfoList;
    private GridView gvNotesShelf;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gvNotesShelf.setAdapter(new NotesShelfAdapter());

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

        gvNotesShelf = (GridView) view.findViewById(R.id.gv_notes_shelf);

        mToolBar.setTitle("笔记");
        mActivity.isOptionMenuShown = true;
        mActivity.onPrepareOptionsMenu(mMenu);

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

    class NotesShelfAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return notesInfoList.size();
        }

        @Override
        public NoteInfo getItem(int position) {
            return notesInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder;

            if (convertView == null) {
                viewholder = new ViewHolder();
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_notes_shelf,
                        null);

                viewholder.tvNoteName = (TextView) convertView.findViewById(R.id.tv_notes_name);
                viewholder.ivNoteCover = (ImageView) convertView.findViewById(R.id.iv_notes_cover);

                convertView.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }


            viewholder.tvNoteName.setText(getItem(position).getName());
            viewholder.ivNoteCover.setBackgroundResource(R.drawable.cover);

            return convertView;
        }
    }

    static class ViewHolder {
        private TextView tvNoteName;
        private ImageView ivNoteCover;
    }


}
