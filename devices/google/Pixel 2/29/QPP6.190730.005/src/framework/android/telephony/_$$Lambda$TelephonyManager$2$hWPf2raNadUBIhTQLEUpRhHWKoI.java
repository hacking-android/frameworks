/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.List;
import java.util.concurrent.Executor;

public final class _$$Lambda$TelephonyManager$2$hWPf2raNadUBIhTQLEUpRhHWKoI
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ TelephonyManager.CellInfoCallback f$1;
    private final /* synthetic */ List f$2;

    public /* synthetic */ _$$Lambda$TelephonyManager$2$hWPf2raNadUBIhTQLEUpRhHWKoI(Executor executor, TelephonyManager.CellInfoCallback cellInfoCallback, List list) {
        this.f$0 = executor;
        this.f$1 = cellInfoCallback;
        this.f$2 = list;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.2.lambda$onCellInfo$1(this.f$0, this.f$1, this.f$2);
    }
}

