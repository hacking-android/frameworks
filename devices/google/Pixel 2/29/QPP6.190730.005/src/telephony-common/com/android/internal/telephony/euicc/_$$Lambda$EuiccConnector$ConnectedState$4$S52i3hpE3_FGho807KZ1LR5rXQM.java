/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.euicc.GetEuiccProfileInfoListResult
 */
package com.android.internal.telephony.euicc;

import android.service.euicc.GetEuiccProfileInfoListResult;
import com.android.internal.telephony.euicc.EuiccConnector;

public final class _$$Lambda$EuiccConnector$ConnectedState$4$S52i3hpE3_FGho807KZ1LR5rXQM
implements Runnable {
    private final /* synthetic */ EuiccConnector.ConnectedState.4 f$0;
    private final /* synthetic */ EuiccConnector.BaseEuiccCommandCallback f$1;
    private final /* synthetic */ GetEuiccProfileInfoListResult f$2;

    public /* synthetic */ _$$Lambda$EuiccConnector$ConnectedState$4$S52i3hpE3_FGho807KZ1LR5rXQM(EuiccConnector.ConnectedState.4 var1_1, EuiccConnector.BaseEuiccCommandCallback baseEuiccCommandCallback, GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) {
        this.f$0 = var1_1;
        this.f$1 = baseEuiccCommandCallback;
        this.f$2 = getEuiccProfileInfoListResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onComplete$0$EuiccConnector$ConnectedState$4(this.f$1, this.f$2);
    }
}

