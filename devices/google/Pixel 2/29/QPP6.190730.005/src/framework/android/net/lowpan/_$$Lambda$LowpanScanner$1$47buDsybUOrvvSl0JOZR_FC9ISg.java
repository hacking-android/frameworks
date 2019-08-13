/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanScanner;

public final class _$$Lambda$LowpanScanner$1$47buDsybUOrvvSl0JOZR_FC9ISg
implements Runnable {
    private final /* synthetic */ LowpanScanner.Callback f$0;
    private final /* synthetic */ LowpanBeaconInfo f$1;

    public /* synthetic */ _$$Lambda$LowpanScanner$1$47buDsybUOrvvSl0JOZR_FC9ISg(LowpanScanner.Callback callback, LowpanBeaconInfo lowpanBeaconInfo) {
        this.f$0 = callback;
        this.f$1 = lowpanBeaconInfo;
    }

    @Override
    public final void run() {
        LowpanScanner.1.lambda$onNetScanBeacon$0(this.f$0, this.f$1);
    }
}

