/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$1$wTkmDdVlxcrtbVPcCl3t7xD490o
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.1 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$1$wTkmDdVlxcrtbVPcCl3t7xD490o(EuiccConnector.ConnectedState.1 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, String string) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = string;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuccess$0$EuiccConnector$ConnectedState$1(this.f$1, this.f$2);
    }
}

