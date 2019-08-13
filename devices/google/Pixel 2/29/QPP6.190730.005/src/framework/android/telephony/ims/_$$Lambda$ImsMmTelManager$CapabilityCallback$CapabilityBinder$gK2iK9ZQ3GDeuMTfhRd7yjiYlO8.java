/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.CapabilityCallback.CapabilityBinder f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8(ImsMmTelManager.CapabilityCallback.CapabilityBinder capabilityBinder, int n) {
        this.f$0 = capabilityBinder;
        this.f$1 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onCapabilitiesStatusChanged$1$ImsMmTelManager$CapabilityCallback$CapabilityBinder(this.f$1);
    }
}

