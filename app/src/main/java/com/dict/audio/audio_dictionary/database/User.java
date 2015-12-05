package com.dict.audio.audio_dictionary.database;

import android.provider.BaseColumns;

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
}
