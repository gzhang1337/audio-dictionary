package com.dict.audio.audio_dictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// implement helper I've been use this guide
// https://github.com/codepath/android_guides/wiki/Local-Databases-with-SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 3;

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + User.Entry.TABLE_NAME +
                "(" +
                    User.Entry._ID + " INTEGER PRIMARY KEY," +
                    User.Entry.KEY_NAME + " TEXT," +
                    User.Entry.KEY_PASS + " TEXT," +
                    User.Entry.KEY_TOKENS + " INTEGER" +
                ")";

        String CREATE_SUBMISSIONS_TABLE = "CREATE TABLE " + Submission.Entry.TABLE_NAME +
                "(" +
                    Submission.Entry._ID + " INTEGER PRIMARY KEY," +
                    Submission.Entry.KEY_UID + " INTEGER," +
                    Submission.Entry.KEY_WORD + " TEXT," +
                    Submission.Entry.KEY_FIDS + " TEXT," +
                    Submission.Entry.KEY_AUDIO + " TEXT," +
                    Submission.Entry.KEY_TIMESTAMP + " TIME" +
                ")";

        String CREATE_FEEDBACKS_TABLE = "CREATE TABLE " + Feedback.Entry.TABLE_NAME +
                "(" +
                    Feedback.Entry._ID + " INTEGER PRIMARY KEY," +
                    Feedback.Entry.KEY_SID + " INTEGER," +
                    Feedback.Entry.KEY_UID + " INTEGER," +
                    Feedback.Entry.KEY_TEXT + " TEXT," +
                    Feedback.Entry.KEY_WORD + " TEXT," +
                    Feedback.Entry.KEY_TIMESTAMP + " TIME" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SUBMISSIONS_TABLE);
        db.execSQL(CREATE_FEEDBACKS_TABLE);
    }

    @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // recreate tables
            db.execSQL("DROP TABLE IF EXISTS " + User.Entry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Submission.Entry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Feedback.Entry.TABLE_NAME);
            onCreate(db);
        }
    }
    public User getUserByUid( int uid) {
        SQLiteDatabase db = getWritableDatabase();
        String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s=%d",
                User.Entry.TABLE_NAME,User.Entry._ID,uid);
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        User result = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = new User(cursor.getInt(cursor.getColumnIndex(User.Entry._ID)),
                            cursor.getString(cursor.getColumnIndex(User.Entry.KEY_NAME)),
                            cursor.getString(cursor.getColumnIndex(User.Entry.KEY_PASS)),
                            cursor.getInt(cursor.getColumnIndex(User.Entry.KEY_TOKENS)));
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

    public User getUserByNamePass(String name, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s=%s AND WHERE %s=%s",
                User.Entry.TABLE_NAME,User.Entry.KEY_NAME,name,User.Entry.KEY_PASS,pass);
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);
        User result = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    result = new User(cursor.getInt(cursor.getColumnIndex(User.Entry._ID)),
                            cursor.getString(cursor.getColumnIndex(User.Entry.KEY_NAME)),
                            cursor.getString(cursor.getColumnIndex(User.Entry.KEY_PASS)),
                            cursor.getInt(cursor.getColumnIndex(User.Entry.KEY_TOKENS)));
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

    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(User.Entry.KEY_NAME, user.name);
            values.put(User.Entry.KEY_PASS, user.pass);
            values.put(User.Entry.KEY_TOKENS,user.tokens);
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(User.Entry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

}
