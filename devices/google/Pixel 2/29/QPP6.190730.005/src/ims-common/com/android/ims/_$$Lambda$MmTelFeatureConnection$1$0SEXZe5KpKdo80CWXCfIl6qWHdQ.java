/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import com.android.ims.MmTelFeatureConnection;

public final class _$$Lambda$MmTelFeatureConnection$1$0SEXZe5KpKdo80CWXCfIl6qWHdQ
implements Runnable {
    private final /* synthetic */ MmTelFeatureConnection.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ int f$3;

    public /* synthetic */ _$$Lambda$MmTelFeatureConnection$1$0SEXZe5KpKdo80CWXCfIl6qWHdQ(MmTelFeatureConnection.1 var1_1, int n, int n2, int n3) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = n2;
        this.f$3 = n3;
    }

    @Override
    public final void run() {
        this.f$0.lambda$imsStatusChanged$2$MmTelFeatureConnection$1(this.f$1, this.f$2, this.f$3);
    }
}

