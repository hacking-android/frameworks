/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.DnsResolver;
import android.os.CancellationSignal;
import java.io.FileDescriptor;

public final class _$$Lambda$DnsResolver$DW9jYL2ZOH6BjebIVPhZIrrhoD8
implements CancellationSignal.OnCancelListener {
    private final /* synthetic */ DnsResolver f$0;
    private final /* synthetic */ Object f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ FileDescriptor f$3;
    private final /* synthetic */ boolean f$4;
    private final /* synthetic */ FileDescriptor f$5;

    public /* synthetic */ _$$Lambda$DnsResolver$DW9jYL2ZOH6BjebIVPhZIrrhoD8(DnsResolver dnsResolver, Object object, boolean bl, FileDescriptor fileDescriptor, boolean bl2, FileDescriptor fileDescriptor2) {
        this.f$0 = dnsResolver;
        this.f$1 = object;
        this.f$2 = bl;
        this.f$3 = fileDescriptor;
        this.f$4 = bl2;
        this.f$5 = fileDescriptor2;
    }

    @Override
    public final void onCancel() {
        this.f$0.lambda$query$6$DnsResolver(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
    }
}

