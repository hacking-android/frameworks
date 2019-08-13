/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Intent
 */
package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import com.android.internal.telephony.euicc.EuiccController;

public final class _$$Lambda$EuiccController$aZ8yEHh32lS1TctCOFmVEa57ekc
implements Runnable {
    private final /* synthetic */ EuiccController f$0;
    private final /* synthetic */ PendingIntent f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ Intent f$3;

    public /* synthetic */ _$$Lambda$EuiccController$aZ8yEHh32lS1TctCOFmVEa57ekc(EuiccController euiccController, PendingIntent pendingIntent, int n, Intent intent) {
        this.f$0 = euiccController;
        this.f$1 = pendingIntent;
        this.f$2 = n;
        this.f$3 = intent;
    }

    @Override
    public final void run() {
        this.f$0.lambda$refreshSubscriptionsAndSendResult$0$EuiccController(this.f$1, this.f$2, this.f$3);
    }
}

