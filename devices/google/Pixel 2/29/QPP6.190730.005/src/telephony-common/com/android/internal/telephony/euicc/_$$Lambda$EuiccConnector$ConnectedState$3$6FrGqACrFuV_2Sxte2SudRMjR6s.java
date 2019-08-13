/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.euicc.DownloadSubscriptionResult
 */
package com.android.internal.telephony.euicc;

import android.service.euicc.DownloadSubscriptionResult;
import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$3$6FrGqACrFuV_2Sxte2SudRMjR6s
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.3 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ DownloadSubscriptionResult f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$3$6FrGqACrFuV_2Sxte2SudRMjR6s(EuiccConnector.ConnectedState.3 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, DownloadSubscriptionResult downloadSubscriptionResult) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = downloadSubscriptionResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onComplete$0$EuiccConnector$ConnectedState$3(this.f$1, this.f$2);
    }
}

