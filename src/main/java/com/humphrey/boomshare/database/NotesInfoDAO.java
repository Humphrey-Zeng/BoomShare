package com.humphrey.boomshare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.humphrey.boomshare.bean.NoteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NotesInfoDAO {

    private NotesInfoDatabaseOpenHelper helper;
    private SQLiteDatabase db;

    public NotesInfoDAO(Context context) {
        helper = new NotesInfoDatabaseOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(NoteInfo info) {
        ContentValues value = new ContentValues();
        value.put("name", info.getName());
        value.put("type", info.getType());
        value.put("picIndex", 1);

        db.insert("notesinfos", null, value);
    }

    public void delete(NoteInfo info) {
        db.delete("notesinfos", "name = ?", new String[]{info.getName()});
    }

    public void update(NoteInfo preInfo, NoteInfo updateInfo) {

        ContentValues values = new ContentValues();
        values.put("name", updateInfo.getName());
        values.put("type", updateInfo.getType());
        values.put("picIndex", updateInfo.getPicIndex());

        db.update("notesinfos", values, "name = ?", new String[]{preInfo.getName()});
    }

    public List<NoteInfo> findAll() {
        Cursor cursor = db.query("notesinfos", new String[]{"name", "type", "picIndex"}, null, null, null, null,
                "picIndex ASC", null);

        List<NoteInfo> list = new ArrayList<NoteInfo>();
        while (cursor.moveToNext()) {
            NoteInfo info = new NoteInfo();
            String name = cursor.getString(0);
            String type = cursor.getString(1);
            info.setName(name);
            info.setType(type);
            list.add(info);
        }

        cursor.close();
        return list;
    }

    public boolean findName(String name) {
        Cursor cursor = db.query("notesinfos", new String[]{"name"}, "name = ?", new
                        String[]{name}, null, null,
                null, null);

        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }
}
