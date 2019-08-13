/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;

public final class _$$Lambda$PermissionControllerService$1$aoBUJn0rgfJAYfvz7rYL8N9wr_Y
implements Runnable {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$aoBUJn0rgfJAYfvz7rYL8N9wr_Y(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    @Override
    public final void run() {
        PermissionControllerService.1.lambda$grantOrUpgradeDefaultRuntimePermissions$6(this.f$0);
    }
}

