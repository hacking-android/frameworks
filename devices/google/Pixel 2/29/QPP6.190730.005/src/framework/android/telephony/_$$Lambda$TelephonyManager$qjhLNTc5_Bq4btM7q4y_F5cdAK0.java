/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyManager$qjhLNTc5_Bq4btM7q4y_F5cdAK0
implements Runnable {
    private final /* synthetic */ Consumer f$0;

    public /* synthetic */ _$$Lambda$TelephonyManager$qjhLNTc5_Bq4btM7q4y_F5cdAK0(Consumer consumer) {
        this.f$0 = consumer;
    }

    @Override
    public final void run() {
        TelephonyManager.lambda$updateAvailableNetworks$1(this.f$0);
    }
}

