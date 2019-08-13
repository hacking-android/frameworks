/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingGetRuntimePermissionBackup f$0;
    private final /* synthetic */ byte[] f$1;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE(PermissionControllerManager.PendingGetRuntimePermissionBackup pendingGetRuntimePermissionBackup, byte[] arrby) {
        this.f$0 = pendingGetRuntimePermissionBackup;
        this.f$1 = arrby;
    }

    @Override
    public final void run() {
        this.f$0.lambda$accept$0$PermissionControllerManager$PendingGetRuntimePermissionBackup(this.f$1);
    }
}

