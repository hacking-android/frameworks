/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiSwitchClient;

public final class _$$Lambda$HdmiSwitchClient$3$apecUZ8P9DH90drOKNmw2Y8Fspg
implements Runnable {
    private final /* synthetic */ HdmiSwitchClient.OnSelectListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$HdmiSwitchClient$3$apecUZ8P9DH90drOKNmw2Y8Fspg(HdmiSwitchClient.OnSelectListener onSelectListener, int n) {
        this.f$0 = onSelectListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        HdmiSwitchClient.3.lambda$onComplete$0(this.f$0, this.f$1);
    }
}

