/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyManager$6$AFjFk42NCFYCMG8wA5_6SCfk7No
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$6$AFjFk42NCFYCMG8wA5_6SCfk7No(Consumer consumer, int n) {
        this.f$0 = consumer;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        TelephonyManager.6.lambda$onComplete$0(this.f$0, this.f$1);
    }
}

