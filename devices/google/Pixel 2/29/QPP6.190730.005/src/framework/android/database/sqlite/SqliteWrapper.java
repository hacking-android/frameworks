/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
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

    @UnsupportedAppUsage
    public static void checkSQLiteException(Context context, SQLiteException sQLiteException) {
        if (SqliteWrapper.isLowMemory(sQLiteException)) {
            Toast.makeText(context, 17040298, 0).show();
            return;
        }
        throw sQLiteException;
    }

    @UnsupportedAppUsage
    public static int delete(Context context, ContentResolver contentResolver, Uri uri, String string2, String[] arrstring) {
        try {
            int n = contentResolver.delete(uri, string2, arrstring);
            return n;
        }
        catch (SQLiteException sQLiteException) {
            Log.e(TAG, "Catch a SQLiteException when delete: ", sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return -1;
        }
    }

    @UnsupportedAppUsage
    public static Uri insert(Context context, ContentResolver object, Uri uri, ContentValues contentValues) {
        try {
            object = ((ContentResolver)object).insert(uri, contentValues);
            return object;
        }
        catch (SQLiteException sQLiteException) {
            Log.e(TAG, "Catch a SQLiteException when insert: ", sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return null;
        }
    }

    private static boolean isLowMemory(SQLiteException sQLiteException) {
        return sQLiteException.getMessage().equals(SQLITE_EXCEPTION_DETAIL_MESSAGE);
    }

    @UnsupportedAppUsage
    public static Cursor query(Context context, ContentResolver object, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        try {
            object = ((ContentResolver)object).query(uri, arrstring, string2, arrstring2, string3);
            return object;
        }
        catch (SQLiteException sQLiteException) {
            Log.e(TAG, "Catch a SQLiteException when query: ", sQLiteException);
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
            Log.e(TAG, "Catch a SQLiteException when requery: ", sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public static int update(Context context, ContentResolver contentResolver, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        try {
            int n = contentResolver.update(uri, contentValues, string2, arrstring);
            return n;
        }
        catch (SQLiteException sQLiteException) {
            Log.e(TAG, "Catch a SQLiteException when update: ", sQLiteException);
            SqliteWrapper.checkSQLiteException(context, sQLiteException);
            return -1;
        }
    }
}

