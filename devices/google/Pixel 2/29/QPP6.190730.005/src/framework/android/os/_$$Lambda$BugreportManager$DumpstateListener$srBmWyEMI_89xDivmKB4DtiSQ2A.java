/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.BugreportManager;

public final class _$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A
implements Runnable {
    private final /* synthetic */ BugreportManager.DumpstateListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A(BugreportManager.DumpstateListener dumpstateListener, int n) {
        this.f$0 = dumpstateListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$1$BugreportManager$DumpstateListener(this.f$1);
    }
}

