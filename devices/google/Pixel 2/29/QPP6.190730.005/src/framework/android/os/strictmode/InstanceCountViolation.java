/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public class InstanceCountViolation
extends Violation {
    private static final StackTraceElement[] FAKE_STACK = new StackTraceElement[]{new StackTraceElement("android.os.StrictMode", "setClassInstanceLimit", "StrictMode.java", 1)};
    private final long mInstances;

    public InstanceCountViolation(Class class_, long l, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.toString());
        stringBuilder.append("; instances=");
        stringBuilder.append(l);
        stringBuilder.append("; limit=");
        stringBuilder.append(n);
        super(stringBuilder.toString());
        this.setStackTrace(FAKE_STACK);
        this.mInstances = l;
    }

    public long getNumberOfInstances() {
        return this.mInstances;
    }
}

