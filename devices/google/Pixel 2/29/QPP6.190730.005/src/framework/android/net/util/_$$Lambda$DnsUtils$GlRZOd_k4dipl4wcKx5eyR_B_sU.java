/*
 * Decompiled with CFR 0.145.
 */
package android.net.util;

import android.net.util.DnsUtils;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$DnsUtils$GlRZOd_k4dipl4wcKx5eyR_B_sU
implements Consumer {
    private final /* synthetic */ List f$0;

    public /* synthetic */ _$$Lambda$DnsUtils$GlRZOd_k4dipl4wcKx5eyR_B_sU(List list) {
        this.f$0 = list;
    }

    public final void accept(Object object) {
        DnsUtils.lambda$rfc6724Sort$1(this.f$0, (DnsUtils.SortableAddress)object);
    }
}

