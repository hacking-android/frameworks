/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$iOtsqOYp69ztB6u3PYNu_iI_PGo
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$iOtsqOYp69ztB6u3PYNu_iI_PGo(ConnectivityManager.PacketKeepalive.1 var1_1, int n, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = packetKeepaliveCallback;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onStarted$1$ConnectivityManager$PacketKeepalive$1(this.f$1, this.f$2);
    }
}

