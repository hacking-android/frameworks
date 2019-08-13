/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Bundle;
import android.os.RemoteCallback;
import java.util.function.Consumer;

public final class _$$Lambda$ZsFzoG2loyqNOR2cNbo_thrNK5c
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$ZsFzoG2loyqNOR2cNbo_thrNK5c(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        this.f$0.sendResult((Bundle)object);
    }
}

