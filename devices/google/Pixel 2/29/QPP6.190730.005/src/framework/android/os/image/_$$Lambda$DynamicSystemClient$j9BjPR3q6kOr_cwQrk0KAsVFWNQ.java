/*
 * Decompiled with CFR 0.145.
 */
package android.os.image;

import android.os.image.DynamicSystemClient;

public final class _$$Lambda$DynamicSystemClient$j9BjPR3q6kOr_cwQrk0KAsVFWNQ
implements Runnable {
    private final /* synthetic */ DynamicSystemClient f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ long f$3;
    private final /* synthetic */ Throwable f$4;

    public /* synthetic */ _$$Lambda$DynamicSystemClient$j9BjPR3q6kOr_cwQrk0KAsVFWNQ(DynamicSystemClient dynamicSystemClient, int n, int n2, long l, Throwable throwable) {
        this.f$0 = dynamicSystemClient;
        this.f$1 = n;
        this.f$2 = n2;
        this.f$3 = l;
        this.f$4 = throwable;
    }

    @Override
    public final void run() {
        this.f$0.lambda$handleMessage$0$DynamicSystemClient(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

