/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingCountPermissionAppsRequest$5yk4p2I96nUHJ1QRErjoF1iiLLY
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingCountPermissionAppsRequest f$0;
    private final /* synthetic */ PermissionControllerManager.OnCountPermissionAppsResultCallback f$1;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingCountPermissionAppsRequest$5yk4p2I96nUHJ1QRErjoF1iiLLY(PermissionControllerManager.PendingCountPermissionAppsRequest pendingCountPermissionAppsRequest, PermissionControllerManager.OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback) {
        this.f$0 = pendingCountPermissionAppsRequest;
        this.f$1 = onCountPermissionAppsResultCallback;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingCountPermissionAppsRequest(this.f$1, bundle);
    }
}

