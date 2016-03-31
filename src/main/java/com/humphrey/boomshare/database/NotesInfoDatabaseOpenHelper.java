package com.humphrey.boomshare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NotesInfoDatabaseOpenHelper extends SQLiteOpenHelper {

    public NotesInfoDatabaseOpenHelper(Context context) {
        super(context, "NotesInfos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notesinfos(_id integer primary key autoincrement, name varchar" +
                "(20), " +
                "type varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
