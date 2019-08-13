/*
 * Decompiled with CFR 0.145.
 */
package android.service.watchdog;

import android.os.RemoteCallback;
import android.service.watchdog.ExplicitHealthCheckService;

public final class _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$5Rv9E4_Jc0y0GMGqI_g_82qtYpg
implements Runnable {
    private final /* synthetic */ ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper f$0;
    private final /* synthetic */ RemoteCallback f$1;

    public /* synthetic */ _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$5Rv9E4_Jc0y0GMGqI_g_82qtYpg(ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper explicitHealthCheckServiceWrapper, RemoteCallback remoteCallback) {
        this.f$0 = explicitHealthCheckServiceWrapper;
        this.f$1 = remoteCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$getSupportedPackages$3$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(this.f$1);
    }
}

