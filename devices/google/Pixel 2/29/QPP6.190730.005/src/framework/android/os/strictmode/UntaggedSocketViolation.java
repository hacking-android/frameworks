/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class UntaggedSocketViolation
extends Violation {
    public UntaggedSocketViolation() {
        super("Untagged socket detected; use TrafficStats.setThreadSocketTag() to track all network usage");
    }
}

