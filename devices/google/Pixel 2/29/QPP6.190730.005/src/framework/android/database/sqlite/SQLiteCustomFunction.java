/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.sqlite.SQLiteDatabase;

public final class SQLiteCustomFunction {
    public final SQLiteDatabase.CustomFunction callback;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final String name;
    @UnsupportedAppUsage
    public final int numArgs;

    public SQLiteCustomFunction(String string2, int n, SQLiteDatabase.CustomFunction customFunction) {
        if (string2 != null) {
            this.name = string2;
            this.numArgs = n;
            this.callback = customFunction;
            return;
        }
        throw new IllegalArgumentException("name must not be null.");
    }

    @UnsupportedAppUsage
    private void dispatchCallback(String[] arrstring) {
        this.callback.callback(arrstring);
    }
}

