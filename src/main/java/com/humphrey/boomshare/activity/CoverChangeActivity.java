package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.utils.NativeImageLoader;
import com.humphrey.boomshare.view.MyImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.humphrey.boomshare.utils.GlobalUtils.SELECT_COVER;
import static com.humphrey.boomshare.utils.GlobalUtils.getNoteCoverFolderPath;

public class CoverChangeActivity extends Activity implements View.OnClickListener {

    private ListView lvCoverChange;
    private ArrayList<String> list;
    private CoverChangeAdapter adapter;
    private Button btnCoverChangeCancel;
    private Button btnCoverChangeOK;
    private static HashMap<Integer, String> mCoverNewMap;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
            NativeImageLoader.getInstance().clearCacheInNative();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_change);

        lvCoverChange = (ListView) findViewById(R.id.lv_cover_change);
        btnCoverChangeCancel = (Button) findViewById(R.id.btn_cover_change_cancel);
        btnCoverChangeOK = (Button) findViewById(R.id.btn_cover_change_ok);

        btnCoverChangeOK.setOnClickListener(this);
        btnCoverChangeCancel.setOnClickListener(this);

        list = getIntent().getStringArrayListExtra("coverList");

        mCoverNewMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String bitmapPath = getNoteCoverFolderPath() + "/" + list.get(i);
            mCoverNewMap.put(i, bitmapPath);
        }

        adapter = new CoverChangeAdapter();
        lvCoverChange.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cover_change_cancel:
                finish();
                break;
            case R.id.btn_cover_change_ok:
                changeSelectCover();
                break;
        }
    }

    private void changeSelectCover() {
        Set<Map.Entry<Integer, String>> entrySet = mCoverNewMap.entrySet();

        final int size = entrySet.size();
        int mCount = 0;

        for (Map.Entry<Integer, String> entry :
                entrySet) {
            final Integer position = entry.getKey();
            final String path = entry.getValue();
            mCount++;
            if (path != null) {
                final int finalMCount = mCount;
                new Thread() {
                    @Override
                    public void run() {
                        String coverPath = getNoteCoverFolderPath();
                        File coverFile = new File(coverPath, list.get(position) + "");

                        if (coverFile.exists()) {
                            coverFile.delete();
                        }

                        FileOutputStream coverFileOutputStream;
                        try {
                            coverFileOutputStream = new FileOutputStream(coverFile);
                            Bitmap mBitmap = Picasso.with(CoverChangeActivity.this).load(new File
                                    (path)).get();
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

                            coverFileOutputStream.write(os.toByteArray());

                            if (!mBitmap.isRecycled()) {
                                mBitmap.recycle();
                                System.gc();
                            }

                            coverFileOutputStream.close();

                            if (finalMCount >= size){
                                mHandler.sendEmptyMessage(0);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                String coverPath = getNoteCoverFolderPath();
                File coverFile = new File(coverPath, list.get(position) + "");
                if (coverFile.exists()) {
                    coverFile.delete();
                }
                if (mCount >= size){
                    mHandler.sendEmptyMessage(0);
                }
            }
        }
    }

    private class CoverChangeAdapter extends BaseAdapter {

        private Point mPoint = new Point(0, 0);
        private Point mNewPoint = new Point(0, 0);

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(CoverChangeActivity.this, R.layout.item_cover_change,
                        null);
                viewHolder = new ViewHolder();
                viewHolder.ivOldCover = (MyImageView) convertView.findViewById(R.id.iv_cover_old);
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

            viewHolder.ivOldCover.setOnMeasureListener(new MyImageView.OnMeasureListener() {
                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            String bitmapPath = getNoteCoverFolderPath() + "/" + list.get(position);
            viewHolder.tvCoverName.setText(list.get(position));
            viewHolder.ivOldCover.setTag(bitmapPath);

            File coverFile = new File(bitmapPath);
            if (coverFile.exists()) {
                Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(bitmapPath,
                        mPoint, new NativeImageLoader.NativeImageCallBack() {

                            @Override
                            public void onImageLoader(Bitmap bitmap, String path) {
                                ImageView mImageView = (ImageView) lvCoverChange.findViewWithTag
                                        (path);
                                if (bitmap != null && mImageView != null) {
                                    mImageView.setImageBitmap(bitmap);
                                }
                            }
                        });
                if (bitmap != null) {
                    viewHolder.ivOldCover.setImageBitmap(bitmap);
                } else {
                    viewHolder.ivOldCover.setImageResource(R.drawable.cover);
                }
            } else {
                viewHolder.ivOldCover.setImageResource(R.drawable.cover);
            }

            if (!mCoverNewMap.containsKey(position)) {
//                viewHolder.ivOldCover.setDrawingCacheEnabled(true);
//                Bitmap coverNewBitmap = viewHolder.ivOldCover.getDrawingCache();
//                viewHolder.ivNewCover.setImageBitmap(coverNewBitmap);
                viewHolder.ivNewCover.setImageResource(R.drawable.cover);
            } else {
                mNewPoint.set(viewHolder.ivNewCover.getWidth(), viewHolder.ivNewCover.getHeight());
                final String coverNewPath = mCoverNewMap.get(position);
                if (coverNewPath != null) {
                    viewHolder.ivNewCover.setTag("New" + coverNewPath);
                    Bitmap coverNewBitmap = NativeImageLoader.getInstance().loadNativeImage
                            (coverNewPath, mNewPoint, new NativeImageLoader.NativeImageCallBack() {
                                @Override
                                public void onImageLoader(Bitmap bitmap, String path) {
                                    ImageView imageView = (ImageView) lvCoverChange
                                            .findViewWithTag("New" + path);
                                    if (bitmap != null && imageView != null) {
                                        imageView.setImageBitmap(bitmap);
                                    }
                                }
                            });
                    if (coverNewBitmap != null) {
                        viewHolder.ivNewCover.setImageBitmap(coverNewBitmap);
                    } else {
                        viewHolder.ivNewCover.setImageResource(R.drawable.cover);
                    }
                } else {
                    viewHolder.ivNewCover.setImageResource(R.drawable.cover);
                }
            }
            viewHolder.btnSelectPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CoverChangeActivity.this, SelectFolderActivity
                            .class);
                    intent.putExtra("selectType", SELECT_COVER);
                    startActivityForResult(intent, position);
                }
            });

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.btnDefaultPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder.ivNewCover.setImageResource(R.drawable.cover);
                    mCoverNewMap.put(position, null);
                }
            });

            return convertView;
        }
    }

    private static class ViewHolder {
        private MyImageView ivOldCover;
        private ImageView ivNewCover;
        private TextView tvCoverName;
        private Button btnSelectPicture;
        private Button btnDefaultPicture;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        final String coverPath = data.getStringExtra("coverPath");
        if (coverPath != null) {
            mCoverNewMap.put(requestCode, coverPath);
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }
    }
}
