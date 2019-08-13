/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ConnectivityManager$PacketKeepalive$1$_H5tzn67t3ydWL8tXpl9UyOmDcc
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ConnectivityManager.PacketKeepalive.1 f$0;
    private final /* synthetic */ ConnectivityManager.PacketKeepaliveCallback f$1;

    public /* synthetic */ _$$Lambda$ConnectivityManager$PacketKeepalive$1$_H5tzn67t3ydWL8tXpl9UyOmDcc(ConnectivityManager.PacketKeepalive.1 var1_1, ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback) {
        this.f$0 = var1_1;
        this.f$1 = packetKeepaliveCallback;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onStopped$3$ConnectivityManager$PacketKeepalive$1(this.f$1);
    }
}

