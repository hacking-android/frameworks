/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$DV4gvjPxJzdQvcfoIJqGrzFtTQs
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;

    public /* synthetic */ _$$Lambda$FontsContract$DV4gvjPxJzdQvcfoIJqGrzFtTQs(FontsContract.FontRequestCallback fontRequestCallback) {
        this.f$0 = fontRequestCallback;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$6(this.f$0);
    }
}

