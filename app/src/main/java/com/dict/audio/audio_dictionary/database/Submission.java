package com.dict.audio.audio_dictionary.database;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;

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
        public static final String KEY_UPVOTE = "upvote";
        public static final String KEY_DOWNVOTE = "downvote";
    }

    public int sid;
    public int uid;
    public int upvote;
    public int downvote;
    public String word;
    public ArrayList<Integer> fids;
    public String audio;
    public Date timestamp;

    public Submission(int sid, int uid, String word, String audio, String fids, String timestamp,
                      int upvote, int downvote) {
        this.sid = sid;
        this.uid = uid;
        this.word = word.trim();
        this.audio = audio;
        this.upvote = upvote;
        this.downvote = downvote;


        this.fids = new ArrayList<>();

        if (!fids.isEmpty()) {
            // remove brackets if necessary
            if (fids.startsWith("[") && fids.endsWith("]"))
                fids = fids.substring(1, fids.length() - 1);

            // parse all FIDs
            if (fids.contains(","))
                for (String s : fids.split(","))
                    this.fids.add(Integer.parseInt(s.trim()));
        }

        this.timestamp = new Date(Long.valueOf(timestamp) * 1000);
    }
}
