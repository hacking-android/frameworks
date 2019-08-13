/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$cgbsG1socgf6wsJmCUAPmh_jKmw
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$cgbsG1socgf6wsJmCUAPmh_jKmw(PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin pendingSetRuntimePermissionGrantStateByDeviceAdmin) {
        this.f$0 = pendingSetRuntimePermissionGrantStateByDeviceAdmin;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTimeout$2$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin();
    }
}

