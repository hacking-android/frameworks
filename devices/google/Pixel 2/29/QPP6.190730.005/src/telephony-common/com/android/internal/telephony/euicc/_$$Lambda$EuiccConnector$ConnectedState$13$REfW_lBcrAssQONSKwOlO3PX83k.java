/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$13$REfW_lBcrAssQONSKwOlO3PX83k
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.13 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$13$REfW_lBcrAssQONSKwOlO3PX83k(EuiccConnector.ConnectedState.13 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onOtaStatusChanged$1$EuiccConnector$ConnectedState$13(this.f$1, this.f$2);
    }
}

