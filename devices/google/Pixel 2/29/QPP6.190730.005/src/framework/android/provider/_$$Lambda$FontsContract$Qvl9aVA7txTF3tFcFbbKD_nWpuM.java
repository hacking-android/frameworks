/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$Qvl9aVA7txTF3tFcFbbKD_nWpuM
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;

    public /* synthetic */ _$$Lambda$FontsContract$Qvl9aVA7txTF3tFcFbbKD_nWpuM(FontsContract.FontRequestCallback fontRequestCallback) {
        this.f$0 = fontRequestCallback;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$8(this.f$0);
    }
}

