/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.euicc.GetDefaultDownloadableSubscriptionListResult
 */
package com.android.internal.telephony.euicc;

import android.service.euicc.GetDefaultDownloadableSubscriptionListResult;
import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$5$zyynBcfeewf_ACr0Sg8S162JrG4
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.5 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ GetDefaultDownloadableSubscriptionListResult f$3;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$5$zyynBcfeewf_ACr0Sg8S162JrG4(EuiccConnector.ConnectedState.5 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n, GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = n;
        this.f$3 = getDefaultDownloadableSubscriptionListResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onComplete$0$EuiccConnector$ConnectedState$5(this.f$1, this.f$2, this.f$3);
    }
}

