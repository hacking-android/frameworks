/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class SqliteObjectLeakedViolation
extends Violation {
    public SqliteObjectLeakedViolation(String string2, Throwable throwable) {
        super(string2);
        this.initCause(throwable);
    }
}

