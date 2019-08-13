/*
 * Decompiled with CFR 0.145.
 */
package android.service.watchdog;

import android.service.watchdog.ExplicitHealthCheckService;

public final class _$$Lambda$ExplicitHealthCheckService$ulagkAZ2bM7_LW9T7PSTxSLQfBE
implements Runnable {
    private final /* synthetic */ ExplicitHealthCheckService f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$ExplicitHealthCheckService$ulagkAZ2bM7_LW9T7PSTxSLQfBE(ExplicitHealthCheckService explicitHealthCheckService, String string2) {
        this.f$0 = explicitHealthCheckService;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$notifyHealthCheckPassed$0$ExplicitHealthCheckService(this.f$1);
    }
}

