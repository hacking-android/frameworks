/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.IBinder;
import android.os.IncidentManager;

public final class _$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk
implements IBinder.DeathRecipient {
    private final /* synthetic */ IncidentManager f$0;

    public /* synthetic */ _$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk(IncidentManager incidentManager) {
        this.f$0 = incidentManager;
    }

    @Override
    public final void binderDied() {
        this.f$0.lambda$getIIncidentManagerLocked$0$IncidentManager();
    }
}

