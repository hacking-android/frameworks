/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.NumberVerificationCallback;
import android.telephony.TelephonyManager;

public final class _$$Lambda$TelephonyManager$4i1RRVjnCzfQvX2hIGG9K8g4DaY
implements Runnable {
    private final /* synthetic */ NumberVerificationCallback f$0;

    public /* synthetic */ _$$Lambda$TelephonyManager$4i1RRVjnCzfQvX2hIGG9K8g4DaY(NumberVerificationCallback numberVerificationCallback) {
        this.f$0 = numberVerificationCallback;
    }

    @Override
    public final void run() {
        TelephonyManager.lambda$requestNumberVerification$0(this.f$0);
    }
}

