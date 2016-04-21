package com.humphrey.boomshare.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Humphrey on 2016/4/21.
 */
public class CacheUtils {
    private LruCache<String, Bitmap> mMemCache;
    private static CacheUtils mInstance = new CacheUtils();
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    CacheUtils(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mMemCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public static CacheUtils getInstance(){
        return mInstance;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapByPathFromCache(String path){
        return mMemCache.get(path);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmapWithPathToCache(String path, Bitmap bitmap){
        if (getBitmapByPathFromCache(path) == null && bitmap != null){
            mMemCache.put(path, bitmap);
        }
    }
}
