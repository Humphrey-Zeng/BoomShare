package com.humphrey.boomshare.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.bean.NoteInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.humphrey.boomshare.utils.GlobalUtils.getNotePicturesFolderPath;

public class NoteViewActivity extends Activity {

    private String noteFolderName;
    private ViewPager vpNote;
    List<ImageView> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        vpNote = (ViewPager) findViewById(R.id.vp_note);

        noteFolderName = getIntent().getStringExtra("note_name");
        pictures = new ArrayList<ImageView>();

        showNotePictures();

        vpNote.setAdapter(new NoteAdapter());
    }

    private void showNotePictures() {

        String path = getNotePicturesFolderPath(noteFolderName);
        File parentFile = new File(path);
        final File[] childFiles = parentFile.listFiles();

        for (int i = 0; i < childFiles.length; i++) {
            try {
                InputStream in = new FileInputStream(childFiles[i]);
                byte[] addPicture = new byte[(int) childFiles[i].length()];
                in.read(addPicture);
                addBitmapPicture(addPicture);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void addBitmapPicture(byte[] addPicture) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(addPicture, 0, addPicture.length, options);

        int windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        int windowHeight = getWindowManager().getDefaultDisplay().getHeight();

        options.inSampleSize = calculateInSampleSize(options, windowWidth, windowHeight);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeByteArray(addPicture, 0, addPicture.length,
                options);

        ImageView imageView = new ImageView(NoteViewActivity.this);
        imageView.setImageBitmap(bitmap);

        PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

        pictures.add(imageView);

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqwidth, int reqheight) {

        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqheight || width > reqwidth) {
            int heightRatio = Math.round((float) height / (float) reqheight);
            int widthRatio = Math.round((float) width / (float) reqwidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    class NoteAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pictures.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(pictures.get(position));

            return pictures.get(position);
        }

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
