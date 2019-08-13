/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.os.strictmode.Violation;

public final class WebViewMethodCalledOnWrongThreadViolation
extends Violation {
    public WebViewMethodCalledOnWrongThreadViolation(Throwable throwable) {
        super(null);
        this.setStackTrace(throwable.getStackTrace());
    }
}

