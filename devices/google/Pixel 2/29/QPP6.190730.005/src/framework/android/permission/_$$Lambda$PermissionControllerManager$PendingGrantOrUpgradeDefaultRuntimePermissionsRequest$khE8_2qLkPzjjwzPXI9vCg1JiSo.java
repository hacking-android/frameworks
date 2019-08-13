/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.permission.PermissionControllerManager;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$khE8_2qLkPzjjwzPXI9vCg1JiSo
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ PermissionControllerManager.PendingGrantOrUpgradeDefaultRuntimePermissionsRequest f$0;
    private final /* synthetic */ Executor f$1;
    private final /* synthetic */ Consumer f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$khE8_2qLkPzjjwzPXI9vCg1JiSo(PermissionControllerManager.PendingGrantOrUpgradeDefaultRuntimePermissionsRequest pendingGrantOrUpgradeDefaultRuntimePermissionsRequest, Executor executor, Consumer consumer) {
        this.f$0 = pendingGrantOrUpgradeDefaultRuntimePermissionsRequest;
        this.f$1 = executor;
        this.f$2 = consumer;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(this.f$1, this.f$2, bundle);
    }
}

