/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.sqlite.SQLiteException;

public class SQLiteDatabaseCorruptException
extends SQLiteException {
    public SQLiteDatabaseCorruptException() {
    }

    public SQLiteDatabaseCorruptException(String string2) {
        super(string2);
    }

    public static boolean isCorruptException(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof SQLiteDatabaseCorruptException) {
                return true;
            }
            throwable = throwable.getCause();
        }
        return false;
    }
}

