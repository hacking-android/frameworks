/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.DnsResolver;
import android.os.CancellationSignal;
import java.io.FileDescriptor;

public final class _$$Lambda$DnsResolver$hIO7FFv0AXN6Nj0Dzka_LD8S870
implements Runnable {
    private final /* synthetic */ Object f$0;
    private final /* synthetic */ CancellationSignal f$1;
    private final /* synthetic */ FileDescriptor f$2;
    private final /* synthetic */ DnsResolver.Callback f$3;

    public /* synthetic */ _$$Lambda$DnsResolver$hIO7FFv0AXN6Nj0Dzka_LD8S870(Object object, CancellationSignal cancellationSignal, FileDescriptor fileDescriptor, DnsResolver.Callback callback) {
        this.f$0 = object;
        this.f$1 = cancellationSignal;
        this.f$2 = fileDescriptor;
        this.f$3 = callback;
    }

    @Override
    public final void run() {
        DnsResolver.lambda$registerFDListener$8(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

