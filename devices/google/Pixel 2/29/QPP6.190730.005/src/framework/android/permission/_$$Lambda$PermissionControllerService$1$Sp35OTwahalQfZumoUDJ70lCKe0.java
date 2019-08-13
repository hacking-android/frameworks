/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.RemoteCallback;
import android.permission.PermissionControllerService;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerService$1$Sp35OTwahalQfZumoUDJ70lCKe0
implements Consumer {
    private final /* synthetic */ RemoteCallback f$0;

    public /* synthetic */ _$$Lambda$PermissionControllerService$1$Sp35OTwahalQfZumoUDJ70lCKe0(RemoteCallback remoteCallback) {
        this.f$0 = remoteCallback;
    }

    public final void accept(Object object) {
        PermissionControllerService.1.lambda$setRuntimePermissionGrantStateByDeviceAdmin$5(this.f$0, (Boolean)object);
    }
}

