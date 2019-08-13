/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface SQLiteCursorDriver {
    public void cursorClosed();

    public void cursorDeactivated();

    public void cursorRequeried(Cursor var1);

    public Cursor query(SQLiteDatabase.CursorFactory var1, String[] var2);

    public void setBindArguments(String[] var1);
}

