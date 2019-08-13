/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;

public final class _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$4YNlUy9HsD02E7Sbv2VeVtbao08
implements Runnable {
    private final /* synthetic */ ImsMmTelManager.CapabilityCallback.CapabilityBinder f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$4YNlUy9HsD02E7Sbv2VeVtbao08(ImsMmTelManager.CapabilityCallback.CapabilityBinder capabilityBinder, int n) {
        this.f$0 = capabilityBinder;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCapabilitiesStatusChanged$0$ImsMmTelManager$CapabilityCallback$CapabilityBinder(this.f$1);
    }
}

