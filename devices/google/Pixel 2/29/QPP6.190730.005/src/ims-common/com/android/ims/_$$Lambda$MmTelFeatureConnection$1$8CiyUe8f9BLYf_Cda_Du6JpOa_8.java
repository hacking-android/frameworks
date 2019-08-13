/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import com.android.ims.MmTelFeatureConnection;

public final class _$$Lambda$MmTelFeatureConnection$1$8CiyUe8f9BLYf_Cda_Du6JpOa_8
implements Runnable {
    private final /* synthetic */ MmTelFeatureConnection.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$MmTelFeatureConnection$1$8CiyUe8f9BLYf_Cda_Du6JpOa_8(MmTelFeatureConnection.1 var1_1, int n, int n2) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$imsFeatureRemoved$1$MmTelFeatureConnection$1(this.f$1, this.f$2);
    }
}

