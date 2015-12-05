package com.dict.audio.audio_dictionary.database;

import android.provider.BaseColumns;

import java.util.Date;

public class Feedback {
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "feedbacks";
        public static final String KEY_UID = "uid";
        public static final String KEY_SID = "sid";
        public static final String KEY_TEXT = "text";
        public static final String KEY_WORD = "word";
        public static final String KEY_TIMESTAMP = "timestamp";
    }

    public int fid;
    public int sid;
    public int uid;
    public String text;
    public String word;
    public Date timestamp;

    public Feedback(int fid, int sid, int uid, String text, String word, String timestamp) {
        this.fid = fid;
        this.sid = sid;
        this.uid = uid;
        this.text = text;
        this.word = word;
        this.timestamp = new Date(Long.valueOf(timestamp) * 1000);
    }
}
