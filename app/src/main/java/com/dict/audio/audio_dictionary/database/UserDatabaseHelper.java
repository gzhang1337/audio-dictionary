package com.dict.audio.audio_dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//implemenet helper I've been use this guide
// https://github.com/codepath/android_guides/wiki/Local-Databases-with-SQLiteOpenHelper
public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static UserDatabaseHelper mInstance;

    //Table Names
    final static String TABLE_USERS = "users";
    final static String TABLE_PRONOUN = "pronouns";
    // Database Info
    final private static String NAME = "user_db";
    final private static Integer VERSION = 1;
    // User Table Columns
    final static String KEY_USER_ID = "id";
    final static String KEY_USER_NAME = "userName";
    final static String KEY_PASSWORD = "password";

    // Pronoun table columns
    final static String KEY_PRO_ID = "id";
    final static String KEY_WORD_ID = "wordId";
    final static String KEY_PRONOUN_URL = "pronounUrl";

    final private Context mContext;
    /* private constructor to enforce singleton database,
       get an instance of database with getInstance method
    */
    private UserDatabaseHelper(Context context) {
        super(context, NAME,null,VERSION);
        this.mContext = context;
    }

    public static UserDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserDatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" + KEY_USER_ID + "INTEGER PRIMARY KEY," +
                    KEY_USER_NAME + "TEXT," +
                    KEY_PASSWORD + "TEXT" +
                ")";
        String CREATE_PRONOUN_TABLE = "CREATE TABLE " + TABLE_PRONOUN +
                "(" + KEY_PRO_ID + "INTEGER PRIMARY KEY," +
                    KEY_WORD_ID + "TEXT, " +
                    KEY_PRONOUN_URL + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PRONOUN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //unimplemented
    }
    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }
}
