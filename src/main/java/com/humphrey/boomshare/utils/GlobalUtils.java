package com.humphrey.boomshare.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Humphrey on 2016/4/1.
 */
public class GlobalUtils {

    public static final int SELECT_PICTURE = 0;
    public static final int SELECT_COVER = 1;

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

    public static String getNoteCoverFolderPath(){

        String path = getNotePicturesFolderPath("Boom笔记封面");

        return path;
    }
}
