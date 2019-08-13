/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.DnsResolver;
import android.os.CancellationSignal;
import android.os.MessageQueue;
import java.io.FileDescriptor;
import java.util.concurrent.Executor;

public final class _$$Lambda$DnsResolver$kxKi6qjPYeR_SIipxW4tYpxyM50
implements MessageQueue.OnFileDescriptorEventListener {
    private final /* synthetic */ MessageQueue f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ Object f$2;
    private final /* synthetic */ CancellationSignal f$3;
    private final /* synthetic */ DnsResolver.Callback f$4;

    public /* synthetic */ _$$Lambda$DnsResolver$kxKi6qjPYeR_SIipxW4tYpxyM50(MessageQueue messageQueue, Executor executor, Object object, CancellationSignal cancellationSignal, DnsResolver.Callback callback) {
        this.f$0 = messageQueue;
        this.f$1 = executor;
        this.f$2 = object;
        this.f$3 = cancellationSignal;
        this.f$4 = callback;
    }

    @Override
    public final int onFileDescriptorEvents(FileDescriptor fileDescriptor, int n) {
        return DnsResolver.lambda$registerFDListener$9(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, fileDescriptor, n);
    }
}

