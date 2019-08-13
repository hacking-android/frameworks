/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import com.android.ims.ImsManager;

public final class _$$Lambda$ImsManager$D1JuJ3ba2jMHWDKlSpm03meBR1c
implements Runnable {
    private final /* synthetic */ ImsManager f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsManager$D1JuJ3ba2jMHWDKlSpm03meBR1c(ImsManager imsManager, int n) {
        this.f$0 = imsManager;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setWfcRoamingSettingInternal$2$ImsManager(this.f$1);
    }
}

