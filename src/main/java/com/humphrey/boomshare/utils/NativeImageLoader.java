package com.humphrey.boomshare.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Humphrey on 2016/3/25.
 */
public class NativeImageLoader {
    private LruCache<String, Bitmap> mMemoryCache;
    private static NativeImageLoader mInstance = new NativeImageLoader();
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public NativeImageLoader() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public static NativeImageLoader getInstance() {
        return mInstance;
    }

    public Bitmap loadNativeImage(final String path, final NativeImageCallBack mCallBack) {
        return this.loadNativeImage(path, null, mCallBack);
    }

    public Bitmap loadNativeImage(final String path, final Point mPoint, final
    NativeImageCallBack mCallBack) {
        final Bitmap bitmap = getBitmapFromMemCache(path);

        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mCallBack != null) {
                    mCallBack.onImageLoader((Bitmap) msg.obj, path);
                }
            }
        };

        if (bitmap == null) {
            mImageThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap mBitmap = decodeThumBitmapForFile(path, mPoint == null ? 0 : mPoint.x,
                            mPoint == null ? 0 : mPoint.y);

                    Message msg = mHandler.obtainMessage();
                    msg.obj = mBitmap;
                    mHandler.sendMessage(msg);

                    addBitmapToMemoryCache(path, mBitmap);
                }
            });
        }
        return bitmap;
    }

    private Bitmap decodeThumBitmapForFile(String path, int viewWidth, int viewHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    private int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);

            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }

        return inSampleSize;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void clearCacheInNative(){
        mMemoryCache.evictAll();
    }

    public interface NativeImageCallBack {
        public void onImageLoader(Bitmap bitmap, String path);
    }
}
