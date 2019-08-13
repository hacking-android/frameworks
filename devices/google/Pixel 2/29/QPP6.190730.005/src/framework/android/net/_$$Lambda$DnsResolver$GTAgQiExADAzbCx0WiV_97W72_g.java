/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 */
package android.net;

import android.net.DnsResolver;
import android.system.ErrnoException;

public final class _$$Lambda$DnsResolver$GTAgQiExADAzbCx0WiV_97W72_g
implements Runnable {
    private final /* synthetic */ DnsResolver.Callback f$0;
    private final /* synthetic */ ErrnoException f$1;

    public /* synthetic */ _$$Lambda$DnsResolver$GTAgQiExADAzbCx0WiV_97W72_g(DnsResolver.Callback callback, ErrnoException errnoException) {
        this.f$0 = callback;
        this.f$1 = errnoException;
    }

    @Override
    public final void run() {
        DnsResolver.lambda$rawQuery$1(this.f$0, this.f$1);
    }
}

