/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.IBinder;
import android.os.IncidentManager;

public final class _$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g
implements IBinder.DeathRecipient {
    private final /* synthetic */ IncidentManager f$0;

    public /* synthetic */ _$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g(IncidentManager incidentManager) {
        this.f$0 = incidentManager;
    }

    @Override
    public final void binderDied() {
        this.f$0.lambda$getCompanionServiceLocked$1$IncidentManager();
    }
}

