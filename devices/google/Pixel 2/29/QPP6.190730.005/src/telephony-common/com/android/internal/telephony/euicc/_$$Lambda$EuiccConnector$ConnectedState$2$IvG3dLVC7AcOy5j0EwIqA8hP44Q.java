/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.euicc.GetDownloadableSubscriptionMetadataResult
 */
package com.android.internal.telephony.euicc;

import android.service.euicc.GetDownloadableSubscriptionMetadataResult;
import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$2$IvG3dLVC7AcOy5j0EwIqA8hP44Q
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.2 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ GetDownloadableSubscriptionMetadataResult f$3;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$2$IvG3dLVC7AcOy5j0EwIqA8hP44Q(EuiccConnector.ConnectedState.2 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n, GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = n;
        this.f$3 = getDownloadableSubscriptionMetadataResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onComplete$0$EuiccConnector$ConnectedState$2(this.f$1, this.f$2, this.f$3);
    }
}

