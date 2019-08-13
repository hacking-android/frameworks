/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyManager$eMNW6lCcxHLvIrcBQvhUXUKuLFU
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$eMNW6lCcxHLvIrcBQvhUXUKuLFU(Executor executor, Consumer consumer) {
        this.f$0 = executor;
        this.f$1 = consumer;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.lambda$updateAvailableNetworks$2(this.f$0, this.f$1);
    }
}

