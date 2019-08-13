/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.TcpSocketKeepalive;

public final class _$$Lambda$TcpSocketKeepalive$E1MP45uBTM6jPfrxAAqXFllEmAA
implements Runnable {
    private final /* synthetic */ TcpSocketKeepalive f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TcpSocketKeepalive$E1MP45uBTM6jPfrxAAqXFllEmAA(TcpSocketKeepalive tcpSocketKeepalive, int n) {
        this.f$0 = tcpSocketKeepalive;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$startImpl$0$TcpSocketKeepalive(this.f$1);
    }
}

