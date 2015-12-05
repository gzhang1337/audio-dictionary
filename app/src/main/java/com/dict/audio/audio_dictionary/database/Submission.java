package com.dict.audio.audio_dictionary.database;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
* Created and implemented by William Harris, Yinchen Zhang, Rae Kang
* */
public class Submission {
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "submissions";
        public static final String KEY_UID = "uid";
        public static final String KEY_WORD = "word";
        public static final String KEY_FIDS = "fids";
        public static final String KEY_AUDIO = "audio";
        public static final String KEY_TIMESTAMP = "time";
    }

    public int sid;
    public int uid;
    public String word;
    public List<Integer> fids;
    public String audio;
    public Date timestamp;

    public Submission(int sid, int uid, String word, String audio, String fids, String timestamp) {
        this.sid = sid;
        this.uid = uid;
        this.word = word;
        this.audio = audio;

        this.fids = new ArrayList<>();
        if (!fids.isEmpty()) {
            for (String fid : fids.split(" ")) {
                this.fids.add(Integer.parseInt(fid));
            }
        }
        this.timestamp = new Date(Long.valueOf(timestamp) * 1000);
    }
}
