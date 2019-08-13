/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg
implements Runnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive f$0;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg(ConnectivityManager.PacketKeepalive packetKeepalive) {
        this.f$0 = packetKeepalive;
    }

    @Override
    public final void run() {
        this.f$0.lambda$stop$0$ConnectivityManager$PacketKeepalive();
    }
}

