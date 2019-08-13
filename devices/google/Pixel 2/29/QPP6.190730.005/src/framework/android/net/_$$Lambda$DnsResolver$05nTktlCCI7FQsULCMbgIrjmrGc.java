/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.DnsResolver;
import android.os.CancellationSignal;
import java.io.FileDescriptor;

public final class _$$Lambda$DnsResolver$05nTktlCCI7FQsULCMbgIrjmrGc
implements CancellationSignal.OnCancelListener {
    private final /* synthetic */ DnsResolver f$0;
    private final /* synthetic */ Object f$1;
    private final /* synthetic */ FileDescriptor f$2;

    public /* synthetic */ _$$Lambda$DnsResolver$05nTktlCCI7FQsULCMbgIrjmrGc(DnsResolver dnsResolver, Object object, FileDescriptor fileDescriptor) {
        this.f$0 = dnsResolver;
        this.f$1 = object;
        this.f$2 = fileDescriptor;
    }

    @Override
    public final void onCancel() {
        this.f$0.lambda$addCancellationSignal$10$DnsResolver(this.f$1, this.f$2);
    }
}

