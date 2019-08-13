/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyManager;

public final class _$$Lambda$DevicePolicyManager$1$k6Rmp3Fg9FFATYRU5Z7rHDXGemA
implements Runnable {
    private final /* synthetic */ DevicePolicyManager.OnClearApplicationUserDataListener f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ boolean f$2;

    public /* synthetic */ _$$Lambda$DevicePolicyManager$1$k6Rmp3Fg9FFATYRU5Z7rHDXGemA(DevicePolicyManager.OnClearApplicationUserDataListener onClearApplicationUserDataListener, String string2, boolean bl) {
        this.f$0 = onClearApplicationUserDataListener;
        this.f$1 = string2;
        this.f$2 = bl;
    }

    @Override
    public final void run() {
        DevicePolicyManager.1.lambda$onRemoveCompleted$0(this.f$0, this.f$1, this.f$2);
    }
}

