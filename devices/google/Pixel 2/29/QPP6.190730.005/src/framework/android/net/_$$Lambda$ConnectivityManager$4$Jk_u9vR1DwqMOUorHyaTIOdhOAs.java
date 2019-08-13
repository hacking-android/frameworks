/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$ConnectivityManager$4$Jk_u9vR1DwqMOUorHyaTIOdhOAs
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ ConnectivityManager.OnTetheringEntitlementResultListener f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$4$Jk_u9vR1DwqMOUorHyaTIOdhOAs(Executor executor, ConnectivityManager.OnTetheringEntitlementResultListener onTetheringEntitlementResultListener, int n) {
        this.f$0 = executor;
        this.f$1 = onTetheringEntitlementResultListener;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        ConnectivityManager.4.lambda$onReceiveResult$1(this.f$0, this.f$1, this.f$2);
    }
}

