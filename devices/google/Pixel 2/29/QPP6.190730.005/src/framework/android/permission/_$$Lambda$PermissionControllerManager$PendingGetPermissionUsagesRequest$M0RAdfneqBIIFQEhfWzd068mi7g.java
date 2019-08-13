/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;
import java.util.concurrent.Executor;

public final class _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$M0RAdfneqBIIFQEhfWzd068mi7g
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingGetPermissionUsagesRequest f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ PermissionControllerManager.OnPermissionUsageResultCallback f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$M0RAdfneqBIIFQEhfWzd068mi7g(PermissionControllerManager.PendingGetPermissionUsagesRequest pendingGetPermissionUsagesRequest, Executor executor, PermissionControllerManager.OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
        this.f$0 = pendingGetPermissionUsagesRequest;
        this.f$1 = executor;
        this.f$2 = onPermissionUsageResultCallback;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$PermissionControllerManager$PendingGetPermissionUsagesRequest(this.f$1, this.f$2, bundle);
    }
}

