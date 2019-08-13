/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import android.net.Network;

public final class _$$Lambda$ConnectivityManager$3$Hh_etCA_vVs2IV58umWLOd1O4yk
implements Runnable {
    private final /* synthetic */ ConnectivityManager.OnTetheringEventCallback f$0;
    private final /* synthetic */ Network f$1;

    public /* synthetic */ _$$Lambda$ConnectivityManager$3$Hh_etCA_vVs2IV58umWLOd1O4yk(ConnectivityManager.OnTetheringEventCallback onTetheringEventCallback, Network network) {
        this.f$0 = onTetheringEventCallback;
        this.f$1 = network;
    }

    @Override
    public final void run() {
        ConnectivityManager.3.lambda$onUpstreamChanged$0(this.f$0, this.f$1);
    }
}

