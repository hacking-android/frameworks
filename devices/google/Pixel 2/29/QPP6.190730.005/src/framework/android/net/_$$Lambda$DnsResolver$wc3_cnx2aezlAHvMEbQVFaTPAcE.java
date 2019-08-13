/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 */
package android.net;

import android.net.DnsResolver;
import android.system.ErrnoException;

public final class _$$Lambda$DnsResolver$wc3_cnx2aezlAHvMEbQVFaTPAcE
implements Runnable {
    private final /* synthetic */ DnsResolver.Callback f$0;
    private final /* synthetic */ ErrnoException f$1;

    public /* synthetic */ _$$Lambda$DnsResolver$wc3_cnx2aezlAHvMEbQVFaTPAcE(DnsResolver.Callback callback, ErrnoException errnoException) {
        this.f$0 = callback;
        this.f$1 = errnoException;
    }

    @Override
    public final void run() {
        DnsResolver.lambda$query$7(this.f$0, this.f$1);
    }
}

