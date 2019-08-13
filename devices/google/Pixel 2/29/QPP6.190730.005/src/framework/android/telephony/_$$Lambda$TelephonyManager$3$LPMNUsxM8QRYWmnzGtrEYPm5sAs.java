/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.NumberVerificationCallback;
import android.telephony.TelephonyManager;

public final class _$$Lambda$TelephonyManager$3$LPMNUsxM8QRYWmnzGtrEYPm5sAs
implements Runnable {
    private final /* synthetic */ NumberVerificationCallback f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$3$LPMNUsxM8QRYWmnzGtrEYPm5sAs(NumberVerificationCallback numberVerificationCallback, String string2) {
        this.f$0 = numberVerificationCallback;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        TelephonyManager.3.lambda$onCallReceived$0(this.f$0, this.f$1);
    }
}

