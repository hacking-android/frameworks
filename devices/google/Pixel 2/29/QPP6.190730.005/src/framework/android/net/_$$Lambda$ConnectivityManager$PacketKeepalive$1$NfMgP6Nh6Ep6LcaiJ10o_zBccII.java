/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$NfMgP6Nh6Ep6LcaiJ10o_zBccII
implements Runnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$NfMgP6Nh6Ep6LcaiJ10o_zBccII(ConnectivityManager.PacketKeepalive.1 var1_1, int n, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = packetKeepaliveCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStarted$0$ConnectivityManager$PacketKeepalive$1(this.f$1, this.f$2);
    }
}

