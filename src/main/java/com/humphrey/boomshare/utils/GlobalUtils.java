package com.humphrey.boomshare.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Humphrey on 2016/4/1.
 */
public class GlobalUtils {
    public static String getNotePicturesFolderPath(String folderName) {

        String path = "";

        boolean isSDCardExisted = Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED);

        if (isSDCardExisted){
            path = Environment.getExternalStorageDirectory().toString();
        }

        path = path + "/" + "Boom笔记" + "/" + folderName;

        return path;
    }
}
