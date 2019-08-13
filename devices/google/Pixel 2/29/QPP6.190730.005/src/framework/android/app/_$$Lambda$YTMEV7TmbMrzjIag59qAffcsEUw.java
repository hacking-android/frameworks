/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.KeyguardManager;

public final class _$$Lambda$YTMEV7TmbMrzjIag59qAffcsEUw
implements Runnable {
    private final /* synthetic */ KeyguardManager.KeyguardDismissCallback f$0;

    public /* synthetic */ _$$Lambda$YTMEV7TmbMrzjIag59qAffcsEUw(KeyguardManager.KeyguardDismissCallback keyguardDismissCallback) {
        this.f$0 = keyguardDismissCallback;
    }

    @Override
    public final void run() {
        this.f$0.onDismissSucceeded();
    }
}

