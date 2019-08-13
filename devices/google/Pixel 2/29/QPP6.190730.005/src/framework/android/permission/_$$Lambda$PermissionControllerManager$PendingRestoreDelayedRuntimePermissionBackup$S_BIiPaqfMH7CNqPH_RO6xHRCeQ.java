/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$S_BIiPaqfMH7CNqPH_RO6xHRCeQ
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ Consumer f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$S_BIiPaqfMH7CNqPH_RO6xHRCeQ(PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup pendingRestoreDelayedRuntimePermissionBackup, Executor executor, Consumer consumer) {
        this.f$0 = pendingRestoreDelayedRuntimePermissionBackup;
        this.f$1 = executor;
        this.f$2 = consumer;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup(this.f$1, this.f$2, bundle);
    }
}

