/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$SocketKeepalive$1$m_VPtyb2YaC8aWd5gXQYgFGhVbM
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ SocketKeepalive.Callback f$2;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$m_VPtyb2YaC8aWd5gXQYgFGhVbM(SocketKeepalive.1 var1_1, int n, SocketKeepalive.Callback callback) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = callback;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onStarted$1$SocketKeepalive$1(this.f$1, this.f$2);
    }
}

