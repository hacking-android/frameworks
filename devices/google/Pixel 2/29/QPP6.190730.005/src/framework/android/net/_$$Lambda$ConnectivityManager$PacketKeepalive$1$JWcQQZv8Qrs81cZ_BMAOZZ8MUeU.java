/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$JWcQQZv8Qrs81cZ_BMAOZZ8MUeU
implements Runnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$JWcQQZv8Qrs81cZ_BMAOZZ8MUeU(ConnectivityManager.PacketKeepalive.1 var1_1, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback, int n) {
        this.f$0 = var1_1;
        this.f$1 = packetKeepaliveCallback;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$4$ConnectivityManager$PacketKeepalive$1(this.f$1, this.f$2);
    }
}

