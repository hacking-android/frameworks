/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.StrictMode;

public final class _$$Lambda$StrictMode$yZJXPvy2veRNA_xL_SWdXzX_OLg
implements Runnable {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ StrictMode.ViolationInfo f$1;

    public /* synthetic */ _$$Lambda$StrictMode$yZJXPvy2veRNA_xL_SWdXzX_OLg(int n, StrictMode.ViolationInfo violationInfo) {
        this.f$0 = n;
        this.f$1 = violationInfo;
    }

    @Override
    public final void run() {
        StrictMode.lambda$dropboxViolationAsync$2(this.f$0, this.f$1);
    }
}

