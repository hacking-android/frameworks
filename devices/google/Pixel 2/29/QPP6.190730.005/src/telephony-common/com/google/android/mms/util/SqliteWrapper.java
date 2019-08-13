/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteException
 *  android.net.Uri
 *  android.util.Log
 *  android.widget.Toast
 */
package com.google.android.mms.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public final class SqliteWrapper {
    private static final String SQLITE_EXCEPTION_DETAIL_MESSAGE = "unable to open database file";
    private static final String TAG = "SqliteWrapper";

    private SqliteWrapper() {
    }

    public static void checkSQLiteException(Context context, SQLiteException sQLiteException) {
        if (SqliteWrapper.isLowMemory(sQLiteException)) {
            Toast.makeText((Context)context, (int)17040298, (int)0).show();
            return;
        }
        throw sQLiteException;
    }

    public static int delete(Context context, ContentResolver contentResolver, Uri uri, String string, String[] arrstring) {
        try {
            int n = contentResolver.delete(uri, string, arrstring);
            return n;
        }
        catch (SQLiteException sQLiteException) {
            Log.e((String)TAG, (String)"Catch a SQLiteException when delete: ", (Throwable)sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return -1;
        }
    }

    public static Uri insert(Context context, ContentResolver contentResolver, Uri uri, ContentValues contentValues) {
        try {
            contentResolver = contentResolver.insert(uri, contentValues);
            return contentResolver;
        }
        catch (SQLiteException sQLiteException) {
            Log.e((String)TAG, (String)"Catch a SQLiteException when insert: ", (Throwable)sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return null;
        }
    }

    private static boolean isLowMemory(Context context) {
        if (context == null) {
            return false;
        }
        context = (ActivityManager)context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        context.getMemoryInfo(memoryInfo);
        return memoryInfo.lowMemory;
    }

    private static boolean isLowMemory(SQLiteException sQLiteException) {
        return sQLiteException.getMessage().equals(SQLITE_EXCEPTION_DETAIL_MESSAGE);
    }

    public static Cursor query(Context context, ContentResolver contentResolver, Uri uri, String[] arrstring, String string, String[] arrstring2, String string2) {
        try {
            contentResolver = contentResolver.query(uri, arrstring, string, arrstring2, string2);
            return contentResolver;
        }
        catch (SQLiteException sQLiteException) {
            Log.e((String)TAG, (String)"Catch a SQLiteException when query: ", (Throwable)sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return null;
        }
    }

    public static boolean requery(Context context, Cursor cursor) {
        try {
            boolean bl = cursor.requery();
            return bl;
        }
        catch (SQLiteException sQLiteException) {
            Log.e((String)TAG, (String)"Catch a SQLiteException when requery: ", (Throwable)sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return false;
        }
    }

    public static int update(Context context, ContentResolver contentResolver, Uri uri, ContentValues contentValues, String string, String[] arrstring) {
        try {
            int n = contentResolver.update(uri, contentValues, string, arrstring);
            return n;
        }
        catch (SQLiteException sQLiteException) {
            Log.e((String)TAG, (String)"Catch a SQLiteException when update: ", (Throwable)sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return -1;
        }
    }
}

