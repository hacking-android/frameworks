/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.graphics.Typeface;
import android.provider.FontsContract;

public final class _$$Lambda$FontsContract$p_tsXYYYpEH0_EJSp2uPrJ33dkU
implements Runnable {
    private final /* synthetic */ FontsContract.FontRequestCallback f$0;
    private final /* synthetic */ Typeface f$1;

    public /* synthetic */ _$$Lambda$FontsContract$p_tsXYYYpEH0_EJSp2uPrJ33dkU(FontsContract.FontRequestCallback fontRequestCallback, Typeface typeface) {
        this.f$0 = fontRequestCallback;
        this.f$1 = typeface;
    }

    @Override
    public final void run() {
        FontsContract.lambda$requestFonts$1(this.f$0, this.f$1);
    }
}

