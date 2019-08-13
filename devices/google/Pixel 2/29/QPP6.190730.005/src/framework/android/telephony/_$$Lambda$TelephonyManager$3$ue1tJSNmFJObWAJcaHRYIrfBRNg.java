/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.NumberVerificationCallback;
import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$TelephonyManager$3$ue1tJSNmFJObWAJcaHRYIrfBRNg
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ NumberVerificationCallback f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$TelephonyManager$3$ue1tJSNmFJObWAJcaHRYIrfBRNg(Executor executor, NumberVerificationCallback numberVerificationCallback, String string2) {
        this.f$0 = executor;
        this.f$1 = numberVerificationCallback;
        this.f$2 = string2;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.3.lambda$onCallReceived$1(this.f$0, this.f$1, this.f$2);
    }
}

