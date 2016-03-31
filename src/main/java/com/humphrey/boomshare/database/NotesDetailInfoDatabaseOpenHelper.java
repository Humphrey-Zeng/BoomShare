package com.humphrey.boomshare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NotesDetailInfoDatabaseOpenHelper extends SQLiteOpenHelper {

    public NotesDetailInfoDatabaseOpenHelper(Context context) {
        super(context, "NotesDetailInfos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notesdetailinfos(_id integer primary key autoincrement, name " +
                "varchar(20), picIndex integer, picture blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
