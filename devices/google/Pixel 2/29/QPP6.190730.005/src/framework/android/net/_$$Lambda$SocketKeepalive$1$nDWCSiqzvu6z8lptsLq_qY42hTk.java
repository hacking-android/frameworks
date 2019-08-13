/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;

public final class _$$Lambda$SocketKeepalive$1$nDWCSiqzvu6z8lptsLq_qY42hTk
implements Runnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ SocketKeepalive.Callback f$2;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$nDWCSiqzvu6z8lptsLq_qY42hTk(SocketKeepalive.1 var1_1, int n, SocketKeepalive.Callback callback) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = callback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStarted$0$SocketKeepalive$1(this.f$1, this.f$2);
    }
}

