/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyManager$5$dLg4hbo46SmKP0wtKbXAlS8hCpg
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$5$dLg4hbo46SmKP0wtKbXAlS8hCpg(Consumer consumer, int n) {
        this.f$0 = consumer;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        TelephonyManager.5.lambda$onComplete$0(this.f$0, this.f$1);
    }
}

