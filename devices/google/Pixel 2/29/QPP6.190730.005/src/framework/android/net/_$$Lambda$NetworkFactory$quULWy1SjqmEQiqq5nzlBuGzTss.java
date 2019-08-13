/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkFactory;
import android.net.NetworkRequest;

public final class _$$Lambda$NetworkFactory$quULWy1SjqmEQiqq5nzlBuGzTss
implements Runnable {
    private final /* synthetic */ NetworkFactory f$0;
    private final /* synthetic */ NetworkRequest f$1;

    public /* synthetic */ _$$Lambda$NetworkFactory$quULWy1SjqmEQiqq5nzlBuGzTss(NetworkFactory networkFactory, NetworkRequest networkRequest) {
        this.f$0 = networkFactory;
        this.f$1 = networkRequest;
    }

    @Override
    public final void run() {
        this.f$0.lambda$releaseRequestAsUnfulfillableByAnyFactory$1$NetworkFactory(this.f$1);
    }
}

