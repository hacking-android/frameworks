/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$HQXgA6xx0k7jv6y22RQn3Fx34QQ
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingRevokeRuntimePermissionRequest f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$HQXgA6xx0k7jv6y22RQn3Fx34QQ(PermissionControllerManager.PendingRevokeRuntimePermissionRequest pendingRevokeRuntimePermissionRequest) {
        this.f$0 = pendingRevokeRuntimePermissionRequest;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTimeout$2$PermissionControllerManager$PendingRevokeRuntimePermissionRequest();
    }
}

