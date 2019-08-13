/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$rvEOORTXb3mMYTLkoH9nlHQr9Iw
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$FontsContract$rvEOORTXb3mMYTLkoH9nlHQr9Iw(FontsContract.FontRequestCallback fontRequestCallback, int n) {
        this.f$0 = fontRequestCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$9(this.f$0, this.f$1);
    }
}

