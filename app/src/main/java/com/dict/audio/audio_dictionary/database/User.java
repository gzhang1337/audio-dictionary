package com.dict.audio.audio_dictionary.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

public class User {
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String KEY_NAME = "name";
        public static final String KEY_PASS = "pass";
        public static final String KEY_TOKENS = "tokens";
    }

    public int uid;
    public String name;
    public String pass;
    public int tokens;

    public User(int uid, String name, String pass, int tokens) {
        this.uid = uid;
        this.name = name;
        this.pass = pass;
        this.tokens = tokens;
    }

    public User getUserByUid(SQLiteDatabase db, int uid) {
        String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s=%d",
                Entry.TABLE_NAME,Entry._ID,uid);
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        User result = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = new User(cursor.getInt(cursor.getColumnIndex(Entry._ID)),
                            cursor.getString(cursor.getColumnIndex(Entry.KEY_NAME)),
                                    cursor.getString(cursor.getColumnIndex(Entry.KEY_PASS)),
                                    cursor.getInt(cursor.getColumnIndex(Entry.KEY_TOKENS)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public User getUserByNamePass(SQLiteDatabase db, String name, String pass) {
        String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s=%s AND WHERE %s=%s",
                Entry.TABLE_NAME,Entry.KEY_NAME,name,Entry.KEY_PASS,pass);
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);
        User result = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = new User(cursor.getInt(cursor.getColumnIndex(Entry._ID)),
                            cursor.getString(cursor.getColumnIndex(Entry.KEY_NAME)),
                            cursor.getString(cursor.getColumnIndex(Entry.KEY_PASS)),
                            cursor.getInt(cursor.getColumnIndex(Entry.KEY_TOKENS)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void addUser(SQLiteDatabase db,User user) {
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(Entry.KEY_NAME, user.name);
            values.put(Entry.KEY_PASS, user.pass);
            values.put(Entry.KEY_TOKENS,user.tokens);
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(Entry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
