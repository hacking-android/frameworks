/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewDebug;
import java.util.concurrent.CountDownLatch;

public final class _$$Lambda$ViewDebug$1iDmmthcZt_8LsYI6VndkxasPEs
implements Runnable {
    private final /* synthetic */ View f$0;
    private final /* synthetic */ Bitmap[] f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ CountDownLatch f$3;

    public /* synthetic */ _$$Lambda$ViewDebug$1iDmmthcZt_8LsYI6VndkxasPEs(View view, Bitmap[] arrbitmap, boolean bl, CountDownLatch countDownLatch) {
        this.f$0 = view;
        this.f$1 = arrbitmap;
        this.f$2 = bl;
        this.f$3 = countDownLatch;
    }

    @Override
    public final void run() {
        ViewDebug.lambda$performViewCapture$5(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

