/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;

public final class _$$Lambda$TTC7hNz7BTsLwhNRb2L5kl_7mdU
implements LockPatternUtils.CheckCredentialProgressCallback {
    private final /* synthetic */ LockPatternChecker.OnCheckCallback f$0;

    public /* synthetic */ _$$Lambda$TTC7hNz7BTsLwhNRb2L5kl_7mdU(LockPatternChecker.OnCheckCallback onCheckCallback) {
        this.f$0 = onCheckCallback;
    }

    @Override
    public final void onEarlyMatched() {
        this.f$0.onEarlyMatched();
    }
}

