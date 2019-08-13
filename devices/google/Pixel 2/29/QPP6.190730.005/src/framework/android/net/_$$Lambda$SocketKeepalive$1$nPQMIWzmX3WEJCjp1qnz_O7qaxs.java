/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$SocketKeepalive$1$nPQMIWzmX3WEJCjp1qnz_O7qaxs
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ SocketKeepalive.Callback f$2;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$nPQMIWzmX3WEJCjp1qnz_O7qaxs(SocketKeepalive.1 var1_1, Executor executor, SocketKeepalive.Callback callback) {
        this.f$0 = var1_1;
        this.f$1 = executor;
        this.f$2 = callback;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onDataReceived$7$SocketKeepalive$1(this.f$1, this.f$2);
    }
}

