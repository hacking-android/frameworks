/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.ZygoteConnection;

public final class _$$Lambda$ZygoteConnection$KxVsZ_s4KsanePOHCU5JcuypPik
implements Runnable {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ZygoteConnection$KxVsZ_s4KsanePOHCU5JcuypPik(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    @Override
    public final void run() {
        ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(this.f$0, this.f$1);
    }
}

