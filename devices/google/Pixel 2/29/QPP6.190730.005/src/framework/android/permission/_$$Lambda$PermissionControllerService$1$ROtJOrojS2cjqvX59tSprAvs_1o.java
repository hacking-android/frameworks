/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerService$1$ROtJOrojS2cjqvX59tSprAvs_1o
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$ROtJOrojS2cjqvX59tSprAvs_1o(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        PermissionControllerService.1.lambda$getAppPermissions$2(this.f$0, (List)object);
    }
}

