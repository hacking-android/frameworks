/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class IntentReceiverLeakedViolation
extends Violation {
    public IntentReceiverLeakedViolation(Throwable throwable) {
        super(null);
        this.setStackTrace(throwable.getStackTrace());
    }
}

