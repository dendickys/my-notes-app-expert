package com.example.consumerappexpert.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.mynotesappexpert";
    private static final String SCHEME = "content";

    public static final class NoteColumns implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";

        // untuk membuat URI content://com.example.mynotesappexpert/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
