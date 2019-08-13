/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$13$5nh8TOHvAdIIa_S3V0gwsRICKC4
implements Runnable {
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$13$5nh8TOHvAdIIa_S3V0gwsRICKC4(EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n) {
        this.f$0 = baseEuiccCommandCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        EuiccConnector.ConnectedState.13.lambda$onOtaStatusChanged$0(this.f$0, this.f$1);
    }
}

