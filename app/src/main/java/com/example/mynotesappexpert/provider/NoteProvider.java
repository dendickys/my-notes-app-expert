package com.example.mynotesappexpert.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.mynotesappexpert.db.NoteHelper;

import static com.example.mynotesappexpert.db.DatabaseContract.AUTHORITY;
import static com.example.mynotesappexpert.db.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.example.mynotesappexpert.db.DatabaseContract.NoteColumns.TABLE_NAME;

public class NoteProvider extends ContentProvider {

    /*Integer digunakan sebagai identifier antara select all sama select by id*/
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private NoteHelper noteHelper;

    private static final UriMatcher sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
    misal
    uri com.dicoding.picodiploma.mynotesapp dicocokan dengan integer 1
    uri com.dicoding.picodiploma.mynotesapp/# dicocokan dengan integer 2
     */
    static {
        // content://com.example.mynotesappexpert/note
        sUriMathcer.addURI(AUTHORITY, TABLE_NAME, NOTE);

        // content://com.example.mynotesappexpert/note/id
        sUriMathcer.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        noteHelper = NoteHelper.getInstance(getContext());
        noteHelper.open();
        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMathcer.match(uri)) {
            case NOTE:
                cursor = noteHelper.queryAll();
                break;
            case NOTE_ID:
                cursor = noteHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMathcer.match(uri)) {
            case NOTE:
                added = noteHelper.insert(values);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        switch (sUriMathcer.match(uri)) {
            case NOTE_ID:
                updated = noteHelper.update(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMathcer.match(uri)) {
            case NOTE_ID:
                deleted = noteHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }
}
