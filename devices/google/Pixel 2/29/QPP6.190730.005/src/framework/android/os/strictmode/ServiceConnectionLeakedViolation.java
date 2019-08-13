/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class ServiceConnectionLeakedViolation
extends Violation {
    public ServiceConnectionLeakedViolation(Throwable throwable) {
        super(null);
        this.setStackTrace(throwable.getStackTrace());
    }
}

