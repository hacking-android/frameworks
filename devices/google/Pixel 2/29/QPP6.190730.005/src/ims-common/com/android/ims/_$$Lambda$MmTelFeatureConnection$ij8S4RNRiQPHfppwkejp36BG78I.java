/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package com.android.ims;

import android.os.IBinder;
import com.android.ims.MmTelFeatureConnection;

public final class _$$Lambda$MmTelFeatureConnection$ij8S4RNRiQPHfppwkejp36BG78I
implements IBinder.DeathRecipient {
    private final /* synthetic */ MmTelFeatureConnection f$0;

    public /* synthetic */ _$$Lambda$MmTelFeatureConnection$ij8S4RNRiQPHfppwkejp36BG78I(MmTelFeatureConnection mmTelFeatureConnection) {
        this.f$0 = mmTelFeatureConnection;
    }

    public final void binderDied() {
        this.f$0.lambda$new$0$MmTelFeatureConnection();
    }
}

