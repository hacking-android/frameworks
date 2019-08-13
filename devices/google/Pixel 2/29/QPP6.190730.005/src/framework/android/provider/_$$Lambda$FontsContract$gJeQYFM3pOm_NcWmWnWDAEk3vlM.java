/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.graphics.Typeface;
import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$gJeQYFM3pOm_NcWmWnWDAEk3vlM
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;
    private final /* synthetic */ Typeface f$1;

    public /* synthetic */ _$$Lambda$FontsContract$gJeQYFM3pOm_NcWmWnWDAEk3vlM(FontsContract.FontRequestCallback fontRequestCallback, Typeface typeface) {
        this.f$0 = fontRequestCallback;
        this.f$1 = typeface;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$11(this.f$0, this.f$1);
    }
}

