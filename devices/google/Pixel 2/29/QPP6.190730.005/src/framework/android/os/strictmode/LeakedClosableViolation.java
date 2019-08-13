/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class LeakedClosableViolation
extends Violation {
    public LeakedClosableViolation(String string2, Throwable throwable) {
        super(string2);
        this.initCause(throwable);
    }
}

