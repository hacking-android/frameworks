/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteSession;
import java.util.function.Supplier;

public final class _$$Lambda$RBWjWVyGrOTsQrLCYzJ_G8Uk25Q
implements Supplier {
    private final /* synthetic */ SQLiteDatabase f$0;

    public /* synthetic */ _$$Lambda$RBWjWVyGrOTsQrLCYzJ_G8Uk25Q(SQLiteDatabase sQLiteDatabase) {
        this.f$0 = sQLiteDatabase;
    }

    public final Object get() {
        return this.f$0.createSession();
    }
}

