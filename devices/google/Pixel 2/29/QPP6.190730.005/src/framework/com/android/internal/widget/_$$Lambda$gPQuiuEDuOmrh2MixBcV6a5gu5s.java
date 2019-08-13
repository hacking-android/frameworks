/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import com.android.internal.widget.LockPatternUtils;

public final class _$$Lambda$gPQuiuEDuOmrh2MixBcV6a5gu5s
implements Runnable {
    private final /* synthetic */ LockPatternUtils.CheckCredentialProgressCallback f$0;

    public /* synthetic */ _$$Lambda$gPQuiuEDuOmrh2MixBcV6a5gu5s(LockPatternUtils.CheckCredentialProgressCallback checkCredentialProgressCallback) {
        this.f$0 = checkCredentialProgressCallback;
    }

    @Override
    public final void run() {
        this.f$0.onEarlyMatched();
    }
}

