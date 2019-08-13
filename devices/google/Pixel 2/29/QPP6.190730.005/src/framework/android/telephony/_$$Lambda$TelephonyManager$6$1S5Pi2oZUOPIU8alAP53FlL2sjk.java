/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyManager$6$1S5Pi2oZUOPIU8alAP53FlL2sjk
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Consumer f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$TelephonyManager$6$1S5Pi2oZUOPIU8alAP53FlL2sjk(Executor executor, Consumer consumer, int n) {
        this.f$0 = executor;
        this.f$1 = consumer;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.6.lambda$onComplete$1(this.f$0, this.f$1, this.f$2);
    }
}

