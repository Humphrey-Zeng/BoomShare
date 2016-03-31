package com.humphrey.boomshare;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.humphrey.boomshare.database.NotesInfoDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class TestNotesInfo extends AndroidTestCase{

    public void testadd(){
        NotesInfoDatabaseOpenHelper helper = new NotesInfoDatabaseOpenHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("name", "生物");
        value.put("type", 1);

        db.insert("notesinfos", null, value);
    }

    public void testdelete(){
        NotesInfoDatabaseOpenHelper helper = new NotesInfoDatabaseOpenHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete("notesinfos", "name = ?", new String[]{"生物"});
    }

    public void testupdate(){

        NotesInfoDatabaseOpenHelper helper = new NotesInfoDatabaseOpenHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", "化学");
        values.put("type", "3");

        db.update("notesinfos", values, "name = ?", new String[]{"化学"});
    }

    public void testfind(){
        NotesInfoDatabaseOpenHelper helper = new NotesInfoDatabaseOpenHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.query("notesinfos", new String[]{"name"}, null, null, null, null,
                null, null);

        List<String> list = new ArrayList<String>();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            list.add(name);
        }

        for (int i = 0;i < list.size();i++){
            System.out.println(list.get(i));
        }

        cursor.close();
    }
}
