/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.NumberVerificationCallback;
import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$TelephonyManager$3$TrNEDm6VsUgT1BQFiXGiPDtbxuA
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ NumberVerificationCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$TelephonyManager$3$TrNEDm6VsUgT1BQFiXGiPDtbxuA(Executor executor, NumberVerificationCallback numberVerificationCallback, int n) {
        this.f$0 = executor;
        this.f$1 = numberVerificationCallback;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.3.lambda$onVerificationFailed$3(this.f$0, this.f$1, this.f$2);
    }
}

