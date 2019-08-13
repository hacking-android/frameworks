/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$LJ3jfZobcxq5xTMmb88GlM1r9Jk
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;

    public /* synthetic */ _$$Lambda$FontsContract$LJ3jfZobcxq5xTMmb88GlM1r9Jk(FontsContract.FontRequestCallback fontRequestCallback) {
        this.f$0 = fontRequestCallback;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$7(this.f$0);
    }
}

