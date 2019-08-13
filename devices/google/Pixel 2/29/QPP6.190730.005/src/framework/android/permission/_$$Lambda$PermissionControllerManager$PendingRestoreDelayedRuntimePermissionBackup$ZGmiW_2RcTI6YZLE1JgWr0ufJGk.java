/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.permission.PermissionControllerManager;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$ZGmiW_2RcTI6YZLE1JgWr0ufJGk
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup f$0;
    private final /* synthetic */ Consumer f$1;
    private final /* synthetic */ Bundle f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$ZGmiW_2RcTI6YZLE1JgWr0ufJGk(PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup pendingRestoreDelayedRuntimePermissionBackup, Consumer consumer, Bundle bundle) {
        this.f$0 = pendingRestoreDelayedRuntimePermissionBackup;
        this.f$1 = consumer;
        this.f$2 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup(this.f$1, this.f$2);
    }
}

