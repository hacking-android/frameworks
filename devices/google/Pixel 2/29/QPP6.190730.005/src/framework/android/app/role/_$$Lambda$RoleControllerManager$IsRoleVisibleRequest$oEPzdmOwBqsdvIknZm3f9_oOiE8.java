/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.app.role.RoleControllerManager;
import android.os.Bundle;
import android.os.RemoteCallback;

public final class _$$Lambda$RoleControllerManager$IsRoleVisibleRequest$oEPzdmOwBqsdvIknZm3f9_oOiE8
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ RoleControllerManager.IsRoleVisibleRequest f$0;

    public /* synthetic */ _$$Lambda$RoleControllerManager$IsRoleVisibleRequest$oEPzdmOwBqsdvIknZm3f9_oOiE8(RoleControllerManager.IsRoleVisibleRequest isRoleVisibleRequest) {
        this.f$0 = isRoleVisibleRequest;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$RoleControllerManager$IsRoleVisibleRequest(bundle);
    }
}

