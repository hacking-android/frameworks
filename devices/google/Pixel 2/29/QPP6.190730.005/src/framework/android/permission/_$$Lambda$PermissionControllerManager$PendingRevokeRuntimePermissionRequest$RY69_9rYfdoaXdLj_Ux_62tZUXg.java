/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$RY69_9rYfdoaXdLj_Ux_62tZUXg
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingRevokeRuntimePermissionRequest f$0;
    private final /* synthetic */ Bundle f$1;
    private final /* synthetic */ PermissionControllerManager.OnRevokeRuntimePermissionsCallback f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$RY69_9rYfdoaXdLj_Ux_62tZUXg(PermissionControllerManager.PendingRevokeRuntimePermissionRequest pendingRevokeRuntimePermissionRequest, Bundle bundle, PermissionControllerManager.OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
        this.f$0 = pendingRevokeRuntimePermissionRequest;
        this.f$1 = bundle;
        this.f$2 = onRevokeRuntimePermissionsCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingRevokeRuntimePermissionRequest(this.f$1, this.f$2);
    }
}

