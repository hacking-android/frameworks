/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.CancellationSignal;

public final class SQLiteDirectCursorDriver
implements SQLiteCursorDriver {
    private final CancellationSignal mCancellationSignal;
    private final SQLiteDatabase mDatabase;
    private final String mEditTable;
    private SQLiteQuery mQuery;
    private final String mSql;

    public SQLiteDirectCursorDriver(SQLiteDatabase sQLiteDatabase, String string2, String string3, CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        this.mEditTable = string3;
        this.mSql = string2;
        this.mCancellationSignal = cancellationSignal;
    }

    @Override
    public void cursorClosed() {
    }

    @Override
    public void cursorDeactivated() {
    }

    @Override
    public void cursorRequeried(Cursor cursor) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Cursor query(SQLiteDatabase.CursorFactory object, String[] arrstring) {
        SQLiteQuery sQLiteQuery = new SQLiteQuery(this.mDatabase, this.mSql, this.mCancellationSignal);
        try {
            sQLiteQuery.bindAllArgsAsStrings(arrstring);
            object = object == null ? new SQLiteCursor(this, this.mEditTable, sQLiteQuery) : object.newCursor(this.mDatabase, this, this.mEditTable, sQLiteQuery);
        }
        catch (RuntimeException runtimeException) {
            sQLiteQuery.close();
            throw runtimeException;
        }
        this.mQuery = sQLiteQuery;
        return object;
    }

    @Override
    public void setBindArguments(String[] arrstring) {
        this.mQuery.bindAllArgsAsStrings(arrstring);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLiteDirectCursorDriver: ");
        stringBuilder.append(this.mSql);
        return stringBuilder.toString();
    }
}

