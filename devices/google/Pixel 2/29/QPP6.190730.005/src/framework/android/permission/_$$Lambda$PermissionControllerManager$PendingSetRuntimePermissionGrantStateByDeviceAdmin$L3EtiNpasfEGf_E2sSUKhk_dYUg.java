/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Bundle;
import android.permission.PermissionControllerManager;
import java.util.function.Consumer;

public final class _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$L3EtiNpasfEGf_E2sSUKhk_dYUg
implements Runnable {
    private final /* synthetic */ PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin f$0;
    private final /* synthetic */ Consumer f$1;
    private final /* synthetic */ Bundle f$2;

    public /* synthetic */ _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$L3EtiNpasfEGf_E2sSUKhk_dYUg(PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin pendingSetRuntimePermissionGrantStateByDeviceAdmin, Consumer consumer, Bundle bundle) {
        this.f$0 = pendingSetRuntimePermissionGrantStateByDeviceAdmin;
        this.f$1 = consumer;
        this.f$2 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin(this.f$1, this.f$2);
    }
}

