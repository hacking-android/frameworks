/*
 * Decompiled with CFR 0.145.
 */
package android.net.util;

import android.net.Network;
import android.net.util.DnsUtils;
import java.net.InetAddress;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$DnsUtils$E7rjA1PKdcqMJSVvye8jaivYDec
implements Consumer {
    private final /* synthetic */ List f$0;
    private final /* synthetic */ Network f$1;

    public /* synthetic */ _$$Lambda$DnsUtils$E7rjA1PKdcqMJSVvye8jaivYDec(List list, Network network) {
        this.f$0 = list;
        this.f$1 = network;
    }

    public final void accept(Object object) {
        DnsUtils.lambda$rfc6724Sort$0(this.f$0, this.f$1, (InetAddress)object);
    }
}

