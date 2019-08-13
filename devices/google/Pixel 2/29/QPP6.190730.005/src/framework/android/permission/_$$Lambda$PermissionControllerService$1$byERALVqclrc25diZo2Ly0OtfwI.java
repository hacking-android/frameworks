/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerService$1$byERALVqclrc25diZo2Ly0OtfwI
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$byERALVqclrc25diZo2Ly0OtfwI(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        PermissionControllerService.1.lambda$restoreDelayedRuntimePermissionBackup$1(this.f$0, (Boolean)object);
    }
}

