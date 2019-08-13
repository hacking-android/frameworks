/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.permission.PermissionControllerManager;

public final class _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8(PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup pendingRestoreDelayedRuntimePermissionBackup) {
        this.f$0 = pendingRestoreDelayedRuntimePermissionBackup;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTimeout$2$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup();
    }
}

