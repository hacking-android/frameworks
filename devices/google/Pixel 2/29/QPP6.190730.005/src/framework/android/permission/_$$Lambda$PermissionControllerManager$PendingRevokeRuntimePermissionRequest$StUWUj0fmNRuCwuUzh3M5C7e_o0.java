/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;
import java.util.concurrent.Executor;

public final class _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$StUWUj0fmNRuCwuUzh3M5C7e_o0
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingRevokeRuntimePermissionRequest f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ PermissionControllerManager.OnRevokeRuntimePermissionsCallback f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$StUWUj0fmNRuCwuUzh3M5C7e_o0(PermissionControllerManager.PendingRevokeRuntimePermissionRequest pendingRevokeRuntimePermissionRequest, Executor executor, PermissionControllerManager.OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
        this.f$0 = pendingRevokeRuntimePermissionRequest;
        this.f$1 = executor;
        this.f$2 = onRevokeRuntimePermissionsCallback;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$PermissionControllerManager$PendingRevokeRuntimePermissionRequest(this.f$1, this.f$2, bundle);
    }
}

