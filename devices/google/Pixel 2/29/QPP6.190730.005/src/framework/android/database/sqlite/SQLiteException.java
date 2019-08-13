/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.SQLException;

public class SQLiteException
extends SQLException {
    public SQLiteException() {
    }

    public SQLiteException(String string2) {
        super(string2);
    }

    public SQLiteException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

