/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$7$_Ogvr7PIASwQa0kQAqAyfdEKAG4
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.7 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$7$_Ogvr7PIASwQa0kQAqAyfdEKAG4(EuiccConnector.ConnectedState.7 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onComplete$0$EuiccConnector$ConnectedState$7(this.f$1, this.f$2);
    }
}

