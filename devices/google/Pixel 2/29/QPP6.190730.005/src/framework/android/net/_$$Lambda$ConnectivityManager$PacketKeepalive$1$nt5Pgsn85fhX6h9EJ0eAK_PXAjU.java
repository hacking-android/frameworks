/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$nt5Pgsn85fhX6h9EJ0eAK_PXAjU
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$nt5Pgsn85fhX6h9EJ0eAK_PXAjU(ConnectivityManager.PacketKeepalive.1 var1_1, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback, int n) {
        this.f$0 = var1_1;
        this.f$1 = packetKeepaliveCallback;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onError$5$ConnectivityManager$PacketKeepalive$1(this.f$1, this.f$2);
    }
}

