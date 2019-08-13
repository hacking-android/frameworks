/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;

public final class _$$Lambda$SocketKeepalive$1$yVvEaumPDc_celEzvlSEH2FU0nc
implements Runnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ SocketKeepalive.Callback f$1;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$yVvEaumPDc_celEzvlSEH2FU0nc(SocketKeepalive.1 var1_1, SocketKeepalive.Callback callback) {
        this.f$0 = var1_1;
        this.f$1 = callback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onDataReceived$6$SocketKeepalive$1(this.f$1);
    }
}

