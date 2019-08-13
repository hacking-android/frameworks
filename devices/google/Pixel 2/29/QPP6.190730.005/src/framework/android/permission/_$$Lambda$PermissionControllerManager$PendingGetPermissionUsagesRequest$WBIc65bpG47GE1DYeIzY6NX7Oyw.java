/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$WBIc65bpG47GE1DYeIzY6NX7Oyw
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingGetPermissionUsagesRequest f$0;
    private final /* synthetic */ Bundle f$1;
    private final /* synthetic */ PermissionControllerManager.OnPermissionUsageResultCallback f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$WBIc65bpG47GE1DYeIzY6NX7Oyw(PermissionControllerManager.PendingGetPermissionUsagesRequest pendingGetPermissionUsagesRequest, Bundle bundle, PermissionControllerManager.OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
        this.f$0 = pendingGetPermissionUsagesRequest;
        this.f$1 = bundle;
        this.f$2 = onPermissionUsageResultCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingGetPermissionUsagesRequest(this.f$1, this.f$2);
    }
}

