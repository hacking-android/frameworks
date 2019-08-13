/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;

public final class _$$Lambda$SocketKeepalive$1$xxwNi85oVXVQ_ILhrZNWwo4ppA8
implements Runnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ SocketKeepalive.Callback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$xxwNi85oVXVQ_ILhrZNWwo4ppA8(SocketKeepalive.1 var1_1, SocketKeepalive.Callback callback, int n) {
        this.f$0 = var1_1;
        this.f$1 = callback;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$4$SocketKeepalive$1(this.f$1, this.f$2);
    }
}

