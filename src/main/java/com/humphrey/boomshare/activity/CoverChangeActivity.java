package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.humphrey.boomshare.R;

import java.util.ArrayList;

public class CoverChangeActivity extends Activity {

    private ListView lvCoverChange;
    private ArrayList<String> list;
    private CoverChangeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_change);

        lvCoverChange = (ListView) findViewById(R.id.lv_cover_change);

        list = getIntent().getStringArrayListExtra("coverList");

        adapter = new CoverChangeAdapter();
        lvCoverChange.setAdapter(adapter);
    }

    private class CoverChangeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(CoverChangeActivity.this, R.layout.item_cover_change,
                        null);
                viewHolder = new ViewHolder();
                viewHolder.ivOldCover = (ImageView) convertView.findViewById(R.id.iv_cover_old);
                viewHolder.ivNewCover = (ImageView) convertView.findViewById(R.id.iv_cover_new);
                viewHolder.tvCoverName = (TextView) convertView.findViewById(R.id
                        .tv_item_cover_name);
                viewHolder.btnSelectPicture = (Button) convertView.findViewById(R.id
                        .btn_select_picture);
                viewHolder.btnDefaultPicture = (Button) convertView.findViewById(R.id
                        .btn_default_picture);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvCoverName.setText(list.get(position));
            viewHolder.ivOldCover.setImageResource(R.drawable.cover);
            viewHolder.ivNewCover.setImageResource(R.drawable.cover);

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnDefaultPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder.ivNewCover.setImageResource(R.drawable.cover);
                }
            });

            return convertView;
        }
    }

    private static class ViewHolder {
        private ImageView ivOldCover;
        private ImageView ivNewCover;
        private TextView tvCoverName;
        private Button btnSelectPicture;
        private Button btnDefaultPicture;
    }
}
