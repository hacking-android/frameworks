/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.app.role.RoleControllerManager;
import android.os.Bundle;
import android.os.RemoteCallback;

public final class _$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$YqB5KyJlcDUM5urf3ImMD1odxhI
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ RoleControllerManager.IsApplicationQualifiedForRoleRequest f$0;

    public /* synthetic */ _$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$YqB5KyJlcDUM5urf3ImMD1odxhI(RoleControllerManager.IsApplicationQualifiedForRoleRequest isApplicationQualifiedForRoleRequest) {
        this.f$0 = isApplicationQualifiedForRoleRequest;
    }

    @Override
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$new$1$RoleControllerManager$IsApplicationQualifiedForRoleRequest(bundle);
    }
}

