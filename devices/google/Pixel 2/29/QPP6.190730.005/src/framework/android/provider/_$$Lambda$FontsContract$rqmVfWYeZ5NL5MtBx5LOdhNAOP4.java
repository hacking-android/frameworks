/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$rqmVfWYeZ5NL5MtBx5LOdhNAOP4
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;

    public /* synthetic */ _$$Lambda$FontsContract$rqmVfWYeZ5NL5MtBx5LOdhNAOP4(FontsContract.FontRequestCallback fontRequestCallback) {
        this.f$0 = fontRequestCallback;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$10(this.f$0);
    }
}

