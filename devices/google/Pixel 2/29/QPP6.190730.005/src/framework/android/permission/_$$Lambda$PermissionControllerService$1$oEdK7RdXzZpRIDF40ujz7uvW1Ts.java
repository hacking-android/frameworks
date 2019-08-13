/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerService$1$oEdK7RdXzZpRIDF40ujz7uvW1Ts
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$oEdK7RdXzZpRIDF40ujz7uvW1Ts(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        PermissionControllerService.1.lambda$getPermissionUsages$4(this.f$0, (List)object);
    }
}

