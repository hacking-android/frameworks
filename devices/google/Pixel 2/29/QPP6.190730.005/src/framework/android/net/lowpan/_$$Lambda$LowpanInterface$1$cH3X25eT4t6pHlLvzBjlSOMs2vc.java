/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.IpPrefix;
import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$cH3X25eT4t6pHlLvzBjlSOMs2vc
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ IpPrefix f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$cH3X25eT4t6pHlLvzBjlSOMs2vc(LowpanInterface.Callback callback, IpPrefix ipPrefix) {
        this.f$0 = callback;
        this.f$1 = ipPrefix;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onLinkNetworkRemoved$7(this.f$0, this.f$1);
    }
}

