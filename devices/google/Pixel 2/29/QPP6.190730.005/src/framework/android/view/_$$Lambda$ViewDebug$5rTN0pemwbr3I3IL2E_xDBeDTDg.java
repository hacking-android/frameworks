/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ViewDebug;
import java.util.concurrent.CountDownLatch;

public final class _$$Lambda$ViewDebug$5rTN0pemwbr3I3IL2E_xDBeDTDg
implements Runnable {
    private final /* synthetic */ ViewDebug.ViewOperation f$0;
    private final /* synthetic */ long[] f$1;
    private final /* synthetic */ CountDownLatch f$2;

    public /* synthetic */ _$$Lambda$ViewDebug$5rTN0pemwbr3I3IL2E_xDBeDTDg(ViewDebug.ViewOperation viewOperation, long[] arrl, CountDownLatch countDownLatch) {
        this.f$0 = viewOperation;
        this.f$1 = arrl;
        this.f$2 = countDownLatch;
    }

    @Override
    public final void run() {
        ViewDebug.lambda$profileViewOperation$3(this.f$0, this.f$1, this.f$2);
    }
}

