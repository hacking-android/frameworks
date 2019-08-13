/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.BugreportManager;

public final class _$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w
implements Runnable {
    private final /* synthetic */ BugreportManager.DumpstateListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w(BugreportManager.DumpstateListener dumpstateListener, int n) {
        this.f$0 = dumpstateListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onProgress$0$BugreportManager$DumpstateListener(this.f$1);
    }
}

