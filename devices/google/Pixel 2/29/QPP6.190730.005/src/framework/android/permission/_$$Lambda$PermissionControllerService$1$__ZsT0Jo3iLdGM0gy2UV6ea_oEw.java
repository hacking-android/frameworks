/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.Map;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerService$1$__ZsT0Jo3iLdGM0gy2UV6ea_oEw
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$__ZsT0Jo3iLdGM0gy2UV6ea_oEw(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        PermissionControllerService.1.lambda$revokeRuntimePermissions$0(this.f$0, (Map)object);
    }
}

