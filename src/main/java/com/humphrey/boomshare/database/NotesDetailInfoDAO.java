package com.humphrey.boomshare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.humphrey.boomshare.bean.NoteDetailInfo;
import com.humphrey.boomshare.bean.NoteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NotesDetailInfoDAO {
    private NotesDetailInfoDatabaseOpenHelper helper;
    private SQLiteDatabase db;

    public NotesDetailInfoDAO(Context context) {
        helper = new NotesDetailInfoDatabaseOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(NoteDetailInfo info) {
        ContentValues value = new ContentValues();
        value.put("name", info.getName());
        value.put("picIndex", info.getIndex());
        value.put("picture", info.getPicture());

        db.insert("notesdetailinfos", null, value);
    }

    public void delete(NoteDetailInfo info) {
        db.delete("notesdetailinfos", "name = ?", new String[]{info.getName()});
    }

    public void update(NoteDetailInfo preInfo, NoteDetailInfo updateInfo) {

        ContentValues values = new ContentValues();
        values.put("name", updateInfo.getName());
        values.put("picIndex", updateInfo.getIndex());
        values.put("picture", updateInfo.getPicture());

        db.update("notesdetailinfos", values, "name = ?", new String[]{preInfo.getName()});
    }

    public List<byte[]> findNotePicturesByName(String name) {
        Cursor cursor = db.query("notesdetailinfos", new String[]{"picture"}, "name = ?", new
                String[]{name}, null, null, "picIndex ASC", null);

        List<byte[]> list = new ArrayList<byte[]>();
        while (cursor.moveToNext()){
            byte[] picture = cursor.getBlob(0);
            list.add(picture);
        }

        cursor.close();

        return list;
    }
}
