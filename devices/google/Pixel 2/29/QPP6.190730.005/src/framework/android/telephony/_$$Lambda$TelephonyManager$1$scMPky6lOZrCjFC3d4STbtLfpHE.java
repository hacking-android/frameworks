/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import java.util.List;

public final class _$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE
implements Runnable {
    private final /* synthetic */ TelephonyManager.CellInfoCallback f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE(TelephonyManager.CellInfoCallback cellInfoCallback, List list) {
        this.f$0 = cellInfoCallback;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        TelephonyManager.1.lambda$onCellInfo$0(this.f$0, this.f$1);
    }
}

