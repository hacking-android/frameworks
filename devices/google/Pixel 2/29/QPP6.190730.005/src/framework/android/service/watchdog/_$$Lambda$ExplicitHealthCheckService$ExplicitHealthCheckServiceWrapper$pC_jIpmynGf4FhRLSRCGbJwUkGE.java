/*
 * Decompiled with CFR 0.145.
 */
package android.service.watchdog;

import android.service.watchdog.ExplicitHealthCheckService;

public final class _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$pC_jIpmynGf4FhRLSRCGbJwUkGE
implements Runnable {
    private final /* synthetic */ ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$pC_jIpmynGf4FhRLSRCGbJwUkGE(ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper explicitHealthCheckServiceWrapper, String string2) {
        this.f$0 = explicitHealthCheckServiceWrapper;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$cancel$2$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(this.f$1);
    }
}

