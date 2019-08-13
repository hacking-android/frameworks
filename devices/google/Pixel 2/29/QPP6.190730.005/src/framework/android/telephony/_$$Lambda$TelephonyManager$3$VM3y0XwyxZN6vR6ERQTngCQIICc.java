/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.NumberVerificationCallback;
import android.telephony.TelephonyManager;

public final class _$$Lambda$TelephonyManager$3$VM3y0XwyxZN6vR6ERQTngCQIICc
implements Runnable {
    private final /* synthetic */ NumberVerificationCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$3$VM3y0XwyxZN6vR6ERQTngCQIICc(NumberVerificationCallback numberVerificationCallback, int n) {
        this.f$0 = numberVerificationCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        TelephonyManager.3.lambda$onVerificationFailed$2(this.f$0, this.f$1);
    }
}

