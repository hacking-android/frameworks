/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$12$wYal9P4llN7g9YAk_zACL8m3nS0
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.12 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$12$wYal9P4llN7g9YAk_zACL8m3nS0(EuiccConnector.ConnectedState.12 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, int n) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuccess$0$EuiccConnector$ConnectedState$12(this.f$1, this.f$2);
    }
}

