/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.database.sqlite.-$
 *  android.database.sqlite.-$$Lambda
 *  android.database.sqlite.-$$Lambda$SQLiteDatabase
 *  android.database.sqlite.-$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE
 */
package android.database.sqlite;

import android.database.sqlite.-$;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.Comparator;

public final class _$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE
implements Comparator {
    public static final /* synthetic */ -$.Lambda.SQLiteDatabase.1FsSJH2q7x3eeDFXCAu9l4piDsE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE();
    }

    private /* synthetic */ _$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE() {
    }

    public final int compare(Object object, Object object2) {
        return SQLiteDatabase.lambda$dumpDatabaseDirectory$0((File)object, (File)object2);
    }
}

