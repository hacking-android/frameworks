/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.SocketKeepalive;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$SocketKeepalive$1$0jK7H49vYYFjBANIXTac00ocnSo
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ SocketKeepalive.1 f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ SocketKeepalive.Callback f$2;
    private final /* synthetic */ int f$3;

    public /* synthetic */ _$$Lambda$SocketKeepalive$1$0jK7H49vYYFjBANIXTac00ocnSo(SocketKeepalive.1 var1_1, Executor executor, SocketKeepalive.Callback callback, int n) {
        this.f$0 = var1_1;
        this.f$1 = executor;
        this.f$2 = callback;
        this.f$3 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onError$5$SocketKeepalive$1(this.f$1, this.f$2, this.f$3);
    }
}

