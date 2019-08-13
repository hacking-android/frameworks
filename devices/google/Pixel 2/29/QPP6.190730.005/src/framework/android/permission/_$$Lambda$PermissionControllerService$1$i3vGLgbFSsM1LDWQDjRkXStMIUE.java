/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.function.IntConsumer;

public final class _$$Lambda$PermissionControllerService$1$i3vGLgbFSsM1LDWQDjRkXStMIUE
implements IntConsumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$i3vGLgbFSsM1LDWQDjRkXStMIUE(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    @Override
    public final void accept(int n) {
        PermissionControllerService.1.lambda$countPermissionApps$3(this.f$0, n);
    }
}

