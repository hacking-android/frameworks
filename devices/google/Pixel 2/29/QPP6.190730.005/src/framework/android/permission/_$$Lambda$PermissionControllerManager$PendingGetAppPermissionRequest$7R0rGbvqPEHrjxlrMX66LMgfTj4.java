/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingGetAppPermissionRequest$7R0rGbvqPEHrjxlrMX66LMgfTj4
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingGetAppPermissionRequest f$0;
    private final /* synthetic */ PermissionControllerManager.OnGetAppPermissionResultCallback f$1;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingGetAppPermissionRequest$7R0rGbvqPEHrjxlrMX66LMgfTj4(PermissionControllerManager.PendingGetAppPermissionRequest pendingGetAppPermissionRequest, PermissionControllerManager.OnGetAppPermissionResultCallback onGetAppPermissionResultCallback) {
        this.f$0 = pendingGetAppPermissionRequest;
        this.f$1 = onGetAppPermissionResultCallback;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingGetAppPermissionRequest(this.f$1, bundle);
    }
}

