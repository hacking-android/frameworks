/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.ParcelableException;
import android.telephony.TelephonyManager;

public final class _$$Lambda$TelephonyManager$2$Ulw55AvQUDkoL1gDNnPVlIOb8mw
implements Runnable {
    private final /* synthetic */ TelephonyManager.CellInfoCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ParcelableException f$2;

    public /* synthetic */ _$$Lambda$TelephonyManager$2$Ulw55AvQUDkoL1gDNnPVlIOb8mw(TelephonyManager.CellInfoCallback cellInfoCallback, int n, ParcelableException parcelableException) {
        this.f$0 = cellInfoCallback;
        this.f$1 = n;
        this.f$2 = parcelableException;
    }

    @Override
    public final void run() {
        TelephonyManager.2.lambda$onError$2(this.f$0, this.f$1, this.f$2);
    }
}

