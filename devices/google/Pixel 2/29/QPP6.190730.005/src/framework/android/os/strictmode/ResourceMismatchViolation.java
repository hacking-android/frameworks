/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class ResourceMismatchViolation
extends Violation {
    public ResourceMismatchViolation(Object object) {
        super(object.toString());
    }
}

