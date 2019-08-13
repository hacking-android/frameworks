/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.DnsResolver;

public final class _$$Lambda$DnsResolver$kjq9c3feWPGKUPV3AzJBFi1GUvw
implements Runnable {
    private final /* synthetic */ DnsResolver.Callback f$0;

    public /* synthetic */ _$$Lambda$DnsResolver$kjq9c3feWPGKUPV3AzJBFi1GUvw(DnsResolver.Callback callback) {
        this.f$0 = callback;
    }

    @Override
    public final void run() {
        DnsResolver.lambda$query$3(this.f$0);
    }
}

