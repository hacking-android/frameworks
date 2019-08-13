/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyManager;
import android.os.Bundle;
import android.os.RemoteCallback;
import java.util.concurrent.CompletableFuture;

public final class _$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ CompletableFuture f$0;

    public /* synthetic */ _$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk(CompletableFuture completableFuture) {
        this.f$0 = completableFuture;
    }

    @Override
    public final void onResult(Bundle bundle) {
        DevicePolicyManager.lambda$setPermissionGrantState$0(this.f$0, bundle);
    }
}

