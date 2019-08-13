/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyManager;

public final class _$$Lambda$DevicePolicyManager$aBAov4sAc4DWENs1_hCXh31NAg0
implements Runnable {
    private final /* synthetic */ DevicePolicyManager.InstallSystemUpdateCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$DevicePolicyManager$aBAov4sAc4DWENs1_hCXh31NAg0(DevicePolicyManager.InstallSystemUpdateCallback installSystemUpdateCallback, int n, String string2) {
        this.f$0 = installSystemUpdateCallback;
        this.f$1 = n;
        this.f$2 = string2;
    }

    @Override
    public final void run() {
        DevicePolicyManager.lambda$executeCallback$1(this.f$0, this.f$1, this.f$2);
    }
}

