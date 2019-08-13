/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.ParcelableException;
import android.telephony.TelephonyManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$TelephonyManager$2$6owqHJtmTOa9dDQAz_9oKh9XFVk
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ TelephonyManager.CellInfoCallback f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ ParcelableException f$3;

    public /* synthetic */ _$$Lambda$TelephonyManager$2$6owqHJtmTOa9dDQAz_9oKh9XFVk(Executor executor, TelephonyManager.CellInfoCallback cellInfoCallback, int n, ParcelableException parcelableException) {
        this.f$0 = executor;
        this.f$1 = cellInfoCallback;
        this.f$2 = n;
        this.f$3 = parcelableException;
    }

    @Override
    public final void runOrThrow() {
        TelephonyManager.2.lambda$onError$3(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

