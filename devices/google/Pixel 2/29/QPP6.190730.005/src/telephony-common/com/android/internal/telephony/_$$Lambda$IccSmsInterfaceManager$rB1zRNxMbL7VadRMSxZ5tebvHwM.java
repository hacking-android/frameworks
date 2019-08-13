/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.android.internal.telephony;

import android.app.PendingIntent;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.SmsDispatchersController;

public final class _$$Lambda$IccSmsInterfaceManager$rB1zRNxMbL7VadRMSxZ5tebvHwM
implements SmsDispatchersController.SmsInjectionCallback {
    private final /* synthetic */ PendingIntent f$0;

    public /* synthetic */ _$$Lambda$IccSmsInterfaceManager$rB1zRNxMbL7VadRMSxZ5tebvHwM(PendingIntent pendingIntent) {
        this.f$0 = pendingIntent;
    }

    @Override
    public final void onSmsInjectedResult(int n) {
        IccSmsInterfaceManager.lambda$injectSmsPdu$0(this.f$0, n);
    }
}

