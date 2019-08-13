/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;

public final class _$$Lambda$ConnectivityManager$4$GbcJVaUJX_pIrYQi94EYHYBwTJI
implements Runnable {
    private final /* synthetic */ ConnectivityManager.OnTetheringEntitlementResultListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ConnectivityManager$4$GbcJVaUJX_pIrYQi94EYHYBwTJI(ConnectivityManager.OnTetheringEntitlementResultListener onTetheringEntitlementResultListener, int n) {
        this.f$0 = onTetheringEntitlementResultListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        ConnectivityManager.4.lambda$onReceiveResult$0(this.f$0, this.f$1);
    }
}

