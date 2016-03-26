package com.humphrey.boomshare.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.utils.NativeImageLoader;
import com.humphrey.boomshare.view.MyImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Humphrey on 2016/3/26.
 */
public class ChildAdapter extends BaseAdapter {
    private Point mPoint = new Point(0, 0);

    private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
    private GridView mGridView;
    private List<String> list;
    protected LayoutInflater mInflater;


    public ChildAdapter(Context context, List<String> list, GridView mGridView) {
        this.list = list;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
    }

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
        final ViewHolder viewHolder;
        String path = list.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_photo_select, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.photo_image);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb_photo_select);

            viewHolder.mImageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {
                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.mImageView.setTag(path);
        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mSelectMap.containsKey(position) || !mSelectMap.get(position)) {
                    addAnimation(viewHolder.mCheckBox);
                }
                mSelectMap.put(position, isChecked);
            }
        });

        viewHolder.mCheckBox.setChecked(mSelectMap.containsKey(position) ? mSelectMap.get
                (position) : false);

        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new
                NativeImageLoader.NativeImageCallBack() {

                    @Override
                    public void onImageLoader(Bitmap bitmap, String path) {
                        ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
                        if (bitmap != null && mImageView != null) {
                            mImageView.setImageBitmap(bitmap);
                        }
                    }
                });

        if (bitmap != null) {
            viewHolder.mImageView.setImageBitmap(bitmap);
        } else {
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }

  /*  private void addAnimation(View view) {
        float[] values = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f,
                1.2f, 1.15f, 1.1f, 1.0f};
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, scaleX, values), ObjectAnimator.ofFloat
                (view, scaleY, values));
        set.setDuration(150);

        set.start();
    }*/

    public static class ViewHolder {
        public MyImageView mImageView;
        public CheckBox mCheckBox;
    }

    public List<Integer> getSelectItems() {
        List<Integer> list = new ArrayList<Integer>();

        for (Iterator<Map.Entry<Integer, Boolean>> it = mSelectMap.entrySet().iterator(); it
                .hasNext(); ) {
            Map.Entry<Integer, Boolean> entry = it.next();
            if (entry.getValue()) {
                list.add(entry.getKey());
            }
        }

        return list;
    }


}
