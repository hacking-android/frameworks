/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.euicc.EuiccInfo
 */
package com.android.internal.telephony.euicc;

import android.telephony.euicc.EuiccInfo;
import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$6$RMNCT6pukGHYhU_7k7HVxbm5IWE
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.6 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ EuiccInfo f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$6$RMNCT6pukGHYhU_7k7HVxbm5IWE(EuiccConnector.ConnectedState.6 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, EuiccInfo euiccInfo) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = euiccInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuccess$0$EuiccConnector$ConnectedState$6(this.f$1, this.f$2);
    }
}

