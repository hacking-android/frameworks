/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$FCawscMFN_8Qxcb2EdA5gdE_O2k
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;

    public /* synthetic */ _$$Lambda$FontsContract$FCawscMFN_8Qxcb2EdA5gdE_O2k(FontsContract.FontRequestCallback fontRequestCallback) {
        this.f$0 = fontRequestCallback;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$5(this.f$0);
    }
}

