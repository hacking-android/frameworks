/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseErrorHandler {
    public void onCorruption(SQLiteDatabase var1);
}

