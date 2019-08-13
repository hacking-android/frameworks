/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$WmmtbYWlzqL_V8wWUDKe3CWjvy0
implements Runnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$1;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$WmmtbYWlzqL_V8wWUDKe3CWjvy0(ConnectivityManager.PacketKeepalive.1 var1_1, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback) {
        this.f$0 = var1_1;
        this.f$1 = packetKeepaliveCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStopped$2$ConnectivityManager$PacketKeepalive$1(this.f$1);
    }
}

